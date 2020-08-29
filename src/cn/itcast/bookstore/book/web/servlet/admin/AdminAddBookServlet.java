package cn.itcast.bookstore.book.web.servlet.admin;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.book.service.BookService;
import cn.itcast.bookstore.category.domain.Category;
import cn.itcast.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminAddBookServlet")
public class AdminAddBookServlet extends HttpServlet {
    private BookService bookService = new BookService();
    private CategoryService categoryService = new CategoryService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        // 上传三步——创建工厂——得到解析器——解析req对象
        DiskFileItemFactory factory = new DiskFileItemFactory(15 * 1024, new File("D:/temp")); // 缓存大小+临时目录
        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setFileSizeMax(15 * 1024); // 最多15kb
        try { // fileItemList中的数据封装到Book对象——普通字段封到Map——Map封装到Book对象
            List<FileItem> fileItemList = sfu.parseRequest(request);
            Map<String, String> map = new HashMap<String, String>();
            for (FileItem fileItem : fileItemList) {
                if (fileItem.isFormField()) {
                    map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
                }
            }
            Book book = CommonUtils.toBean(map, Book.class);
            book.setBid(CommonUtils.uuid());
            Category category = CommonUtils.toBean(map, Category.class);
            book.setCategory(category);
            // 保存上传路径——目录+名字（校验）——保存
            String savaPath = this.getServletContext().getRealPath("/book_img");
            String filename = CommonUtils.uuid() + "_" + fileItemList.get(1).getName();

            if (!filename.toLowerCase().endsWith("jpg")) { // 校验内容图片
                request.setAttribute("msg", "您上传的图片不是JPG扩展名!");
                request.setAttribute("categoryList", categoryService.findAll());
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
            }

            File destFile = new File(savaPath, filename);
            fileItemList.get(1).write(destFile);
            book.setImage("book_img/" + filename);

            Image image = new ImageIcon(destFile.getAbsolutePath()).getImage();
            if (image.getWidth(null) > 200 || image.getHeight(null) > 200) {
                destFile.delete(); // 删除图片
                request.setAttribute("msg", "您上传的文件超出了200*200");
                request.setAttribute("categoryList", categoryService.findAll());
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp");
                return;
            }

            bookService.add(book); // 添加书籍到数据库
            request.getRequestDispatcher("/AdminBookServlet?method=findAll").
                    forward(request, response);
        } catch (Exception e) {
            if (e instanceof FileUploadBase.FileSizeLimitExceededException) {
                request.setAttribute("msg", "您上传的文件超出了15kb");
                request.setAttribute("categoryList", categoryService.findAll());
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);

            }
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
