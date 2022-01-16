package org.csu.mypetstore.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private Date date = null;
    private String logInfo = null;
    private String username = null;
    private String time = null;

    public Log(String action, String username){
        date = new Date();
        String simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        logInfo = action;
        this.username = username;
        time = simpleDateFormat;
    }

    public Log(String username){
        this.username = username;
    }

    public void setLogInfo(String info){
        logInfo = info;
    }

    public String logInfomation(){
        return "account "+ username + " " + logInfo + " at " + time;
    }

    public String getUsername(){
        return username;
    }

}
