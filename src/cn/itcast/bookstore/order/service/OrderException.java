package cn.itcast.bookstore.order.service;

public class OrderException extends Exception {
    public OrderException(String message) {
        super(message);
    }

    public OrderException() {
        super();
    }
}
