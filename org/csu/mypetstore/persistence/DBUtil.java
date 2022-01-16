package org.csu.mypetstore.persistence;

import java.sql.*;

public class DBUtil {
    private static String driveString = "com.mysql.cj.jdbc.Driver";
    private static String connectString = "jdbc:mysql://localhost:3306/mypetstore";
    private static String username = "root";
    private static String password = "abc123";

    public static Connection getConnection() throws Exception{
        Connection connection = null;
        try{
            Class.forName(driveString);
            connection = DriverManager.getConnection(connectString,username,password);
        }catch (Exception e){
            throw e;
        }

        return connection;
    }

    public static void closeStatement(Statement statement) throws Exception{
        statement.close();
    }

    public static void closePreparedStatement(PreparedStatement preparedStatement) throws Exception{
        preparedStatement.close();
    }

    public static void closeResultSet(ResultSet resultSet) throws Exception{
        resultSet.close();
    }

    public static void closeConnection(Connection connection) throws Exception{
        connection.close();
    }


//    public static void main(String[] args) {
//        try {
//            Connection conn = DBUtil.getConnection();
//            System.out.println(conn);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
