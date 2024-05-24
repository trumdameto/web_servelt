package controllers;

import entities.HoaDon;
import entities.KhachHang;
import entities.NhanVien;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import repositories.HoaDonRepo;
import repositories.KhachHangRepo;
import repositories.NhanVienRepo;

import java.io.IOException;
import java.util.*;

@WebServlet({
        "/hoa-don/index",
        "/hoa-don/create",
        "/hoa-don/store",
        "/hoa-don/edit",
        "/hoa-don/update",
        "/hoa-don/delete",
})
public class HoaDonServlet extends HttpServlet {
    private final HoaDonRepo hdRepo;
    private final NhanVienRepo nvRepo;
    private final KhachHangRepo khRepo;

    public HoaDonServlet() {
        this.hdRepo = new HoaDonRepo();
        this.nvRepo = new NhanVienRepo();
        this.khRepo = new KhachHangRepo();
    }

    public void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, ServletException {
        String uri = request.getRequestURI();
        if (uri.contains("create")) {
            this.create(request, response);
        } else if (uri.contains("edit")) {
            this.edit(request, response);
        } else if (uri.contains("delete")) {
            this.delete(request, response);
        } else {
            this.index(request, response);
        }
    }

    public void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, ServletException {
        String uri = request.getRequestURI();
        if (uri.contains("store")) {
            this.store(request, response);
        } else if (uri.contains("update")) {
            this.update(request, response);
        } else {
            //
        }
    }

    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<Integer, String> mapNV = new HashMap<>();
        Map<Integer, String> mapKH = new HashMap<>();
        for (NhanVien nv : this.nvRepo.findAll()) {
            mapNV.put(nv.getId(), nv.getTen());
        }
        for (KhachHang kh : this.khRepo.findAll()) {
            mapKH.put(kh.getId(), kh.getTen());
        }

        request.setAttribute("listNV", mapNV);
        request.setAttribute("listKH", mapKH);
        request.setAttribute("data", this.hdRepo.findAll());
        request.getRequestDispatcher("/views/hoa_don/index.jsp").forward(request, response);
    }

    public void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<NhanVien> listNV = nvRepo.findAll();
        List<KhachHang> listKH = khRepo.findAll();
        request.setAttribute("listNV", listNV);
        request.setAttribute("listKH", listKH);
        request.getRequestDispatcher("/views/hoa_don/create.jsp").forward(request, response);
    }

    public void store(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HoaDon hd = new HoaDon();
        try {
            // Khai báo một DateConverter để chuyển đổi từ chuỗi ngày thành đối tượng Date
            DateConverter dateConverter = new DateConverter(null);
            // Đặt định dạng cho DateConverter
            dateConverter.setPattern("yyyy-MM-dd");
            // Đăng ký DateConverter với BeanUtils
            ConvertUtils.register(dateConverter, Date.class);
            BeanUtils.populate(hd, request.getParameterMap());
            this.hdRepo.insert(hd);
            response.sendRedirect("/hoa-don/index");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        HoaDon kt = hdRepo.findById(id);
        List<NhanVien> listNV = nvRepo.findAll();
        List<KhachHang> listKH = khRepo.findAll();
        if (kt == null) {
            response.sendRedirect("/hoa-don/index");
        } else {
            request.setAttribute("listNV", listNV);
            request.setAttribute("listKH", listKH);
            request.setAttribute("kt", kt);
            request.getRequestDispatcher("/views/hoa_don/edit.jsp").forward(request, response);
        }
    }


    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HoaDon hd = new HoaDon();
        try {
            // Khai báo một DateConverter để chuyển đổi từ chuỗi ngày thành đối tượng Date
            DateConverter dateConverter = new DateConverter(null);
            // Đặt định dạng cho DateConverter
            dateConverter.setPattern("yyyy-MM-dd");
            // Đăng ký DateConverter với BeanUtils
            ConvertUtils.register(dateConverter, Date.class);
            BeanUtils.populate(hd, request.getParameterMap());
            this.hdRepo.update(hd);
            response.sendRedirect("/hoa-don/index");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        HoaDon hd = hdRepo.findById(id);
        hdRepo.delete(hd);
        response.sendRedirect("/hoa-don/index");
    }

}
