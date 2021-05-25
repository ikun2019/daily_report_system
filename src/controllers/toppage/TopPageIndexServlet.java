package controllers.toppage;

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

@WebServlet("/index.html")
public class TopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TopPageIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch(Exception e){
            page = 1;
        }

        List<Report> reports = em.createNamedQuery("getMyAllReports", Report.class)
                .setParameter("employee", login_employee)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        long reports_count = (long)em.createNamedQuery("getMyReportsCount", Long.class)
                .setParameter("employee", login_employee)
                .getSingleResult();

        //ログインしているユーザーが所属している部署の従業員一覧を検索(自分より役職の低い従業員のみ抽出)
        try {
        List<Employee> subordinates = em.createNamedQuery("searchGroupMembers", Employee.class)
                .setParameter("employee_position", login_employee.getPosition())
                .setParameter("group", login_employee.getGroup_id())
                .getResultList();

        System.out.println("subordinatesの中身：" + subordinates);

        List<Report> subordinatesReports = em.createNamedQuery("getSubordinateReports", Report.class)
                .setParameter("employee_id", subordinates)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        System.out.println("subordinatesReportsの中身：" + subordinatesReports);

        request.setAttribute("subordinatesReports", subordinatesReports);
        request.setAttribute("page", page);
        } catch(Exception e) {}


        em.close();

        request.setAttribute("login_employee", login_employee);
        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);

        if(request.getSession().getAttribute("flush") != null){
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }

}
