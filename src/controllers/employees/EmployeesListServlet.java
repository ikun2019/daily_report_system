package controllers.employees;

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
import utils.DBUtil;

@WebServlet("/employees/list")
public class EmployeesListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EmployeesListServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e){}

        List<Employee> employees = em.createNamedQuery("getAllEmployees", Employee.class)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        long employees_count = (long)em.createNamedQuery("getEmployeesCount", Long.class)
                .getSingleResult();

        //ログインしているユーザーがフォローしている人を取得
        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");
        List<String> followers = em.createNamedQuery("getAllFollowers", String.class)
                .setParameter("login_employee", login_employee)
                .getResultList();

        em.close();
        request.getSession().setAttribute("_token", request.getSession().getId());
        request.setAttribute("followers", followers);
        request.setAttribute("employees", employees);
        request.setAttribute("employees_count", employees_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/list.jsp");
        rd.forward(request, response);
    }

}
