package com.aip.bus.servlet;

import com.aip.bus.dao.BusDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/search-buses")
public class SearchBusServlet extends HttpServlet {
    private final BusDAO dao = new BusDAO();
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        String source=req.getParameter("source"), destination=req.getParameter("destination"), date=req.getParameter("journeyDate");
        if(source==null||destination==null||date==null||source.isBlank()||destination.isBlank()||date.isBlank()){
            req.setAttribute("error","Please enter source, destination and journey date."); req.getRequestDispatcher("/index.jsp").forward(req,resp); return;
        }
        try { req.setAttribute("buses",dao.search(source,destination,date)); req.setAttribute("source",source); req.setAttribute("destination",destination); req.setAttribute("journeyDate",date); req.getRequestDispatcher("/WEB-INF/views/buses.jsp").forward(req,resp); }
        catch(IllegalArgumentException|SQLException ex){throw new ServletException("Could not search buses. Check your database settings and journey date.",ex);}
    }
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{resp.sendRedirect(req.getContextPath()+"/index.jsp");}
}
