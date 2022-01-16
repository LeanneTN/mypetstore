package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.CatalogService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AddItemToCartServlet", value = "/AddItemToCartServlet")
public class AddItemToCartServlet extends HttpServlet {
    //1.处理完请求后的跳转页面
    private static final String VIEW_CART = "/WEB-INF/jsp/cart/Cart.jsp";
    //2.定义处理该请求所需要的数据
    private String workingItemId;
    private Cart cart;

    //3.是否需要调用业务逻辑层
    private CatalogService catalogService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        workingItemId = request.getParameter("workingItemId");
        HttpSession session =request.getSession();
        cart = (Cart)session.getAttribute("cart");

        if(cart==null){
            cart = new Cart();
        }

        if(cart.containsItemId(workingItemId)){
            cart.incrementQuantityByItemId(workingItemId);
        }
        else{
            catalogService = new CatalogService();
            boolean isInStock = catalogService.isItemInStock(workingItemId);
            Item item = catalogService.getItem(workingItemId);
            cart.addItem(item,isInStock);
        }

        CartService cartService = new CartService();//向数据库写东西的对象
        Account account = (Account) session.getAttribute("account");
        int new_item_index = cart.getCartItemList().size()-1;
        cartService.addItemToCart(account.getUsername(),cart.getCartItemList().get(new_item_index));

        session.setAttribute("cart",cart);
        request.getRequestDispatcher(VIEW_CART).forward(request,response) ;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
