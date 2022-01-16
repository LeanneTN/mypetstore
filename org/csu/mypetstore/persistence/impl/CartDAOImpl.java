package org.csu.mypetstore.persistence.impl;

import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.persistence.CartDAO;
import org.csu.mypetstore.persistence.DBUtil;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.CatalogService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartDAOImpl implements CartDAO {

    private CatalogService catalogService;

    private static final String CHECK_ACCOUNT_BY_USERNAME = "SELECT * FROM CART WHERE username=?";
//    private static final String GET_ITEM_ID_BY_USERNAME = "SELECT item_id FROM CART WHERE username=?";
    private static final String CHECK_IF_ITEM_EXSISTS_BY_USERNAME_AND_ITEMID = "SELECT * FROM CART WHERE username=? and item_id=?";
    private static final String CREAT_CART_BY_USERNAME = "INSERT INTO cart(username) values(?)";
    private static final String ADD_ITEM_TO_CART = "INSERT INTO cart(username,item_id,product_id,description,list_price,quantity,total_cost) values(?,?,?,?,?,?,?)";
    private static final String EDIT_CARTITEM_QUANTITY = "UPDATE cart SET quantity=? where username=? and item_id=?";
    private static final String GET_QUANTITY_BY_USERNAME_AND_ITEMID = "SELECT quantity FROM cart WHERE username=? and item_id=?";
    private static final String GET_CARTITEM_BY_USERNAME_AND_ITEMID = "SELECT list_price,quantity FROM cart WHERE username=? and item_id=?";
    private static final String GET_ALL_CARTITEM_BY_USERNAME = "SELECT item_id,list_price,quantity from cart WHERE username=? ";
    private static final String SELECT_PRODUCTID_BY_ITEMID = "SELECT product_id FROM cart WHERE item_id=?";
    private static final String REMOVE_CARTITEM_BY_USERNAME_AND_ITEMID = "DELETE FROM cart WHERE username=? and item_id=?";
    private static final String REMOVE_CARTITEM_BY_USERNAME = "DELETE FROM cart WHERE username=?";

    @Override
    public boolean checkIfCartExsistsByUsername(String username) {
        boolean flag=false;
        try {
            Connection connection= DBUtil.getConnection();
            PreparedStatement pStatement=connection.prepareStatement(CHECK_ACCOUNT_BY_USERNAME);
            pStatement.setString(1,username);
            ResultSet resultSet=pStatement.executeQuery();
            if (resultSet.next())
            {
                flag=true;
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return flag;
    }//查看该用户是否有购物车
/*
    @Override
    public String getItemIdByUsername(String username) {
        String itemid=null;
        try {
            Connection connection=DBUtil.getConnection();
            PreparedStatement pStatement=connection.prepareStatement(GET_ITEM_ID_BY_USERNAME);
            pStatement.setString(1,username);
            ResultSet resultSet=pStatement.executeQuery();
            if (resultSet.next())
            {
                itemid=resultSet.getString(2);
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
        return itemid;
    }*/

    @Override
    public void creatCartByUsername(String username) {
        try {
            Connection connection=DBUtil.getConnection();
            PreparedStatement pStatement=connection.prepareStatement(CREAT_CART_BY_USERNAME);
            pStatement.setString(1,username);

            int is_insert=pStatement.executeUpdate();
            DBUtil.closeStatement(pStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }//创建一个空购物车

    @Override
    public boolean checkIfCartitemExsistsByUsernameAndItemid(String username, String itemid) {
        boolean flag = false;
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CHECK_IF_ITEM_EXSISTS_BY_USERNAME_AND_ITEMID);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,itemid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                // = resultSet.getString(1);
                flag = true;
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }//看某类商品是否存在

    @Override
    public void addItemToCart(String username, CartItem cartItem) {
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_ITEM_TO_CART);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,cartItem.getItem().getItemId());
            preparedStatement.setString(3,cartItem.getItem().getProduct().getProductId());
            preparedStatement.setString(4,cartItem.getItem().getProduct().getDescription());
            preparedStatement.setString(5,cartItem.getItem().getListPrice().toString());
            preparedStatement.setString(6,Integer.toString(cartItem.getQuantity()));
            preparedStatement.setString(7,cartItem.getTotal().toString());

            int result = preparedStatement.executeUpdate();
            System.out.println(result);

            DBUtil.closeStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//像购物车中添加商品

    @Override
    public void editCartitemQuantity(String username, String itemid, int quantity) {
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(EDIT_CARTITEM_QUANTITY);
            preparedStatement.setInt(1,quantity);
            preparedStatement.setString(2,username);
            preparedStatement.setString(3,itemid);

            if(preparedStatement.execute()) {
                System.out.println("商品数量修改成功");
            }

            DBUtil.closeStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//更新购物车中某种商品的数量

    @Override
    public int getQuantityByUsernameAndItemid(String username, String itemid) {
        int quantity = 0;
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_QUANTITY_BY_USERNAME_AND_ITEMID);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,itemid);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                quantity = resultSet.getInt(6);
            }

            DBUtil.closeStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(quantity + ":::");
        return quantity;
    }//获取某种商品的数量

    @Override
    public List<CartItem> getAllCartitemByUsername(String username) {
        List<CartItem> cartItemList = new ArrayList<>();
        catalogService = new CatalogService();
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CARTITEM_BY_USERNAME);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            CartService cart = new CartService();

            while (resultSet.next()){
                CartItem cartItem = new CartItem();
                Item item = new Item();
//                String itemid = resultSet.getString(2);
//                item.setItemId(itemid);
//                BigDecimal bigDecimal = new BigDecimal(resultSet.getString(5));
//                item.setListPrice(bigDecimal);
//                String productid =getProductidByItemid(itemid);
//                item.setProductId(productid);
//                Product product = catalogService.getProduct(productid);
//                item.setProduct(product);
//                cartItem.setItem(item);
//                cartItem.setQuantity(resultSet.getInt(6));
//                cartItem.setInStock(true);
                String itemId = resultSet.getString(2);
                String productId = resultSet.getString(3);
                //String description = resultSet.getString(4);
                BigDecimal bigDecimal = new BigDecimal(String.valueOf(resultSet.getString(5)));
                int quantity = resultSet.getInt(6);
                String totalMoney = resultSet.getString(7);

                item.setItemId(itemId);
                item.setProductId(productId);
                item.setListPrice(bigDecimal);
                item.setQuantity(quantity);
                cartItem.setItem(item);

                cartItemList.add(cartItem);
                cart.setCart(item);

            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }

        return cartItemList;
    }

    @Override
    public Item removeCartitemByUsernameAndItemId(String username, String itemid) {//此功能不完善且设计有缺陷
        Item item = null;
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_CARTITEM_BY_USERNAME_AND_ITEMID);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,itemid);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                item = new Item();
                item.setItemId(resultSet.getString(2));
            }

            PreparedStatement preparedStatement1 = connection.prepareStatement(REMOVE_CARTITEM_BY_USERNAME_AND_ITEMID);
            preparedStatement1.setString(1,username);
            preparedStatement1.setString(2,itemid);
            preparedStatement1.executeUpdate();


            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(preparedStatement);
            DBUtil.closePreparedStatement(preparedStatement1);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    public String getProductidByItemid(String itemid) {
        String productid = null;
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(SELECT_PRODUCTID_BY_ITEMID);
            pStatement.setString(1,itemid);

            ResultSet resultSet = pStatement.executeQuery();

            if(resultSet.next()) {
                productid = resultSet.getString(1);
            }

            DBUtil.closeResultSet(resultSet);
            DBUtil.closeStatement(pStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productid;
    }

    public Item removeCartItemByUsername(String username){
        Item item = null;
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_CARTITEM_BY_USERNAME);
            preparedStatement.setString(1,username);
            preparedStatement.executeUpdate();

            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public void getAllItemsIntoCart(String username) {
        try {
            Connection connection= DBUtil.getConnection();
            PreparedStatement pStatement=connection.prepareStatement(CHECK_ACCOUNT_BY_USERNAME);
            pStatement.setString(1,username);
            ResultSet resultSet=pStatement.executeQuery();

            Item item = new Item();
            Cart cart = new Cart();
            CartItem cartItem = new CartItem();
            while(resultSet.next()){
                String itemId = resultSet.getString(2);
                String productId = resultSet.getString(3);
                String description = resultSet.getString(4);
                BigDecimal bigDecimal = new BigDecimal(String.valueOf(resultSet.getString(5)));
                int quantity = resultSet.getInt(6);
                String totalMoney = resultSet.getString(7);

                item.setItemId(itemId);
                item.setProductId(productId);
                item.setListPrice(bigDecimal);
                item.setQuantity(quantity);

                cart.addItem(item,true);
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void clearCartByUsername(String username) {

    }

/*    public BigDecimal getTotalPrice(String username) {
        BigDecimal totalPrice = new BigDecimal("0");
        String cartid = selectAccountByUsername1(username);
        List<CartItem> cartItemList = getAllCartitemByUsername(username);

        for(int i=0;i<cartItemList.size();i++) {
            CartItem cartItem = cartItemList.get(i);
            //totalPrice += cartItem.getTotal();
            totalPrice = totalPrice.add(cartItem.getTotal());
        }

//        System.out.println(totalPrice+"okkokokokokokokok");
        return totalPrice;

    }*/


}
