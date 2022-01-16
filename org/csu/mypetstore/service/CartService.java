package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.persistence.CartDAO;
import org.csu.mypetstore.persistence.impl.CartDAOImpl;
import org.csu.mypetstore.persistence.impl.CartUpdateDAOImpl;

import java.util.List;

public class CartService {
    private Cart cart = new Cart();
    private CartDAO cartDAO;
    private CartUpdateDAOImpl cartUpdateDAO = new CartUpdateDAOImpl();
    public CartService(){
        cartDAO = new CartDAOImpl();
    }

    public void addItemToCart(String username, CartItem cartItem){
        cartDAO.addItemToCart(username,cartItem);
    }

    public Item removeCartitemByUsernameAndItemId(String username, String ItemId) {
        cartDAO.removeCartitemByUsernameAndItemId(username,ItemId);
        return null;
    }

    public Item removeCartItemByUsername(String username){
        cartDAO.removeCartItemByUsername(username);
        return null;
    }

    public List<CartItem> getCartList(String username){
        cartDAO = new CartDAOImpl();
        return cartDAO.getAllCartitemByUsername(username);
    }

    public Cart getCart(String username){
        cartDAO.getAllCartitemByUsername(username);
        return cart;
    }

    public void setCart(Item item){
        cart.addItem(item,true);
    }

    public void update(String username, String itemId, String quantity){
        System.out.println("update get into");

        cartUpdateDAO.cartNumberUpdateByUsername(username,itemId,quantity);
    }

    public void updateTotal(String username, String itemId, String quantity){
        System.out.println("updateTotal get into");

        cartUpdateDAO.cartTotalUpdateByUsername(username, itemId, quantity);
    }
}
