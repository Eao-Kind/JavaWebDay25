package cn.itcast.bookstore.order.service;

import cn.itcast.bookstore.order.dao.OrderDao;
import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.jdbc.JdbcUtils;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private OrderDao orderDao = new OrderDao();

    /**
     * 添加订单
     *
     * @param order
     */
    public void add(Order order) {
        try {
            JdbcUtils.beginTransaction(); // 开启事务
            orderDao.addOrder(order); // 添加订单
            orderDao.addOrderItemList(order.getOrderItemList()); //添加订单条目
            JdbcUtils.commitTransaction(); // 结束事务
        } catch (Exception e) {
            try {
                JdbcUtils.rollbackTransaction(); // 回滚事务
            } catch (SQLException e1) {
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 我的订单
     *
     * @param uid
     * @return
     */
    public List<Order> myOrders(String uid) {
        return orderDao.findByUid(uid);
    }

    public Order load(String oid) {
        return orderDao.load(oid);
    }

    /**
     * 修改订单状态
     *
     * @param oid
     * @throws OrderException
     */
    public void confirm(String oid) throws OrderException {
        // 校验订单状态若！3则抛
        int state = orderDao.getStateByOid(oid);
        if (state != 3) {
            throw new OrderException("订单确定失败");
        }
        orderDao.updateState(oid, 4);

    }
}

