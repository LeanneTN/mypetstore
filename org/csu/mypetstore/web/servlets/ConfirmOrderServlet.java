package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfirmOrderServlet extends HttpServlet {
    private static final String CONFIRM_ORDER = "/WEB-INF/jsp/order/ConfirmOrder.jsp";
    private static final String ERROR = "/WEB-INF/jsp/common/Error.jsp";
    private static final String NEW_ORDER = "/WEB-INF/jsp/order/NewOrderForm.jsp";
    private static final String AT_ONE_ORDER = "/WEB-INF/jsp/order/AtOneForm.jsp";

    private static final List<String> CARD_TYPE_LIST;

    private OrderService orderService = new OrderService();
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
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
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
            req.getRequestDispatcher(ERROR).forward(req,resp);
        } else if (cart != null) {
            order.initOrder(account,cart);
            session.setAttribute("order",order);
            orderService.insertOrder(order);
            req.getRequestDispatcher(AT_ONE_ORDER).forward(req,resp);
        } else {
            String message = "An order could not be created because a cart could not be found.";
            session.setAttribute("message",message);
            req.getRequestDispatcher(ERROR).forward(req,resp);
        }
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-yyyy");
//        Date orderDate = null;
//        try {
//            orderDate = simpleDateFormat.parse(req.getParameter("order.orderDate"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        int orderId = orderService.getNextId();
//        String billAddress1 = req.getParameter("order.billAddress1");
//        String billAddress2 = req.getParameter("order.billAddress2");
//        String billCity = req.getParameter("order.billCity");
//        String billState = req.getParameter("order.billState");
//        String billZip = req.getParameter("order.billZip");
//        String billCountry = req.getParameter("order.billCountry");
//        String billToFirstName = req.getParameter("order.billToFirstName");
//        String billToLastName = req.getParameter("order.billToLastName");
//        String creditCard = req.getParameter("order.creditCard");
//        String expiryDate = req.getParameter("order.expiryDate");
//        String cardType = req.getParameter("order.cardType");

        //order.setOrderDate(orderDate);
//        order.setOrderId(orderId);
//        order.setBillAddress1(billAddress1);
//        order.setBillAddress2(billAddress2);
//        order.setBillCity(billCity);
//        order.setBillState(billState);
//        order.setBillZip(billZip);
//        order.setBillCountry(billCountry);
//        order.setBillToFirstName(billToFirstName);
//        order.setBillToLastName(billToLastName);
//        order.setCreditCard(creditCard);
//        order.setExpiryDate(expiryDate);
//        order.setCardType(cardType);

//        orderService.insertOrder(order);
//        order = orderService.getOrder(order.getOrderId());
//        session.setAttribute("order",order);
//        req.getRequestDispatcher(CONFIRM_ORDER).forward(req,resp);
    }
}
