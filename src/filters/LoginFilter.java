package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Employee;

@WebFilter("/*")
public class LoginFilter implements Filter {

    public LoginFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String context_path = ((HttpServletRequest)request).getContextPath();
        String servlet_path = ((HttpServletRequest)request).getServletPath();

        if(!servlet_path.matches("/css.*")){
            HttpSession session = ((HttpServletRequest)request).getSession();

            //セッションスコープに登録された従業員情報を取得
            Employee e = (Employee)session.getAttribute("login_employee");

            if(!servlet_path.equals("/login")){
                if(e == null){
                    ((HttpServletResponse)response).sendRedirect(context_path + "/login");
                    return;
                }
                //従業員管理の機能は管理者のみが閲覧できるようにする
                if((servlet_path.matches("/employees/edit") || servlet_path.matches("/employees/index") || servlet_path.matches("/employees/new") || servlet_path.matches("/employees/show")) && e.getAdmin_flag() == 0){
                    ((HttpServletResponse)response).sendRedirect(context_path + "/");
                    return;
                }
            }else{
                // ログインしているのにログイン画面を表示させようとした場合は
                // システムのトップページにリダイレクト
                if(e != null){
                    ((HttpServletResponse)response).sendRedirect(context_path + "/");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

}
