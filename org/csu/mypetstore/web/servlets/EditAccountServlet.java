package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CatalogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditAccountServlet extends HttpServlet {
    private  static final String EDIT_ACCOUNT_FORM = "/WEB-INF/jsp/account/EditAccountForm.jsp";
    private static final String ERROR = "/WEB-INF/jsp/common/Error.jsp";

    Account account = new Account();
    AccountService accountService = new AccountService();
    CatalogService catalogService = new CatalogService();

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        account = accountService.getAccount(username);
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
        accountService.updateAccount(account);
        account = accountService.getAccount(account.getUsername());
        //List <Product> myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId());
        session.setAttribute("username",username);
        session.setAttribute("account",account);
        request.getRequestDispatcher(EDIT_ACCOUNT_FORM).forward(request,response);
    }
}
