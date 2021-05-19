package controllers.follows;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

@WebServlet("/follows/destroy")
public class FollowsDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowsDestroyServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");

        if(_token != null && _token.equals(request.getSession().getId())){
            EntityManager em = DBUtil.createEntityManager();

            Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");
            String employeeCode = request.getParameter("employeeCode");
            Follow f = (Follow) em.createNamedQuery("getFollower")
                    .setParameter("login_employee", login_employee)
                    .setParameter("employeeCode", employeeCode)
                    .getSingleResult();

            em.getTransaction().begin();
            em.remove(f);
            em.getTransaction().commit();
            em.close();

            response.sendRedirect(request.getContextPath() + "/employees/list");
        }
    }

}
