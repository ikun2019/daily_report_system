package controllers.follows;

import java.io.IOException;
import java.util.ArrayList;
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

@WebServlet("/follows/report_lists")
public class FollowsReportlistsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowsReportlistsServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //ログインしているユーザー情報を取得
        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");
        //フォロしているユーザーの取得
        List<String> followed_codes = em.createNamedQuery("getAllFollowers", String.class)
                .setParameter("login_employee", login_employee)
                .getResultList();

        List<Employee> followed_employees = new ArrayList<Employee>();
        for (String followed_code: followed_codes){
            followed_employees.add((Employee) em.createNamedQuery("searchEmployeeCode", Employee.class)
                    .setParameter("code", followed_code)
                    .getSingleResult()
                    );
        }

        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {}

        //レポートを配列に格納
        List<Report> followed_employee_reports = em.createNamedQuery("getFollowersReports", Report.class)
                .setParameter("employees", followed_employees)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();
        //レポートの数
        long reports_count = em.createNamedQuery("getFollowersReportsCount", Long.class)
                .setParameter("employees", followed_employees)
                .getSingleResult();

        em.close();

        request.setAttribute("followed_employee_reports", followed_employee_reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follows/report_lists.jsp");
        rd.forward(request, response);
    }

}
