package cn.itcast.bookstore.book.web.servlet.admin;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdminBookServlet")
public class AdminBookServlet extends BaseServlet {
    private BookService bookService = new BookService();
    private CategoryService categoryService = new CategoryService();

    /**
     * 查询所有图书
     *
     * @param request
     * @param response
     * @return
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("bookList", bookService.findAll());
        return "f://adminjsps/admin/book/list.jsp";
    }

    /**
     * 根据bid加载图书
     *
     * @param request
     * @param response
     * @return
     */
    public String load(HttpServletRequest request, HttpServletResponse response) {
        // 获取bid——通过bid得到book对象+所有分类——保存book到request域——转发到desc.jsp
        String bid = request.getParameter("bid");
        request.setAttribute("book", bookService.load(bid));
        request.setAttribute("categoryList", categoryService.findAll());
        return "f://adminjsps/admin/book/desc.jsp";
    }

    /**
     * 查询所有分类
     *
     * @param request
     * @param response
     * @return
     */
    public String addPre(HttpServletRequest request, HttpServletResponse response) {
        // 查询所有分类——保存到request——转发到add.jsp——使用下拉列表显示在表单
        request.setAttribute("categoryList", categoryService.findAll());
        return "f://adminjsps/admin/book/add.jsp";
    }

    /**
     * 删除图书
     *
     * @param request
     * @param response
     * @return
     */
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        String bid = request.getParameter("bid");
        bookService.delete(bid);
        return findAll(request, response);
    }

    /**
     * 编辑图书
     *
     * @param request
     * @param response
     * @return
     */
    public String edit(HttpServletRequest request, HttpServletResponse response) {
        Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        book.setCategory(category);
        bookService.edit(book);
        return findAll(request, response);
    }
}