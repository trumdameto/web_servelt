package controllers;

import entities.MauSac;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositories.MauSacRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet({
		"/mau-sac/index",
		"/mau-sac/create",
		"/mau-sac/store",
		"/mau-sac/edit",
		"/mau-sac/update",
		"/mau-sac/delete",
})
public class MauSacServlet extends HttpServlet {

	private MauSacRepository mauSacRepository;

	public MauSacServlet() {
		this.mauSacRepository = new MauSacRepository();
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

	public void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/views/mau_sac/create.jsp").forward(req, resp);
	}

	public void store(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ma = req.getParameter("ma");
		String ten = req.getParameter("ten");
		int trangThai = Integer.parseInt(req.getParameter("trangThai"));
		MauSac ms = new MauSac(0, ma, ten, trangThai);
		this.mauSacRepository.addMauSac(ms);
		resp.sendRedirect("/mau-sac/index");
	}

	public void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		MauSac ms = this.mauSacRepository.findById(id);
		if (ms != null) {
			req.setAttribute("ms", ms);
			req.getRequestDispatcher("/views/mau_sac/edit.jsp").forward(req, resp);
		} else {
			resp.sendRedirect("/mau-sac/index");
		}
	}

	public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String ma = req.getParameter("ma");
		String ten = req.getParameter("ten");
		int trangThai = Integer.parseInt(req.getParameter("trangThai"));

		MauSac ms = new MauSac(id, ma, ten, trangThai);
		this.mauSacRepository.updateMauSac(ms);

		resp.sendRedirect("/mau-sac/index");
	}

	public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		MauSac ms = this.mauSacRepository.findById(id);
		this.mauSacRepository.deleteMS(ms);
		resp.sendRedirect("/mau-sac/index");
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

		int count = mauSacRepository.getCountBySearch(search, trangThai);
		int totalPage = (count + limit - 1) / limit;

		if (search.length() != 0) {
			req.setAttribute("search", search);
		}
		if (trangThai != null) {
			req.setAttribute("trangThai", trangThai);
		}
		List<MauSac> ds = mauSacRepository.findAll(search, trangThai, Optional.of(page), Optional.of(limit));
		req.setAttribute("ms", ds);
		req.setAttribute("currentPage", page);
		req.setAttribute("totalPage", totalPage);
		req.getRequestDispatcher("/views/mau_sac/index.jsp").forward(req, resp);

	}


}
