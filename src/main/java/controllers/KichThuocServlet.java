package controllers;


import entities.KichThuoc;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.KichThuocRepo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet({
        "/kich-thuoc/index",
        "/kich-thuoc/create",
        "/kich-thuoc/store",
        "/kich-thuoc/edit",
        "/kich-thuoc/update",
        "/kich-thuoc/delete"
})
public class KichThuocServlet extends HttpServlet {
    private KichThuocRepo ktRepo;

    public KichThuocServlet() {
        this.ktRepo = new KichThuocRepo();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        req.getRequestDispatcher("/views/kich_thuoc/create.jsp").forward(req, resp);
    }

    public void store(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ma = req.getParameter("ma");
        String ten = req.getParameter("ten");
        int trangThai = Integer.parseInt(req.getParameter("trangThai"));
        KichThuoc kt = new KichThuoc(0, ma, ten, trangThai);
        this.ktRepo.insertKt(kt);
        resp.sendRedirect("/kich-thuoc/index");
    }

    public void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        KichThuoc kt = this.ktRepo.findById(id);
        if (kt != null) {
            req.setAttribute("kt", kt);
            req.getRequestDispatcher("/views/kich_thuoc/edit.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/kich-thuoc/index");
        }
    }

    public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String ma = req.getParameter("ma");
        String ten = req.getParameter("ten");
        int trangThai = Integer.parseInt(req.getParameter("trangThai"));

        KichThuoc kt = new KichThuoc(id, ma, ten, trangThai);
        this.ktRepo.updateKt(kt);

        resp.sendRedirect("/kich-thuoc/index");
    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        KichThuoc kt = this.ktRepo.findById(id);
        this.ktRepo.deleteKt(kt);
        resp.sendRedirect("/kich-thuoc/index");
    }

    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search") != null ? request.getParameter("search").trim():"";
        Integer trangThai = null;
        try {
             trangThai = Integer.parseInt(request.getParameter("trangThai"));
        } catch (NumberFormatException e){
            //
        }
        String pageS = request.getParameter("page");
        String limitS = request.getParameter("limit");

        int page = pageS == null || pageS .trim().length() == 0 ? 1 : Integer.parseInt(pageS);
        int limit = limitS == null || limitS .trim().length() == 0 ? 5 : Integer.parseInt(limitS);

        List<KichThuoc> ds = this.ktRepo.findAll(search,trangThai,Optional.of(page), Optional.of(limit));
        int count = this.ktRepo.getCountBySearch(search,trangThai);
        int totalPage = (count + limit -1)/limit;

        if (search.length() != 0) {
            request.setAttribute("search", search);
        }
        if (trangThai != null) {
            request.setAttribute("trangThai", trangThai);
        }

        request.setAttribute("data", ds);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPage", totalPage);
        request.getRequestDispatcher("/views/kich_thuoc/index.jsp").forward(request, response);
    }
}
