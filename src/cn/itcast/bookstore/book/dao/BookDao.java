package cn.itcast.bookstore.book.dao;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BookDao {
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 查找所有书
     *
     * @return
     */
    public List<Book> findAll() { // 查询未被删除的图书
        String sql = "select * from book where del=0";
        try {
            return qr.query(sql, new BeanListHandler<Book>(Book.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据分类查找书
     *
     * @param cid
     * @return
     */
    public List<Book> findByCategory(String cid) {
        String sql = "select * from book where cid=?";
        try {
            return qr.query(sql, new BeanListHandler<Book>(Book.class), cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据bid查找书
     *
     * @param bid
     * @return
     */
    public Book finByBid(String bid) {
        String sql = "select * from book where bid=?";
        try {
            Map<String, Object> map = qr.query(sql, new MapHandler(), bid);
            Category category = CommonUtils.toBean(map, Category.class);
            Book book = CommonUtils.toBean(map, Book.class);
            book.setCategory(category);
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据cid获取数量
     *
     * @param cid
     * @return
     */
    public int getCountByCid(String cid) {
        String sql = "select count(*) from book where cid=?";
        try {
            Number cnt = (Number) qr.query(sql, new ScalarHandler(), cid);
            return cnt.intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加书
     *
     * @param book
     */
    public void add(Book book) {
        String sql = "insert into book values(?,?,?,?,?,?) ";
        Object[] params = {book.getBid(), book.getBname(), book.getPrice(),
                book.getAuthor(), book.getImage(), book.getCategory().getCid()};
        try {
            qr.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除图书
     */
    public void delete(String bid) {
        String sql = "update book set del=true where bid=?";
        try {
            qr.update(sql, bid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void edit(Book book) {
        String sql = "update book set bname=?, price=?, author=?, image=?, cid=? where bid=? ";
        Object[] params = {book.getBname(), book.getPrice(),
                book.getAuthor(), book.getImage(), book.getCategory().getCid(), book.getBid()};
        try {
            qr.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
