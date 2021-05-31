package controllers.likes;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Like;
import utils.DBUtil;

@WebServlet("/likes/destroy")
public class LikesDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LikesDestroyServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())){
            EntityManager em = DBUtil.createEntityManager();
            Integer report_id = Integer.parseInt(request.getParameter("report_id"));
            Like l = (Like) em.createNamedQuery("searchLikeData")
                    .setParameter("report_id", report_id)
                    .getSingleResult();

            em.getTransaction().begin();
            em.remove(l);
            em.getTransaction().commit();
            em.close();

            response.sendRedirect(request.getContextPath() + "/reports/show?id=" + report_id);
        }
    }

}
