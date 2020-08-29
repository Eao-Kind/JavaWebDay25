package cn.itcast.bookstore.user.web.servlet;

import cn.itcast.bookstore.cart.domain.Cart;
import cn.itcast.bookstore.user.domain.User;
import cn.itcast.bookstore.user.service.UserService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * User表述层
 */
public class UserServlet extends BaseServlet {
    private UserService userService = new UserService();

    /**
     * 激活功能
     *
     * @param request
     * @param response
     * @return
     */
    public String active(HttpServletRequest request, HttpServletResponse response) {
        // 获取激活码——调用service方法激活（异常信息到request域，转发到msg.jsp）——成功信息转到msg.jsp
        String code = request.getParameter("code");
        try {
            userService.action(code);
            request.setAttribute("msg", "激活成功！");
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
        }
        return "f://jsps/msg.jsp";
    }

    /**
     * 注册功能
     *
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws IOException
     */
    public String regist(HttpServletRequest request, HttpServletResponse response) {
        /*
         * 封装表单数据到form对象,补全uid,code,输入校验,调用service方法完成注册(错误信息转发),发邮件,保存成功信息
         */
        User form = CommonUtils.toBean(request.getParameterMap(), User.class);
        form.setUid(CommonUtils.uuid());
        form.setCode(CommonUtils.uuid() + CommonUtils.uuid());
        //输入的字段校验
        Map<String, String> errors = new HashMap<String, String>();
        String username = form.getUsername();
        if (username == null || username.trim().isEmpty()) {
            errors.put("username", "用户名不能未空");
        } else if (username.length() < 3 || username.length() > 10) {
            errors.put("username", "用户名长度必须在3-10之间1");
        }
        String password = form.getPassword();
        if (password == null || password.trim().isEmpty()) {
            errors.put("password", "密码不能未空");
        } else if (password.length() < 3 || password.length() > 10) {
            errors.put("password", "密码长度必须在3-10之间1");
        }
        String email = form.getEmail();
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "email不能未空");
        } else if (!email.matches("\\w+@\\w+\\.\\w+")) {
            errors.put("email", "email格式错误");
        }

        if (errors.size() > 0) {
            request.setAttribute("errors", errors); // 保存错误信息
            request.setAttribute("form", form);  //保存表单数据
            return "f:/jsps/user/regist.jsp"; // 转发
        }
        try {
            userService.regist(form); // 校验通关了，进行注册
            //发邮件
            Properties props = new Properties();
            props.load(this.getClass().getClassLoader().
                    getResourceAsStream("email_template.properties"));
            String host = props.getProperty("host");
            String uname = props.getProperty("uname");
            String pwd = props.getProperty("pwd");
            String from = props.getProperty("from");
            String to = form.getEmail();
            String subject = props.getProperty("subject");
            String content = props.getProperty("content");
            content = MessageFormat.format(content, form.getCode()); // 替换{0}
            Session session = MailUtils.createSession(host, uname, pwd);
            Mail mail = new Mail(from, to, subject, content); // 创建邮件对象
            try {
                MailUtils.send(session, mail);// 发送邮件
            } catch (MessagingException e) {
                System.out.println(e);
            }
            request.setAttribute("msg", "恭喜，注册成功!请到邮箱激活");
            return "f:/jsps/msg.jsp";
        } catch (UserException | IOException e) { // 注册失败，可能是用户名/邮箱重复
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("form", form);
            return "f:/jsps/user/regist.jsp"; // 转发
        }
    }

    /**
     * 登录功能
     */
    public String login(HttpServletRequest request, HttpServletResponse response) {
        // 封装表单数据——输入校验(略)——调用service方法完成激活(错误抛e转发到login.jsp)——保存信息到session重定向到index.jsp
        User form = CommonUtils.toBean(request.getParameterMap(), User.class);
        try {
            User user = userService.login(form);
            request.getSession().setAttribute("session_user", user);
            //添加购物车
            request.getSession().setAttribute("cart", new Cart());
            return "r:/index.jsp";
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("form", form); // 保存表单数据
            return "f://jsps/user/login.jsp";
        }
    }

    /**
     * 退出功能
     *
     * @param request
     * @param response
     * @return
     */
    public String quit(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate(); // 销毁
        return "r:/index.jsp";
    }
}
