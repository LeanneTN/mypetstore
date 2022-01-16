package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CatalogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignOutServlet extends HttpServlet {
    private  static final String MAIN = "/WEB-INF/jsp/catalog/Main.jsp";


    private boolean authenticated;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountService accountService = new AccountService();
        CatalogService catalogService = new CatalogService();
        Account account = new Account();
        //List<Product> myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
        //myList = null;
        authenticated = false;
        session.setAttribute("authenticated",authenticated);
        request.getRequestDispatcher(MAIN).forward(request,response);
    }
}
