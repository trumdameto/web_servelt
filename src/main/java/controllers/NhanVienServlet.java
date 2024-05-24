package controllers;

import entities.KichThuoc;
import entities.NhanVien;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.NhanVienRepo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet({
		"/nhan-vien/index",
		"/nhan-vien/create",
		"/nhan-vien/store",
		"/nhan-vien/edit",
		"/nhan-vien/update",
		"/nhan-vien/delete"
})
public class NhanVienServlet extends HttpServlet {
	private NhanVienRepo nvRepo;

	public NhanVienServlet() {
		this.nvRepo = new NhanVienRepo();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (uri.contains("store")) {
			this.store(request, response);
		} else if (uri.contains("update")) {
			this.update(request, response);
		} else {
			//
		}
	}

	public void index(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String search = request.getParameter("search") != null ? request.getParameter("search").trim() : "";
		Integer trangThai = null;
		try {
			trangThai = Integer.parseInt(request.getParameter("trangThai"));
		} catch (NumberFormatException e) {
			//
		}
		String pageS = request.getParameter("page");
		String limitS = request.getParameter("limit");

		int page = pageS == null || pageS.trim().length() == 0 ? 1 : Integer.parseInt(pageS);
		int limit = limitS == null || limitS.trim().length() == 0 ? 5 : Integer.parseInt(limitS);

		List<NhanVien> nv = this.nvRepo.findAll(search, trangThai, Optional.of(page), Optional.of(limit));
		int count = this.nvRepo.getCountBySearch(search, trangThai);
		int totalPage = (count + limit - 1) / limit;

		if (search.length() != 0) {
			request.setAttribute("search", search);
		}
		if (trangThai != null) {
			request.setAttribute("trangThai", trangThai);
		}

		request.setAttribute("nv", nv);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("currentPage", page);
		request.getRequestDispatcher("/views/nhan_vien/index.jsp").forward(request, response);

	}

	public void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String ma = generateMaNV();
		request.setAttribute("ma", ma);
		request.getRequestDispatcher("/views/nhan_vien/create.jsp").forward(request, response);
	}

	public void store(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String ten = request.getParameter("ten");
		String ma = request.getParameter("ma");
		String tenDn = request.getParameter("tenDangNhap");
		String matKhau = request.getParameter("matKhau");
		int trangThai = Integer.parseInt(request.getParameter("trangThai"));

		NhanVien nv = new NhanVien(0, ten, ma, tenDn, matKhau, trangThai);
		this.nvRepo.insertNv(nv);
		response.sendRedirect("/nhan-vien/index");
	}

	public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		NhanVien nv = this.nvRepo.findById(id);
		if (nv != null) {
			request.setAttribute("nv", nv);
			request.getRequestDispatcher("/views/nhan_vien/edit.jsp").forward(request, response);
		} else {
			response.sendRedirect("/nhan-vien/index");
		}
	}

	public void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String ten = request.getParameter("ten");
		String ma = request.getParameter("ma");
		String tenDn = request.getParameter("tenDangNhap");
		String matKhau = request.getParameter("matKhau");
		int trangThai = Integer.parseInt(request.getParameter("trangThai"));
		int id = Integer.parseInt(request.getParameter("id"));
		NhanVien nv = new NhanVien(id, ten, ma, tenDn, matKhau, trangThai);

		this.nvRepo.updateNv(nv);
		response.sendRedirect("/nhan-vien/index");
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		NhanVien nv = this.nvRepo.findById(id);
		this.nvRepo.deleteNv(nv);
		response.sendRedirect("/nhan-vien/index");
	}

	public String generateMaNV() {
		int newMa = getMaxMaNV() + 1;
		return String.format("NV%03d", newMa);
	}

	public int getMaxMaNV() {
		int maxMa = 0;
		List<NhanVien> list = nvRepo.findAll();
		for (NhanVien nv : list) {
			int maHienTai = Integer.parseInt(nv.getMa().substring(2));
			if (maHienTai > maxMa) {
				maxMa = maHienTai;
			}
		}
		return maxMa;
	}

}
