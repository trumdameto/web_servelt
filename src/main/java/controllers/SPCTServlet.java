package controllers;

import entities.SanPham;
import entities.SanPhamChiTiet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.KichThuocRepo;
import repositories.MauSacRepository;
import repositories.SPCTRepo;
import repositories.SanPhamRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet({
		"/sp-ct/index",
		"/sp-ct/create",
		"/sp-ct/store",
		"/sp-ct/edit",
		"/sp-ct/update",
		"/sp-ct/delete"
})
public class SPCTServlet extends HttpServlet {

	private final SPCTRepo spctRepo;
	private final KichThuocRepo ktRepo;
	private final MauSacRepository msRepo;
	private final SanPhamRepo spRepo;

	public SPCTServlet() {
		this.spctRepo = new SPCTRepo();
		this.ktRepo = new KichThuocRepo();
		this.msRepo = new MauSacRepository();
		this.spRepo = new SanPhamRepo();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri;
		uri = req.getRequestURI();
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
		req.getRequestDispatcher("/views/san_pham_chi_tiet/create.jsp").forward(req, resp);
	}

	public void store(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String maSPCT = req.getParameter("maSPCT");
		Integer idKichThuoc = Integer.parseInt(req.getParameter("idKichThuoc"));
		Integer idMauSac = Integer.parseInt(req.getParameter("idMauSac"));
		Integer idSanPham = Integer.parseInt(req.getParameter("idSanPham"));
		Integer soLuong = Integer.parseInt(req.getParameter("soLuong"));
		Double donGia = Double.parseDouble(req.getParameter("donGia"));
		int trangThai = Integer.parseInt(req.getParameter("trangThai"));
		SanPhamChiTiet spct = new SanPhamChiTiet(0, idMauSac, idKichThuoc, idSanPham, maSPCT, soLuong, donGia, trangThai);
		this.spctRepo.insert(spct);
		resp.sendRedirect("/sp-ct/index");
	}

	public void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		SanPhamChiTiet sp = this.spctRepo.findById(id);
		if (sp != null) {
			req.setAttribute("list", sp);
			req.getRequestDispatcher("/views/san_pham_chi_tiet/edit.jsp").forward(req, resp);
		} else {
			resp.sendRedirect("/sp-ct/index");
		}

	}

	public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		int idKichThuoc = Integer.parseInt(req.getParameter("idKichThuoc"));
		int idMauSac = Integer.parseInt(req.getParameter("idMauSac"));
		int idSanPham = Integer.parseInt(req.getParameter("idSanPham"));
		int soLuong = Integer.parseInt(req.getParameter("soLuong"));
		int trangThai = Integer.parseInt(req.getParameter("trangThai"));
		String maSPCT = req.getParameter("maSPCT");
		Double donGia = Double.parseDouble(req.getParameter("donGia"));

		SanPhamChiTiet sp = new SanPhamChiTiet(id, idMauSac, idKichThuoc, idSanPham, maSPCT, soLuong, donGia, trangThai);
		this.spctRepo.update(sp);
		resp.sendRedirect("/sp-ct/index");
	}

	public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		SanPhamChiTiet spct = this.spctRepo.findById(id);
		this.spctRepo.delete(spct);
		resp.sendRedirect("/sp-ct/index");
	}

	public void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer idSP = null;
		try {
			idSP = Integer.parseInt(req.getParameter("san_pham_id"));
		} catch (NumberFormatException e) {
			//
		}

		if (idSP == null) {
			resp.sendRedirect("/san-pham/index");
		} else {
			SanPham sp = SanPhamRepo.findById(idSP);
			List<SanPhamChiTiet> ds = this.spctRepo.findSpctByIdSp(idSP);
			List<Integer> listMSId = new ArrayList<>();
			List<Integer> listKTId = new ArrayList<>();
			for (SanPhamChiTiet spct : ds) {
				listMSId.add(spct.getIdMauSac());
				listKTId.add(spct.getIdKichThuoc());
			}

			HashMap<Integer, String> listMS = this.msRepo.findTenByIds(listMSId);
			HashMap<Integer, String> listKT = this.ktRepo.findTenByIds(listKTId);

			req.setAttribute("data", ds);
			req.setAttribute("sanPham", sp);
			req.setAttribute("listMS", listMS);
			req.setAttribute("listKT", listKT);
			req.getRequestDispatcher("/views/san_pham_chi_tiet/index.jsp").forward(req, resp);
		}
	}
}
