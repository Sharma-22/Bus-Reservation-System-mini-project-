package com.aip.bus.servlet;

import com.aip.bus.config.HibernateUtil;
import com.aip.bus.model.Booking;
import com.aip.bus.model.Bus;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.IOException;

@WebServlet("/book")
public class BookBusServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        try { int busId=Integer.parseInt(req.getParameter("busId"));
            try(Session s=HibernateUtil.getFactory().openSession()){
                Bus bus=s.get(Bus.class,busId); if(bus==null){resp.sendError(404,"Bus not found");return;}
                req.setAttribute("bus",bus); req.getRequestDispatcher("/WEB-INF/views/book.jsp").forward(req,resp);
            }
        } catch(NumberFormatException e){resp.sendError(400,"Invalid bus ID");}
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        req.setCharacterEncoding("UTF-8");
        String name=req.getParameter("name"),email=req.getParameter("email"),phone=req.getParameter("phone");
        try {
            int busId=Integer.parseInt(req.getParameter("busId")); int seat=Integer.parseInt(req.getParameter("seatNumber"));
            if(blank(name)||blank(email)||blank(phone)||!email.contains("@")||phone.trim().length()<7){req.setAttribute("error","Enter valid passenger details.");showForm(req,resp,busId);return;}
            Transaction tx=null;
            try(Session s=HibernateUtil.getFactory().openSession()){
                tx=s.beginTransaction(); Bus bus=s.get(Bus.class,busId);
                if(bus==null||seat<1||seat>bus.getTotalSeats()) throw new IllegalArgumentException("Invalid bus or seat number.");
                Long occupied=s.createQuery("select count(b) from Booking b where b.bus.id=:busId and b.seatNumber=:seat and b.status='CONFIRMED'",Long.class).setParameter("busId",busId).setParameter("seat",seat).uniqueResult();
                if(occupied!=null&&occupied>0){tx.rollback();req.setAttribute("error","That seat is already booked. Please choose another seat.");showForm(req,resp,busId);return;}
                Booking booking=new Booking(name.trim(),email.trim(),phone.trim(),seat,bus); s.persist(booking); tx.commit();
                req.setAttribute("booking",booking); req.getRequestDispatcher("/WEB-INF/views/confirmation.jsp").forward(req,resp);
            } catch(Exception ex){if(tx!=null&&tx.isActive())tx.rollback(); if(ex instanceof IllegalArgumentException){req.setAttribute("error",ex.getMessage());showForm(req,resp,busId);}else throw new ServletException("Booking could not be saved. Check the selected seat and database.",ex);}
        } catch(NumberFormatException ex){resp.sendError(400,"Invalid bus or seat number");}
    }
    private void showForm(HttpServletRequest req,HttpServletResponse resp,int busId)throws ServletException,IOException{
        try(Session s=HibernateUtil.getFactory().openSession()){req.setAttribute("bus",s.get(Bus.class,busId));req.getRequestDispatcher("/WEB-INF/views/book.jsp").forward(req,resp);}
    }
    private boolean blank(String s){return s==null||s.isBlank();}
}
