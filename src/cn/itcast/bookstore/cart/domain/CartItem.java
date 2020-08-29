package cn.itcast.bookstore.cart.domain;

import cn.itcast.bookstore.book.domain.Book;

import java.math.BigDecimal;

public class CartItem {
    private Book book; // 商品
    private int count; // 商品数量

    public double getSubtotal() {
        // 小计
        BigDecimal d1 = new BigDecimal(book.getPrice() + "");
        BigDecimal d2 = new BigDecimal(count + "");
        return d1.multiply(d2).doubleValue();
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
