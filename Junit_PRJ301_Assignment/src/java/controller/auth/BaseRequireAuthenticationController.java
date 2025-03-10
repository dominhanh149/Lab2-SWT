/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.User;

/**
 *
 * @author FPTSHOP
 */
public abstract class BaseRequireAuthenticationController extends HttpServlet {

    private boolean isAuthentical(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("account");
        return (user != null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isAuthentical(req)) {
            User user = (User) req.getSession().getAttribute("account");
            doPost(req, resp, user);
        } else {
            resp.getWriter().print("access denied!!");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isAuthentical(req)) {
            User user = (User) req.getSession().getAttribute("account");
            doGet(req, resp, user);
        } else {
            resp.getWriter().print("access denied!!");
        }

    }

    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException;

    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException;

}
