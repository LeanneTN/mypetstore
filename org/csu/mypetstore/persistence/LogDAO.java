package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Log;

import java.util.List;

public interface LogDAO {
    public void insertLog(String username, String logInfo);

    public List<Log> getLogsByUsername(String username);

}
