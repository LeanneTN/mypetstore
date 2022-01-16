package org.csu.mypetstore.persistence.impl;

import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.persistence.DBUtil;
import org.csu.mypetstore.persistence.OrderDAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private static final String GET_ORDERS_BY_USERNAME = "SELECT BILLADDR1 AS billAddress1,BILLADDR2 AS billAddress2, BILLCITY, BILLCOUNTRY, BILLSTATE, BILLTOFIRSTNAME, BILLTOLASTNAME, BILLZIP, SHIPADDR1 AS shipAddress1, SHIPADDR2 AS shipAddress2, SHIPCITY, SHIPCOUNTRY, SHIPSTATE, SHIPTOFIRSTNAME, SHIPTOLASTNAME, SHIPZIP, CARDTYPE, COURIER, CREDITCARD, EXPRDATE AS expiryDate, LOCALE, ORDERDATE, ORDERID, TOTALPRICE, USERID AS username FROM ORDERS WHERE USERID = ?";
    private static final String GET_ORDER = "select BILLADDR1 AS billAddress1,BILLADDR2 AS billAddress2,BILLCITY,BILLCOUNTRY,BILLSTATE,BILLTOFIRSTNAME,BILLTOLASTNAME,BILLZIP,SHIPADDR1 AS shipAddress1,SHIPADDR2 AS shipAddress2,SHIPCITY,SHIPCOUNTRY,SHIPSTATE,SHIPTOFIRSTNAME,SHIPTOLASTNAME,SHIPZIP,CARDTYPE,COURIER,CREDITCARD,EXPRDATE AS expiryDate,LOCALE,ORDERDATE,ORDERID,TOTALPRICE,USERID AS username FROM ORDERS WHERE ORDERID = ?";
    private static final String INSERT_ORDER = "INSERT INTO ORDERS (ORDERID, USERID, ORDERDATE, SHIPADDR1, SHIPADDR2, SHIPCITY, SHIPSTATE, SHIPZIP, SHIPCOUNTRY, BILLADDR1, BILLADDR2, BILLCITY, BILLSTATE, BILLZIP, BILLCOUNTRY, COURIER, TOTALPRICE, BILLTOFIRSTNAME, BILLTOLASTNAME, SHIPTOFIRSTNAME, SHIPTOLASTNAME, CREDITCARD, EXPRDATE, CARDTYPE, LOCALE) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_ORDER_STATUS = "INSERT INTO ORDERSTATUS (ORDERID, LINENUM, TIMESTAMP, STATUS) VALUES (?,?,?,?)";

    @Override
    public List<Order> getOrdersByUsername(String username) {
        List<Order> orderList = new ArrayList<Order>();
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(GET_ORDERS_BY_USERNAME);
            pStatement.setString(1,username);
            ResultSet resultSet = pStatement.executeQuery();
            while(resultSet.next())
            {
                Order order = new Order();
                order.setBillAddress1(resultSet.getString(1));
                order.setBillAddress2(resultSet.getString(2));
                order.setBillCity(resultSet.getString(3));
                order.setBillCountry(resultSet.getString(4));
                order.setBillState(resultSet.getString(5));
                order.setBillToFirstName(resultSet.getString(6));
                order.setBillToLastName(resultSet.getString(7));
                order.setBillZip(resultSet.getString(8));
                order.setShipAddress1(resultSet.getString(9));
                order.setShipAddress2(resultSet.getString(10));
                order.setShipCity(resultSet.getString(11));
                order.setShipCountry(resultSet.getString(12));
                order.setShipState(resultSet.getString(13));
                order.setShipToFirstName(resultSet.getString(14));
                order.setShipToLastName(resultSet.getString(15));
                order.setShipZip(resultSet.getString(16));
                order.setCreditCard(resultSet.getString(17));
                order.setCourier(resultSet.getString(18));
                order.setCardType(resultSet.getString(19));
                order.setExpiryDate(resultSet.getString(20));
                order.setLocale(resultSet.getString(21));
                order.setOrderDate(resultSet.getDate(22));
                order.setOrderId(resultSet.getInt(23));
                order.setTotalPrice(resultSet.getBigDecimal(24));
                order.setUsername(resultSet.getString(25));
                orderList.add(order);
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public Order getOrder(int orderId) {
        Order order = new Order();
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(GET_ORDER);
            pStatement.setInt(1,orderId);
            ResultSet resultSet = pStatement.executeQuery();
            if(resultSet.next())
            {
                order.setBillAddress1(resultSet.getString(1));
                order.setBillAddress2(resultSet.getString(2));
                order.setBillCity(resultSet.getString(3));
                order.setBillCountry(resultSet.getString(4));
                order.setBillState(resultSet.getString(5));
                order.setBillToFirstName(resultSet.getString(6));
                order.setBillToLastName(resultSet.getString(7));
                order.setBillZip(resultSet.getString(8));
                order.setShipAddress1(resultSet.getString(9));
                order.setShipAddress2(resultSet.getString(10));
                order.setShipCity(resultSet.getString(11));
                order.setShipCountry(resultSet.getString(12));
                order.setShipState(resultSet.getString(13));
                order.setShipToFirstName(resultSet.getString(14));
                order.setShipToLastName(resultSet.getString(15));
                order.setShipZip(resultSet.getString(16));
                order.setCreditCard(resultSet.getString(17));
                order.setCourier(resultSet.getString(18));
                order.setCardType(resultSet.getString(19));
                order.setExpiryDate(resultSet.getString(20));
                order.setLocale(resultSet.getString(21));
                order.setOrderDate(resultSet.getDate(22));
                order.setOrderId(resultSet.getInt(23));
                order.setTotalPrice(resultSet.getBigDecimal(24));
                order.setUsername(resultSet.getString(25));
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public void insertOrder(Order order) {
        int orderId = order.getOrderId();
        String username = order.getUsername();
        java.util.Date orderDate = order.getOrderDate();
        String shipAddress1 = order.getShipAddress1();
        String shipAddress2 = order.getShipAddress2();
        String shipCity = order.getShipCity();
        String shipState = order.getShipState();
        String shipZip = order.getShipZip();
        String shipCountry = order.getShipCountry();
        String billAddress1 = order.getBillAddress1();
        String billAddress2 = order.getBillAddress2();
        String billCity = order.getBillCity();
        String billState = order.getBillState();
        String billZip = order.getBillZip();
        String billCountry = order.getBillCountry();
        String courier = order.getCourier();
        BigDecimal totalPrice = order.getTotalPrice();
        String billToFirstName = order.getBillToFirstName();
        String billToLastName = order.getBillToLastName();
        String shipToFirstName = order.getShipToFirstName();
        String shipToLastName = order.getShipToLastName();
        String creditCard = order.getCreditCard();
        String expiryDate = order.getExpiryDate();
        String cardType = order.getCardType();
        String locale = order.getLocale();

        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(INSERT_ORDER);
            pStatement.setInt(1,orderId);
            pStatement.setString(2,username);
            pStatement.setDate(3,new Date(orderDate.getTime()));
            pStatement.setString(4,shipAddress1);
            pStatement.setString(5,shipAddress2);
            pStatement.setString(6,shipCity);
            pStatement.setString(7,shipState);
            pStatement.setString(8,shipZip);
            pStatement.setString(9,shipCountry);
            pStatement.setString(10,billAddress1);
            pStatement.setString(11,billAddress2);
            pStatement.setString(12,billCity);
            pStatement.setString(13,billState);
            pStatement.setString(14,billZip);
            pStatement.setString(15,billCountry);
            pStatement.setString(16,courier);
            pStatement.setBigDecimal(17,totalPrice);
            pStatement.setString(18,billToFirstName);
            pStatement.setString(19,billToLastName);
            pStatement.setString(20,shipToFirstName);
            pStatement.setString(21,shipToLastName);
            pStatement.setString(22,creditCard);
            pStatement.setString(23,expiryDate);
            pStatement.setString(24,cardType);
            pStatement.setString(25,locale);
            pStatement.executeUpdate();
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void insertOrderStatus(Order order) {
        int orderId = order.getOrderId();
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(INSERT_ORDER_STATUS);
            for(int i=0;i<order.getLineItems().size();i++){
                pStatement.setInt(1,orderId);
                pStatement.setInt(2,order.getLineItems().get(i).getLineNumber());
                pStatement.setString(3,"xx");
                pStatement.setString(4,"preparing");
                pStatement.executeUpdate();
            }
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
