package cn.itcast.bookstore.order.web.servlet;

import cn.itcast.bookstore.cart.domain.Cart;
import cn.itcast.bookstore.cart.domain.CartItem;
import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.bookstore.order.domain.OrderItem;
import cn.itcast.bookstore.order.service.OrderException;
import cn.itcast.bookstore.order.service.OrderService;
import cn.itcast.bookstore.user.domain.User;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "OrderServlet")
public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * 购买——添加订单
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //session中得到cart——生成order对象——service完成添加订单——保存order到request域——转发
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        Order order = new Order();
        order.setOid(CommonUtils.uuid()); // 订单编号
        order.setOrdertime(new Date()); // 下单时间
        order.setState(1); // 1:未付款
        User user = (User) request.getSession().getAttribute("session_user");
        order.setOwner(user);
        order.setTotal(cart.getTotal()); // 订单合计
        // 购物车条目转换为订单条目
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem oi = new OrderItem();
            oi.setIid(CommonUtils.uuid());
            oi.setCount(cartItem.getCount());
            oi.setBook(cartItem.getBook());
            oi.setSubtotal(cartItem.getSubtotal());
            oi.setOrder(order); // 所属订单
            orderItemList.add(oi);
        }
        order.setOrderItemList(orderItemList);
        cart.clear();
        orderService.add(order);
        request.setAttribute("order", order);
        return "/jsps/order/desc.jsp";
    }

    public String myOrders(HttpServletRequest request, HttpServletResponse response) {
        // Session中获取用户uid——根据uid调用service得到OrderList——保存到req域——转发到list.jsp
        User user = (User) request.getSession().getAttribute("session_user");
        List<Order> orderList = orderService.myOrders(user.getUid());
        request.setAttribute("orderList", orderList);
        return "f://jsps/order/list.jsp";
    }

    public String load(HttpServletRequest request, HttpServletResponse response) {
        // 得到oid——调用service得到Order——保存到req域——转发到desc
        request.setAttribute("order", orderService.load(request.getParameter("oid")));
        return "f://jsps/order/desc.jsp";
    }

    /**
     * 确认收货
     *
     * @param request
     * @param response
     * @return
     */
    public String confirm(HttpServletRequest request, HttpServletResponse response) {
        // 获取oid——调用service——有异常则保存
        String oid = request.getParameter("oid");
        try {
            orderService.confirm(oid);
            request.setAttribute("msg", "恭喜，交易成功");
        } catch (OrderException e) {
            request.setAttribute("msg", e.getMessage());
        }
        return "f://jsps/msg.jsp";
    }
}
