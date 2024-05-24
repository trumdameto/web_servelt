package controllers;


import entities.KhachHang;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.KhachHangRepo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet({
		"/khach-hang/index",
		"/khach-hang/create",
		"/khach-hang/store",
		"/khach-hang/edit",
		"/khach-hang/update",
		"/khach-hang/delete",
})
public class KhachHangServlet extends HttpServlet {

	private KhachHangRepo khRepo;

	public KhachHangServlet() {
		this.khRepo = new KhachHangRepo();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		if (url.contains("create")) {
			this.create(req, resp);
		} else if (url.contains("edit")) {
			this.edit(req, resp);
		} else if (url.contains("delete")) {
			this.delete(req, resp);
		} else {
			this.index(req, resp);
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		if (url.contains("store")) {
			this.store(req, resp);
		} else if (url.contains("update")) {
			this.update(req, resp);
		} else {
			//
		}
	}

	public void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String search = req.getParameter("search") != null ? req.getParameter("search").trim() : "";
		String soDT = req.getParameter("soDT") != null ? req.getParameter("soDT").trim() : "";
		Integer trangThai = null;
		try {
			trangThai = Integer.parseInt(req.getParameter("trangThai"));
		} catch (NumberFormatException e) {
			//
		}
		String pageS = req.getParameter("page");
		String limitS = req.getParameter("limitS");

		int page = pageS == null || pageS.trim().length() == 0 ? 1 : Integer.parseInt(pageS);
		int limit = limitS == null || limitS.trim().length() == 0 ? 5 : Integer.parseInt(limitS);

		List<KhachHang> kh = this.khRepo.findAll(search, soDT, trangThai, Optional.of(page), Optional.of(limit));
		int count = this.khRepo.getCountBySearch(search, soDT, trangThai);
		int totalPage = (count + limit - 1) / limit;

		if (search.length() != 0) {
			req.setAttribute("search", search);
		}
		if (soDT.length() != 0) {
			req.setAttribute("soDT", soDT);
		}
		if (trangThai != null) {
			req.setAttribute("trangThai", trangThai);
		}
		req.setAttribute("kh", kh);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("currentPage", page);

		req.getRequestDispatcher("/views/khach_hang/index.jsp").forward(req, resp);
	}

	public void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/khach_hang/create.jsp").forward(req, resp);
	}

	public void store(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ma = req.getParameter("ma");
		String ten = req.getParameter("ten");
		String sdt = req.getParameter("sdt");
		int trangThai = Integer.parseInt(req.getParameter("trangThai"));
		KhachHang kh = new KhachHang(0, ma, ten, sdt, trangThai);
		khRepo.insert(kh);
		System.out.println("Thêm thành công");
		resp.sendRedirect("/khach-hang/index");
	}

	public void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		KhachHang kh = this.khRepo.findById(id);
		if (kh != null) {
			req.setAttribute("kh", kh);
			req.getRequestDispatcher("/views/khach_hang/edit.jsp").forward(req, resp);
		} else {
			resp.sendRedirect("/khach-hang/index");
		}

	}

	public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String ma = req.getParameter("ma");
		String ten = req.getParameter("ten");
		String sdt = req.getParameter("sdt");
		int trangThai = Integer.parseInt(req.getParameter("trangThai"));
		KhachHang kh = new KhachHang(id, ma, ten, sdt, trangThai);
		this.khRepo.update(kh);
		resp.sendRedirect("/khach-hang/update");
	}

	public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		KhachHang kh = this.khRepo.findById(id);
		this.khRepo.delete(kh);
		System.out.println("Xóa thành công");
		resp.sendRedirect("/khach-hang/index");
	}

	public Integer generateIdKh() {
		int newId = 0;
		newId = getMaxId() + 1;
		return newId;
	}

	public int getMaxId() {
		int maxID = 0;
		List<KhachHang> list = khRepo.findAll();
		for (KhachHang kh : list) {
			int maHienTai = kh.getId();
			if (maHienTai > maxID) {
				maxID = maHienTai;
			}
		}
		return maxID;
	}
}
