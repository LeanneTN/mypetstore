package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.csu.mypetstore.service.LogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ViewItemServlet extends  javax.servlet.http.HttpServlet{
    private  static final String VIEW_ITEM = "/WEB-INF/jsp/catalog/Item.jsp";

    private String itemId;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        itemId = request.getParameter("itemId");
        CatalogService service = new CatalogService();
        Item item = service.getItem(itemId);
        Product product = item.getProduct();

        HttpSession session = request.getSession();
        session.setAttribute("product",product);
        session.setAttribute("item",item);

        Account account =(Account) session.getAttribute("account");
        String username = account.getUsername();
        LogService service1 = new LogService();
        service1.insertLog(username,username + " viewed the item " + itemId);

        request.getRequestDispatcher(VIEW_ITEM).forward(request,response);
    }
}