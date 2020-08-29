package cn.itcast.bookstore.order.dao;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.bookstore.order.domain.OrderItem;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDao {
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 加载订单
     *
     * @param oid
     * @return
     */
    public Order load(String oid) {
        String sql = "select * from orders where oid=?";
        try {
            Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
            loadOrderItems(order);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加订单
     *
     * @param order
     */
    public void addOrder(Order order) {
        try {
            String sql = "insert into orders values(?,?,?,?,?,?)";
            Timestamp timestamp = new Timestamp(order.getOrdertime().getTime()); //util_data转成sql的timestamp
            Object[] params = {order.getOid(), timestamp, order.getTotal(),
                    order.getState(), order.getOwner().getUid(), order.getAddress()};
            qr.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 插入订单条目
     *
     * @param orderItemsList
     */
    public void addOrderItemList(List<OrderItem> orderItemsList) {
        String sql = "insert into orderitem values(?,?,?,?,?)";
        Object[][] params = new Object[orderItemsList.size()][];
        for (int i = 0; i < orderItemsList.size(); i++) {
            OrderItem item = orderItemsList.get(i);
            params[i] = new Object[]{item.getIid(), item.getCount(), item.getSubtotal(),
                    item.getOrder().getOid(), item.getBook().getBid()};
        }
        try {
            qr.batch(sql, params); // 每个dim执行一次,执行orderItemlist.size()次
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据uid查询订单
     *
     * @param uid
     * @return
     */
    public List<Order> findByUid(String uid) {
        String sql = "select * from  orders where uid=?";
        try {
            List<Order> orderList = qr.query(sql, new BeanListHandler<Order>(Order.class), uid); // 所有订单
            for (Order order : orderList) {
                loadOrderItems(order); // 为order对象添加所有订单条目
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadOrderItems(Order order) {
        String sql = "select * from orderItem i, book b where i.bid=b.bid and oid=?"; // 多表查询
        try {
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), order.getOid());
            List<OrderItem> orderItemList = toOrderitemList(mapList);
            order.setOrderItemList(orderItemList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把mapList中每个Map转换成两个对象并建立关
     *
     * @param mapList
     * @return
     */
    private List<OrderItem> toOrderitemList(List<Map<String, Object>> mapList) {
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for (Map<String, Object> map : mapList) {
            OrderItem item = toOrderItem(map);
            orderItemList.add(item);
        }
        return orderItemList;
    }

    /**
     * 把一个Map转换成一个OrderItem对象
     *
     * @param map
     * @return
     */
    private OrderItem toOrderItem(Map<String, Object> map) {
        OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
        Book book = CommonUtils.toBean(map, Book.class);
        orderItem.setBook(book);
        return orderItem;
    }

    /**
     * 根据oid查询状态
     *
     * @param oid
     * @return
     */
    public int getStateByOid(String oid) {
        try {
            String sql = "select state from orders where oid=?";
            return (Integer) qr.query(sql, new ScalarHandler(), oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新状态
     *
     * @param oid
     * @param state
     */
    public void updateState(String oid, int state) {
        String sql = "update orders set state=? where oid=?";
        try {
            qr.update(sql, state, oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
