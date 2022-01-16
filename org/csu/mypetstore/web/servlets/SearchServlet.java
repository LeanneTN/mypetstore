package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SearchServlet extends javax.servlet.http.HttpServlet{
    private  static final String SEARCH = "/WEB-INF/jsp/catalog/SearchProducts.jsp";
    private static final String ERROR = "/WEB-INF/jsp/common/Error.jsp";

    private String keyword;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        keyword = request.getParameter("keyword");
        HttpSession session = request.getSession();
        if (keyword == null || keyword.length() < 1) {
            session.setAttribute("message","Please enter a keyword to search for, then press the search button.");
            request.getRequestDispatcher(ERROR).forward(request,response);
        } else {
            CatalogService service = new CatalogService();
            List<Product> productList = service.searchProductList(keyword.toLowerCase());
            session.setAttribute("productList",productList);
        }
        request.getRequestDispatcher(SEARCH).forward(request,response);
    }
}
