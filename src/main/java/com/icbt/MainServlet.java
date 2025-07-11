package com.icbt;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class MainServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<h1>Hello from Servlet!</h1>");
    }
}