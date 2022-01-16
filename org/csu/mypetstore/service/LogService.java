package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Log;
import org.csu.mypetstore.persistence.impl.LogDAOImpl;

import java.util.List;

public class LogService {
    private LogDAOImpl logDAO;

    public void insertLog(String username, String action){
        logDAO = new LogDAOImpl();
        Log log = new Log(action, username);
        logDAO.insertLog(username, log.logInfomation());
    }

    public List<Log> getLogByUsername(String username){
        List<Log> logList = logDAO.getLogsByUsername(username);
        return logList;
    }
}
