package org.csu.mypetstore.web.servlets;

import javax.servlet.ServletException;
import java.io.IOException;

public class MainServlet extends  javax.servlet.http.HttpServlet{
    private  static final String MAIN = "/WEB-INF/jsp/catalog/Main.jsp";

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(MAIN).forward(request,response);
    }
}
