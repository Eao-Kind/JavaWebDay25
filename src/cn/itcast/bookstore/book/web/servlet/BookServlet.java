package cn.itcast.bookstore.book.web.servlet;

import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BookServlet")
public class BookServlet extends BaseServlet {
    private BookService bookService = new BookService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("bookList", bookService.findAll());
        return "f://jsps/book/list.jsp";
    }

    public String findByCategory(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        request.setAttribute("bookList", bookService.findByCategory(cid));
        return "f://jsps/book/list.jsp";
    }

    public String load(HttpServletRequest request, HttpServletResponse response) {
        String bid = request.getParameter("bid");
        request.setAttribute("book", bookService.load(bid));
        return "f:/jsps/book/desc.jsp";
    }
}
