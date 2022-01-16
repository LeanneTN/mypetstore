package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.LogService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ViewCartServlet", value = "/ViewCartServlet")

public class ViewCartServlet extends HttpServlet {
    private static final String VIEW_CART = "/WEB-INF/jsp/cart/Cart.jsp";
    private static final String ERROR = "/WEB-INF/jsp/common/Error.jsp";
    private LogService service = new LogService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart)session.getAttribute("cart");

        if(cart == null){
            cart = new Cart();
            session.setAttribute("cart",cart);
        }

        Account account =(Account) session.getAttribute("account");
        if(account==null){
            String message = "Please sign on first";
            session.setAttribute("message", message);
            request.getRequestDispatcher(ERROR).forward(request,response);
        }else {
            String username = account.getUsername();
            service.insertLog(username, username + " confirmed the order");
            List<CartItem> cartItemList = new ArrayList<CartItem>();
            CartService service = new CartService();
//            cartItemList = service.getCartList(username);
//            session.setAttribute("cartItem", cartItemList);
//            List<Item> cartItems = new ArrayList<>();
//            CartService cartService = new CartService();


            CartService cartService = new CartService();
            session.setAttribute("cart", cartService.getCart(username));
            request.getRequestDispatcher(VIEW_CART).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
