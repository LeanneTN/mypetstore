package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Log;
import org.csu.mypetstore.service.LogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class LogServlet extends HttpServlet {
    private static final String LOG = "/WEB-INF/jsp/log/Log.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        LogService logService = new LogService();
        Account account =(Account) session.getAttribute("account");
        String username = account.getUsername();

        List<Log> logList;
        logList = logService.getLogByUsername(username);

        session.setAttribute("logList",logList);
        req.getRequestDispatcher(LOG).forward(req,resp);
    }
}
