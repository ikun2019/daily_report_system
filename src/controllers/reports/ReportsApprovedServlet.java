package controllers.reports;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;

@WebServlet("/reports/approved")
public class ReportsApprovedServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportsApprovedServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())){
            EntityManager em = DBUtil.createEntityManager();
            Integer destroy_report_id = Integer.parseInt(request.getParameter("destroy_report_id"));

            Report r = em.createNamedQuery("searchReport", Report.class)
                    .setParameter("report_id", destroy_report_id)
                    .getSingleResult();

            r.setApproval(null);
            r.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();
            response.sendRedirect(request.getContextPath() + "/reports/show?id=" + destroy_report_id);
        }
    }

}
