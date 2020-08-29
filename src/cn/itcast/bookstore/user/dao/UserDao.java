package cn.itcast.bookstore.user.dao;

import cn.itcast.bookstore.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;


public class UserDao {
    private QueryRunner qr = new TxQueryRunner();

    public User findByUsername(String username) {
        try {
            String sql = "select * from tb_user where username=?";
            System.out.println(username);

            return qr.query(sql, new BeanHandler<User>(User.class), username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByEmail(String email) {
        try {
            String sql = "select * from tb_user where email=?";
            return qr.query(sql, new BeanHandler<User>(User.class), email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(User user) {
        try {
            String sql = "insert into tb_user values(?,?,?,?,?,?)";
            Object[] param = {user.getUid(), user.getUsername(),
                    user.getPassword(), user.getEmail(),
                    user.getCode(), user.isState()};
            qr.update(sql, param);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 根据激活码查询
    public User findBycode(String code) {
        try {
            String sql = "select * from tb_user where code=?";
            return qr.query(sql, new BeanHandler<User>(User.class), code);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 修改指定用户激活状态
    public void updataState(String uid, boolean state) {
        try {
            String sql = "update tb_user set state=? where uid=?";
            qr.update(sql, state, uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
