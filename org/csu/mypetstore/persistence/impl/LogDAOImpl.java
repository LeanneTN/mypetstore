package org.csu.mypetstore.persistence.impl;

import org.csu.mypetstore.domain.Log;
import org.csu.mypetstore.persistence.DBUtil;
import org.csu.mypetstore.persistence.LogDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LogDAOImpl implements LogDAO {
    private static final String INSERT_LOG = "INSERT INTO LOG (USERNAME, LOGINFO) VALUES(?,?)";
    private static final String GET_LOG_BY_USERNAME = "SELECT LOGINFO FROM LOG WHERE USERNAME = ? ";


    @Override
    public void insertLog(String username, String logInfo) {
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LOG);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, logInfo);

            preparedStatement.executeUpdate();
            DBUtil.closePreparedStatement(preparedStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Log> getLogsByUsername(String username){
        System.out.println(username);
        List<Log> logList = new ArrayList<Log>();
        try{
            Connection connection = DBUtil.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(GET_LOG_BY_USERNAME);
            pStatement.setString(1,username);
            ResultSet resultSet = pStatement.executeQuery();
            while(resultSet.next())
            {
                String usernameSql = resultSet.getString(1);
                String Information = resultSet.getString(2);
                Log log = new Log(usernameSql);
                log.setLogInfo(Information);
                logList.add(log);
            }
            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        }catch (Exception e){
            e.printStackTrace();
        }

        return logList;
    }
}
