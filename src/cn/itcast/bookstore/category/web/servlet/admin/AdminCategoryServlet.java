package cn.itcast.bookstore.category.web.servlet.admin;

import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryService();

    /**
     * 查找所有
     *
     * @param request
     * @param response
     * @return
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("categoryList", categoryService.findAll());
        return "f://adminjsps/admin/category/list.jsp";
    }

    /**
     * 添加
     *
     * @param request
     * @param response
     * @return
     */
    public String add(HttpServletRequest request, HttpServletResponse response) {
        // 封装表单数据——补全cid——service完成——调用findALl
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        category.setCid(CommonUtils.uuid());
        categoryService.add(category);
        return findAll(request, response);
    }

    /**
     * 删除
     *
     * @param request
     * @param response
     * @return
     */
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        // 获取cid———service完成——调用findALl
        String cid = request.getParameter("cid");
        try {
            categoryService.delete(cid);
            return findAll(request, response);
        } catch (CategoryException e) {
            request.setAttribute("msg", e.getMessage());
            return "f://adminjsps/msg.jsp";
        }
    }

    /**
     * 加载分类
     *
     * @param request
     * @param response
     * @return
     */
    public String editPre(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        request.setAttribute("category", categoryService.load(cid));
        return "f://adminjsps/admin/category/mod.jsp";
    }

    /**
     * 修改分类
     *
     * @param request
     * @param response
     * @return
     */
    public String edit(HttpServletRequest request, HttpServletResponse response) {
        // 封装表单——调用service——调用findAll
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        categoryService.edit(category);
        return findAll(request, response);
    }
}
