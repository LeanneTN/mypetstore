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

public class NewOrderServlet extends HttpServlet {
    private  static final String CONFIRM = "/WEB-INF/jsp/order/ViewOrder.jsp";
    private static final String ERROR = "/WEB-INF/jsp/common/Error.jsp";

    private static final List<String> CARD_TYPE_LIST;

    private OrderService orderService;
    private AccountService accountService;

    private Order order = new Order();
    private String shippingAddressRequired;
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
        orderService = new OrderService();
        Order order = new Order();
        String cardType = request.getParameter("order.cardType");
        String number = request.getParameter("order.creditCard");
        String date = request.getParameter("order.expiryDate");
        String billToFirstName = request.getParameter("order.billToFirstName");
        String billToLastName = request.getParameter("order.billToLastName");
        String billAddress1 = request.getParameter("order.billAddress1");
        String billAddress2 = request.getParameter("order.billAddress2");
        String billCity = request.getParameter("order.billCity");
        String billState = request.getParameter("order.billState");
        String billZip = request.getParameter("order.billZip");
        String billCountry = request.getParameter("order.billCountry");
        String shipToFirstName = request.getParameter("order.shipToFirstName");
        String shipToLastName = request.getParameter("order.shipToLastName");
        String shipAddress1 = request.getParameter("order.shipAddress1");
        String shipAddress2 = request.getParameter("order.shipAddress2");
        String shipCity = request.getParameter("order.shipCity");
        String shipState = request.getParameter("order.shipState");
        String shipZip = request.getParameter("order.shipZip");
        String shipCountry = request.getParameter("order.shipCountry");
        Account account= (Account) session.getAttribute("account");
        Cart cart = (Cart) session.getAttribute("cart");
        if(account==null){
            String message = "You must sign on before attempting to check out.  Please sign on and try checking out again.";
            session.setAttribute("message",message);
            request.getRequestDispatcher(ERROR).forward(request,response);
        }else if(cart==null){
            String message = "An order could not be created because a cart could not be found.";
            session.setAttribute("message",message);
            request.getRequestDispatcher(ERROR).forward(request,response);
        }else {
            order.initOrder(account,cart);
            order.setOrderId(0);
            order.setShipToFirstName(shipToFirstName);
            order.setShipToLastName(shipToLastName);
            order.setShipAddress1(shipAddress1);
            order.setShipAddress2(shipAddress2);
            order.setShipCity(shipCity);
            order.setShipState(shipState);
            order.setShipZip(shipZip);
            order.setShipCountry(shipCountry);
            order.setBillToFirstName(billToFirstName);
            order.setBillToLastName(billToLastName);
            order.setBillAddress1(billAddress1);
            order.setBillAddress2(billAddress2);
            order.setBillCity(billCity);
            order.setBillState(billState);
            order.setBillZip(billZip);
            order.setBillCountry(billCountry);
            order.setCardType(cardType);
            order.setCreditCard(number);
            orderService.insertOrder(order);
            //List<Order> orderList = orderService.getOrdersByUsername(account.getUsername());
            cart = new Cart();
            session.setAttribute("workingItemId",null);
            session.setAttribute("cart",cart);
            session.setAttribute("order",order);
            //session.setAttribute("orderList",orderList);
            request.getRequestDispatcher(CONFIRM).forward(request,response);
        }
    }
}
