package controllers.follows;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

@WebServlet("/follows/reports")
public class FollowsReportsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowsReportsServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer followerId = Integer.parseInt(request.getParameter("id"));
        EntityManager em = DBUtil.createEntityManager();
        Employee follower = em.createNamedQuery("searchEmployee", Employee.class)
                .setParameter("employee_id", followerId)
                .getSingleResult();

    //ページネーション
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) {}



        List<Report> followerReports = em.createNamedQuery("getFollowerReports", Report.class)
                .setParameter("employee", followerId)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

//        long followerReports_count = (long)em.createNamedQuery("getMyReportsCount", Long.class)
//                .setParameter("employee", followerId)
//                .getSingleResult();

        request.setAttribute("follower", follower);
        request.setAttribute("followerReports", followerReports);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follows/reports.jsp");
        rd.forward(request, response);
    }

}
