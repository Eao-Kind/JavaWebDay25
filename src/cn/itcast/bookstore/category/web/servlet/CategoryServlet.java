package cn.itcast.bookstore.category.web.servlet;

import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "CategoryServlet")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("categoryList", categoryService.findAll());
        return "f://jsps/left.jsp";
    }
}
