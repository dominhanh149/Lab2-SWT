/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manage;

import controller.auth.BaseRBACController;
import dal.ProductionPlanDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.ProductionPlan;
import model.User;

/**
 *
 * @author FPTSHOP
 */
public class ProductionPlanDeleteController extends BaseRBACController{

    @Override
    protected void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        ProductionPlanDBContext pdb = new ProductionPlanDBContext();
        String plid_raw = req.getParameter("plid");
        int plid = -1;
        if(plid_raw != null){
            plid = Integer.parseInt(plid_raw);
        }
        
        ProductionPlan plan = new ProductionPlan();
        plan.setId(plid);
        
        pdb.delete(plan);
        
        resp.sendRedirect("list");
        
    }

    @Override
    protected void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
