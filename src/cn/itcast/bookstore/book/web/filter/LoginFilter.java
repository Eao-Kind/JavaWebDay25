package cn.itcast.bookstore.book.web.filter;

import cn.itcast.bookstore.user.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "Filter")
public class LoginFilter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        // 从session中获取用户信息——如果存在则放行否则转发道login.jsp
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        User user = (User) httpServletRequest.getSession().getAttribute("session_user");
        if (user != null) {
            chain.doFilter(req, resp);
        } else {
            httpServletRequest.setAttribute("msg", "您还未登录！");
            req.getRequestDispatcher("/jsps/user/login.jsp").forward(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
