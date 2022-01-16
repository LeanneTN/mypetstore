package org.csu.mypetstore.persistence.impl;

import org.csu.mypetstore.persistence.CartUpdateDAO;
import org.csu.mypetstore.persistence.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CartUpdateDAOImpl implements CartUpdateDAO {
    private static final String UPDATE_BY_USERNAME = "UPDATE cart SET quantity = ? WHERE item_id = ? AND username = ?";
    private static final String UPDATE_TOTAL_BY_USERNAME = "UPDATE cart SET total_cost = ? WHERE item_id = ? AND username = ?";
    private static final String GET_PRICE_BY_USERNAME = "SELECT LIST_PRICE FROM CART WHERE USERNAME = ? AND ITEM_ID = ?";

    @Override
    public void cartNumberUpdateByUsername(String username, String itemId, String quantity) {
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_USERNAME);
            preparedStatement.setString(1,quantity);
            preparedStatement.setString(2,itemId);
            preparedStatement.setString(3,username);
            preparedStatement.executeUpdate();

            System.out.println("cart number change " + quantity);

            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cartTotalUpdateByUsername(String username, String itemId, String quantity){

        try {
            System.out.println("get into");

            int price = getPrice(username, itemId);
            System.out.println(price+";;;;;;");
            int total = price * Integer.getInteger(quantity);
            String totalPrice = String.valueOf(total);

            System.out.println(totalPrice);
            Connection connection = null;
            connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TOTAL_BY_USERNAME);
            preparedStatement.setString(1,totalPrice);
            preparedStatement.setString(2,itemId);
            preparedStatement.setString(3,username);
            preparedStatement.executeUpdate();
            System.out.println("cart total change: " + totalPrice + " " + price + " " + quantity);

            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getPrice(String username, String itemId){
        int price = 0;
        try{
            Connection connection = null;
            connection = DBUtil.getConnection();
            System.out.println(username + itemId + "get price getInto");
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PRICE_BY_USERNAME);
            preparedStatement.setString(1,username);
            System.out.println("username input");
            preparedStatement.setString(2,itemId);
            System.out.println("item input");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.getRow()==0)
                System.out.println("success");
            else
                System.out.println("error");
            while(resultSet.next()) {
                resultSet.first();
                System.out.println("resultset get");
                price = Integer.parseInt(resultSet.getObject(1).toString());
                System.out.println(resultSet.getString(1));
            }
            System.out.println("result set have no next");

            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
            DBUtil.closeResultSet(resultSet);
            System.out.println("======out get price");
        }catch (Exception e){
            e.printStackTrace();
        }

        return price;
    }
}
