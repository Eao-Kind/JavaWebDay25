package cn.itcast.bookstore.user.service;

import cn.itcast.bookstore.user.dao.UserDao;
import cn.itcast.bookstore.user.domain.User;
import cn.itcast.bookstore.user.web.servlet.UserException;

/**
 * User业务层
 */
public class UserService {
    UserDao userDao = new UserDao();

    /**
     * 注册功能
     *
     * @param form
     * @throws UserException
     */
    public void regist(User form) throws UserException {
        User user = userDao.findByUsername(form.getUsername());
        if (user != null) throw new UserException("用户名已被注册");

        user = userDao.findByEmail(form.getEmail());
        if (user != null) throw new UserException("email已被注册");

        userDao.add(form);
    }

    /**
     * 激活功能
     *
     * @param code
     * @throws UserException
     */
    public void action(String code) throws UserException {
        User user = userDao.findBycode(code);
        if (user == null) throw new UserException("激活码无效！");
        if (user.isState()) throw new UserException("用户已经激活！");
        userDao.updataState(user.getUid(), true);
    }

    /**
     * 登录功能
     *
     * @param form
     * @return user
     * @throws UserException
     */
    public User login(User form) throws UserException {
        // username查询得到user(null则抛e)——比较密码(错误抛e)——查看状态(未激活抛e)——返回User
        User user = userDao.findByUsername(form.getUsername());
        if (user == null) throw new UserException("用户名不存在");
        if (!user.getPassword().equals(form.getPassword())) throw new UserException("密码错误!");
        if (!user.isState()) throw new UserException("尚未激活！");
        return user;
    }
}
