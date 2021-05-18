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
import utils.DBUtil;

@WebServlet("/follows/index")
public class FollowsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowsIndexServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //ログインしているユーザーの取得
        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");

        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) {}

        List<String> followers = em.createNamedQuery("getAllFollowers", String.class)
                .setParameter("login_employee", login_employee)
                .getResultList();

        System.out.println("followersリストの中身：" + followers);

        List<Employee> followersEmployee = new ArrayList<Employee>();

        for(String follower : followers){
            followersEmployee.add(em.createNamedQuery("searchEmployeeCode", Employee.class)
            .setParameter("code", follower)
            .getSingleResult());
        }

        long followers_count = em.createNamedQuery("getFollowersCount", Long.class)
                .getSingleResult();

        em.close();

        request.setAttribute("followers", followersEmployee);
        request.setAttribute("followers_count", followers_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follows/index.jsp");
        rd.forward(request, response);
    }

}
