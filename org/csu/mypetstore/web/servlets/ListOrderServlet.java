package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ListOrderServlet extends HttpServlet {
    private static final String LIST_ORDER = "/WEB-INF/jsp/order/ListOrders.jsp";
    private static final String ERROR = "/WEB-INF/jsp/common/Error.jsp";

    OrderService service = new OrderService();

    private Order order;

    private  static final String LIST_ORDERS = "/WEB-INF/jsp/order/ListOrders.jsp";

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        OrderService orderService = new OrderService();
        Account account =(Account) session.getAttribute("account");
        List<Order> orderList = orderService.getOrdersByUsername(account.getUsername());
        System.out.println(account.getUsername());
        session.setAttribute("orderList",orderList);
        request.getRequestDispatcher(LIST_ORDERS).forward(request,response);
    }
}
