package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.LogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CartClearServlet extends HttpServlet {
    private static String MAIN = "/WEB-INF/jsp/catalog/Main.jsp";
    private LogService service = new LogService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("cart");
        Account account =(Account) session.getAttribute("account");
        String username = account.getUsername();
        service.insertLog(username,username + "clean the cart");
        CartService service = new CartService();
        service.removeCartItemByUsername(username);
        req.getRequestDispatcher(MAIN).forward(req,resp);
    }
}
