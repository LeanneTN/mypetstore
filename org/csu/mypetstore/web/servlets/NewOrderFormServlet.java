package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewOrderFormServlet extends HttpServlet {
    private static final String ERROR = "/WEB-INF/jsp/common/Error.jsp";
    private static final String NEW_ORDER = "/WEB-INF/jsp/order/AtOneForm.jsp";
    private static final List<String> CARD_TYPE_LIST;

    private OrderService orderService;
    private AccountService accountService;

    private Order order = new Order();
    private boolean shippingAddressRequired;
    private boolean confirmed;
    private List<Order> orderList;

    static {
        List<String> cardList = new ArrayList<String>();
        cardList.add("Visa");
        cardList.add("MasterCard");
        cardList.add("American Express");
        CARD_TYPE_LIST = Collections.unmodifiableList(cardList);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        order = new Order();
        shippingAddressRequired = false;
        confirmed = false;
        orderList = null;
        //String username = request.getParameter("username");
        Account account= (Account) session.getAttribute("account");
        Cart cart = (Cart) session.getAttribute("cart");
        //Account account = accountService.getAccount(username);
        if (account == null ) {
            String message = "You must sign on before attempting to check out.  Please sign on and try checking out again.";
            session.setAttribute("message",message);
            request.getRequestDispatcher(ERROR).forward(request,response);
        } else if (cart != null) {
            order.initOrder(account,cart);
            request.getRequestDispatcher(NEW_ORDER).forward(request,response);
        } else {
            String message = "An order could not be created because a cart could not be found.";
            session.setAttribute("message",message);
            request.getRequestDispatcher(ERROR).forward(request,response);
        }
    }

}
