package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.persistence.impl.AccountDAOImpl;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CatalogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class SignonServlet extends HttpServlet {
    private static final String MAIN = "/WEB-INF/jsp/catalog/Main.jsp";
    private static final String ERROR = "/WEB-INF/jsp/common/Error.jsp";
    private static final String SIGNON = "/WEB-INF/jsp/account/SignonForm.jsp";

    private String username;
    private String password;
    private String location;
    private boolean authenticated;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {

        username = request.getParameter("username");
        password = request.getParameter("password");
        HttpSession session = request.getSession();
        //获取session中的验证码
        String token =(String) session.getAttribute(KAPTCHA_SESSION_KEY);
        //删除验证码防止重复操作
        session.removeAttribute(KAPTCHA_SESSION_KEY);
        String code = request.getParameter("verificationCode");
        AccountService accountService = new AccountService();
        CatalogService catalogService = new CatalogService();
        Account account = accountService.getAccount(username,password);
        Account account1 = accountService.getAccount(username);
        if(account1==null){
            String message = "WRONG USERNAME AND PASSWORD";
            session.setAttribute("message", message);
            request.getRequestDispatcher(ERROR).forward(request, response);
        }

        if(token!=null&&token.equalsIgnoreCase(code)) {
            if (account == null) {
                String message = "Please enter a keyword to search for, then press the search button.";
                session.setAttribute("message", message);
                request.getRequestDispatcher(ERROR).forward(request, response);
            } else {
                //account.setPassword(null);
                // this bean is already registered as /actions/Account.action
                //List<Product> myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
                AccountDAOImpl accountDAO = new AccountDAOImpl();
                //String pwd = accountDAO.getPassword(username);
                String pwdReq = password;
                    authenticated = true;
                    session.setAttribute("account", account);
                    session.setAttribute("authenticated", authenticated);
                    request.getRequestDispatcher(MAIN).forward(request, response);
                    session.setAttribute("verificationWrong", null);
                    String message = "Wrong password or account";
                    session.setAttribute("message", message);
                    request.getRequestDispatcher(ERROR).forward(request, response);

            }
        }else{
            String warning = "Please enter the correct verification code";
            session.setAttribute("verificationWrong",warning);
            request.getRequestDispatcher(SIGNON).forward(request,response);
        }
    }
}
