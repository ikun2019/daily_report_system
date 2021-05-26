package controllers.reports;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

@WebServlet("/reports/approve")
public class ReportsApproveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsApproveServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())){
            EntityManager em = DBUtil.createEntityManager();

            Integer create_report_id = Integer.parseInt(request.getParameter("create_report_id"));
            Report r = em.createNamedQuery("searchReport", Report.class)
                    .setParameter("report_id", create_report_id)
                    .getSingleResult();

            System.out.println("rの中身：" + r);

            Employee employee = ((Employee)request.getSession().getAttribute("login_employee"));


            r.setApproval(employee.getId());
            r.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();
            response.sendRedirect(request.getContextPath() + "/reports/show?id=" + create_report_id);
        }

    }

}
