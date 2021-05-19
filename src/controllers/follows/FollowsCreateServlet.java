package controllers.follows;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

@WebServlet("/follows/create")
public class FollowsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FollowsCreateServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId()));
            EntityManager em = DBUtil.createEntityManager();

            Follow f = new Follow();

            //フォローした人のユーザー情報を取得
            String followCode = request.getParameter("followCode");

            System.out.println("followCode：" + followCode);

            f.setFollow_id((Employee)request.getSession().getAttribute("login_employee"));
            f.setFollowed_Code(followCode);
            f.setCreated_at(new Timestamp(System.currentTimeMillis()));
            f.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();
            em.close();

            response.sendRedirect(request.getContextPath() + "/employees/list");
    }

}
