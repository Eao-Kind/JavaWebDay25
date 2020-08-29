package cn.itcast.bookstore.category.service;

import cn.itcast.bookstore.book.dao.BookDao;
import cn.itcast.bookstore.category.dao.CategoryDao;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.bookstore.category.web.servlet.admin.CategoryException;

import java.util.List;

public class CategoryService {
    private CategoryDao categoryDao = new CategoryDao();
    private BookDao bookDao = new BookDao();

    public void edit(Category category) {
        categoryDao.edit(category);
    }

    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    public void add(Category category) {
        categoryDao.add(category);
    }

    public void delete(String cid) throws CategoryException {
        if (bookDao.getCountByCid(cid) > 0) {
            throw new CategoryException("该分类下还有图书，不能删除！");
        }
        categoryDao.delete(cid);
    }

    public Category load(String cid) {
        return categoryDao.load(cid);
    }
}
