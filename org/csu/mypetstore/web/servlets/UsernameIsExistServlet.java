package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UsernameIsExistServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");

        AccountService service = new AccountService();
        Account result = service.getAccount(username);

        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();

        if(result == null){
            out.print("Not Exist");
        }else{
            out.print("Exist");
        }
        out.flush();
        out.flush();
    }
}
