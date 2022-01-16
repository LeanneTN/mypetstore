package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RemoveItemFromCartServlet extends HttpServlet {
    //处理完请求的跳转页面
    private static final String VIEW_CART = "/WEB-INF/jsp/cart/Cart.jsp";
    private static final String ERROR = "/WEB-INF/jsp/common/Error.jsp";

    //定义处理该请求的所需要的数据
    private String workingItemId;
    private Cart cart;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        workingItemId = request.getParameter("workingItemId");

        HttpSession session = request.getSession();
        cart = (Cart) session.getAttribute("cart");

        CartService cartService = new CartService();//向数据库写东西的对象
        Account account = (Account) session.getAttribute("account");
        int new_item_index = cart.getCartItemList().size()-1;
        cartService.removeCartitemByUsernameAndItemId(account.getUsername(),cart.getCartItemList().get(new_item_index).getItem().getItemId());

        Item item = cart.removeItemById(workingItemId);

        if (item == null) {
            session.setAttribute("message","Attempted to remove null CartItem from Cart.");
            request.getRequestDispatcher(ERROR).forward(request,response);
        } else {
            request.getRequestDispatcher(VIEW_CART).forward(request,response);
        }
    }
}
