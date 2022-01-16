package org.csu.mypetstore.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

public class VerificationCodeServlet extends HttpServlet {
    private static final String LocationPrefix = "/images/verificationcode/verificationcode/";
    private static final String LocationSubfix = ".png";
    private String verif_id;
    private String verif_path;
    private String verif_value;

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Random random = new Random();
        int locationId = random.nextInt(14) + 1;
        String location = LocationPrefix + locationId + LocationSubfix;
        session.setAttribute("location",location);
    }
}
