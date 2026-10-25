package com.aip.bus.servlet;

import com.aip.bus.config.HibernateUtil;
import com.aip.bus.model.Booking;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.hibernate.Session;
import java.io.IOException;
import java.util.List;

@WebServlet("/my-bookings")
public class MyBookingsServlet extends HttpServlet {
 @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{req.getRequestDispatcher("/WEB-INF/views/my-bookings.jsp").forward(req,resp);}
 @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
  String email=req.getParameter("email"); if(email==null||email.isBlank()){req.setAttribute("error","Enter the email used for booking.");doGet(req,resp);return;}
  try(Session s=HibernateUtil.getFactory().openSession()){
   List<Booking> bookings=s.createQuery("from Booking b where lower(b.passengerEmail)=lower(:email) order by b.id desc",Booking.class).setParameter("email",email.trim()).list();
   req.setAttribute("bookings",bookings);req.setAttribute("email",email.trim());req.getRequestDispatcher("/WEB-INF/views/my-bookings.jsp").forward(req,resp);
  }catch(Exception e){throw new ServletException("Could not load bookings.",e);}
 }
}
