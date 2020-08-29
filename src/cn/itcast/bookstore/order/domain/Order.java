package cn.itcast.bookstore.order.domain;

import cn.itcast.bookstore.user.domain.User;

import java.util.Date;
import java.util.List;

public class Order {
    private String oid;
    private Date ordertime; // 下单时间
    private double total; //  合计
    private int state; // 订单状态 1为付款 2付款未发货 3已发未确认 4已确认
    private User owner; // 订单所有者
    private String address; // 收获地址
    private List<OrderItem> orderItemList; // 订单条目

    @Override
    public String toString() {
        return "Order{" +
                "oid='" + oid + '\'' +
                ", ordertime=" + ordertime +
                ", total=" + total +
                ", state=" + state +
                ", owner=" + owner +
                ", address='" + address + '\'' +
                ", orderItemList=" + orderItemList +
                '}';
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
