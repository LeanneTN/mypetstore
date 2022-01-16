package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;

import java.util.List;

public interface CartDAO {
    boolean checkIfCartExsistsByUsername(String username);

//    String getItemIdByUsername(String username);

    void creatCartByUsername(String username);

    boolean checkIfCartitemExsistsByUsernameAndItemid(String username,String itemid);

    void addItemToCart(String username, CartItem cartItem);

    void editCartitemQuantity(String username,String itemid,int quantity);

    int getQuantityByUsernameAndItemid(String username,String itemid);

    List<CartItem> getAllCartitemByUsername(String username);

    Item removeCartitemByUsernameAndItemId(String username,String itemid);

    Item removeCartItemByUsername(String username);

    void getAllItemsIntoCart(String username);

    void clearCartByUsername(String username);

//    BigDecimal getTotalPrice(String username);

}
