package controllers.likes;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Like;
import utils.DBUtil;

@WebServlet("/likes/create")
public class LikesCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LikesCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");
        if(_token != null && _token.equals(request.getSession().getId())){
            EntityManager em = DBUtil.createEntityManager();
            Integer report_id = Integer.parseInt(request.getParameter("report_id"));
            Like l = new Like();

            l.setEmployee_id(login_employee);
            l.setReport_id(report_id);
            em.getTransaction().begin();
            em.persist(l);
            em.getTransaction().commit();
            em.close();

            response.sendRedirect(request.getContextPath() + "/reports/show?id=" + report_id);
        }
    }

}
