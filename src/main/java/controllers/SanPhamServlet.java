package controllers;

import entities.NhanVien;
import entities.SanPham;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.SanPhamRepo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet({
        "/san-pham/index",
        "/san-pham/create",
        "/san-pham/store",
        "/san-pham/edit",
        "/san-pham/update",
        "/san-pham/delete"
})
public class SanPhamServlet extends HttpServlet {

    private SanPhamRepo spRepo;

    public SanPhamServlet() {
        this.spRepo = new SanPhamRepo();
    }

    @Override
    protected void doGet(HttpServletRequest  req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.contains("create")) {
            this.create(req, resp);
        } else if (uri.contains("edit")) {
            this.edit(req, resp);
        } else if (uri.contains("delete")) {
            this.delete(req, resp);
        } else {
            this.index(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.contains("store")) {
            this.store(req, resp);
        } else if (uri.contains("update")) {
            this.update(req, resp);
        } else {
            //
        }
    }

    public void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/san_pham/create.jsp").forward(req, resp);
    }

    public void store(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ma = req.getParameter("ma");
        String ten = req.getParameter("ten");
        int trangThai = Integer.parseInt(req.getParameter("trangThai"));
        SanPham sp = new SanPham(0,ma, ten, trangThai);
        this.spRepo.insertSp(sp);
        resp.sendRedirect("/san-pham/index");
    }

    public void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        SanPham sp = this.spRepo.findById(id);
       if (sp != null) {
           req.setAttribute("sp", sp);
           req.getRequestDispatcher("/views/san_pham/edit.jsp").forward(req, resp);
       } else {
           resp.sendRedirect("/san-pham/index");
       }

    }

    public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ma = req.getParameter("ma");
        String ten = req.getParameter("ten");
        int id = Integer.parseInt(req.getParameter("id"));
        int trangThai = Integer.parseInt(req.getParameter("trangThai"));
        SanPham sp = new SanPham(id, ma, ten, trangThai);
        this.spRepo.updateSp(sp);
        resp.sendRedirect("/san-pham/index");
    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        SanPham sp = this.spRepo.findById(id);
        this.spRepo.deleteSp(sp);
        resp.sendRedirect("/san-pham/index");
    }

    public void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search") != null ? req.getParameter("search").trim() : "";
        Integer trangThai = null;
        try {
            trangThai = Integer.parseInt(req.getParameter("trangThai"));
        } catch (NumberFormatException e) {
            //
        }
        String pageS = req.getParameter("page");
        String limitS = req.getParameter("limit");

        int page = pageS == null || pageS.trim().length() == 0 ? 1 : Integer.parseInt(pageS);
        int limit = limitS == null || limitS.trim().length() == 0 ? 5 : Integer.parseInt(limitS);

        List<SanPham> sp = this.spRepo.findAll(search, trangThai, Optional.of(page), Optional.of(limit));
        int count = this.spRepo.getCountBySearch(search, trangThai);
        int totalPage = (count + limit - 1) / limit;

        if (search.length() != 0) {
            req.setAttribute("search", search);
        }
        if (trangThai != null) {
            req.setAttribute("trangThai", trangThai);
        }

        req.setAttribute("sp", sp);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPage", totalPage);
        req.getRequestDispatcher("/views/san_pham/index.jsp").forward(req, resp);
    }
}
