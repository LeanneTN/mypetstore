package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CatalogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class NewAccountServlet extends HttpServlet {
    private  static final String SIGNON = "/WEB-INF/jsp/account/SignonForm.jsp";
    private static final String ERROR = "/WEB-INF/jsp/common/Error.jsp";
    private static final String NEW_ACCOUNT = "/WEB-INF/jsp/account/NewAccountForm.jsp";

    Account account = new Account();
    AccountService accountService = new AccountService();
    CatalogService catalogService = new CatalogService();
    private Boolean authenticated;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String token =(String) session.getAttribute(KAPTCHA_SESSION_KEY);
        //删除验证码防止重复操作
        session.removeAttribute(KAPTCHA_SESSION_KEY);
        String code = request.getParameter("verificationCode");

        if(token!=null&&token.equalsIgnoreCase(code)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String repeatedPassword = request.getParameter("repeatedPassword");
            String firstName = request.getParameter("account.firstName");
            String lastName = request.getParameter("account.lastName");
            String email = request.getParameter("account.email");
            String phone = request.getParameter("account.phone");
            String address1 = request.getParameter("account.address1");
            String address2 = request.getParameter("account.address2");
            String city = request.getParameter("account.city");
            String state = request.getParameter("account.state");
            String zip = request.getParameter("account.zip");
            String country = request.getParameter("account.country");
        /*if(password!=repeatedPassword){
            String message = "The two passwords are inconsistent";
            session.setAttribute("message",message);
            request.getRequestDispatcher(ERROR).forward(request,response);
        }else {
            account.setPassword(password);
        }*/
            account.setPassword(password);
            account.setUsername(username);
            account.setFirstName(firstName);
            account.setLastName(lastName);
            account.setEmail(email);
            account.setPhone(phone);
            account.setAddress1(address1);
            account.setAddress2(address2);
            account.setCity(city);
            account.setState(state);
            account.setZip(zip);
            account.setCountry(country);
            account.setFavouriteCategoryId("BIRDS");
            account.setLanguagePreference("chinese");
            accountService.insertAccount(account);
            account = accountService.getAccount(account.getUsername(), account.getPassword());
            //List<Product> myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
            //authenticated = true;
            //List <Product> myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
            //session.setAttribute("authenticated",authenticated);
            //session.setAttribute("account",account);
            session.removeAttribute("verificationWrong");
            request.getRequestDispatcher(SIGNON).forward(request, response);
        }else{
            String warning = "Please enter the correct verification code";
            session.setAttribute("verificationWrong",warning);
            request.getRequestDispatcher(NEW_ACCOUNT).forward(request,response);
        }
    }
}
