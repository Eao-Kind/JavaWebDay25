package cn.itcast.bookstore.cart.web.servlet;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.bookstore.cart.domain.Cart;
import cn.itcast.bookstore.cart.domain.CartItem;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CartServlet")
public class CartServlet extends BaseServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public String clear(HttpServletRequest request, HttpServletResponse response) {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.clear();
        return "f://jsps/cart/list.jsp";
    }

    public String delete(HttpServletRequest request, HttpServletResponse response) {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        String bid = request.getParameter("bid");
        cart.delete(bid);
        return "f://jsps/cart/list.jsp";
    }

    /**
     * 添加条目
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 得到车——得到条目(书和数量)
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        // 得到条目——数量和bid
        String bid = request.getParameter("bid");
        Book book = new BookService().load(bid);
        int count = Integer.parseInt(request.getParameter("count"));
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setCount(count);
        // 添加道条目
        cart.add(cartItem);
        return "f://jsps/cart/list.jsp";
    }
}
