package com.aip.bus.servlet;

import com.aip.bus.config.HibernateUtil;
import com.aip.bus.model.Booking;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.io.IOException;

@WebServlet("/cancel-booking")
public class CancelBookingServlet extends HttpServlet {
 @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
  String email=req.getParameter("email");try{long id=Long.parseLong(req.getParameter("bookingId"));Transaction tx=null;
   try(Session s=HibernateUtil.getFactory().openSession()){tx=s.beginTransaction();Booking b=s.get(Booking.class,id);
    if(b!=null&&email!=null&&b.getPassengerEmail().equalsIgnoreCase(email.trim())&&"CONFIRMED".equals(b.getStatus())){b.setStatus("CANCELLED");s.merge(b);req.setAttribute("message","Booking #"+id+" cancelled successfully.");}
    else req.setAttribute("error","Booking not found, email mismatch, or booking already cancelled.");tx.commit();}
   req.setAttribute("email",email);
   if(email!=null&&!email.isBlank()){try(Session s=HibernateUtil.getFactory().openSession()){List<Booking> bookings=s.createQuery("from Booking b where lower(b.passengerEmail)=lower(:email) order by b.id desc",Booking.class).setParameter("email",email.trim()).list();req.setAttribute("bookings",bookings);}}
   req.getRequestDispatcher("/WEB-INF/views/my-bookings.jsp").forward(req,resp);
  }catch(NumberFormatException e){resp.sendError(400,"Invalid booking ID");}catch(Exception e){throw new ServletException("Could not cancel booking.",e);}
 }
}
