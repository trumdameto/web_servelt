package controllers;

import entities.HDCT;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import repositories.HoaDonChiTietRepo;
import java.text.ParseException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet({
		"/hoa-don-chi-tiet/index",
		"/hoa-don-chi-tiet/create",
		"/hoa-don-chi-tiet/store",
		"/hoa-don-chi-tiet/edit",
		"/hoa-don-chi-tiet/update",
		"/hoa-don-chi-tiet/delete"
})
public class HDCTServlet extends HttpServlet {
	private HoaDonChiTietRepo hoaDonChiTietRepo;

	public HDCTServlet() {
		this.hoaDonChiTietRepo = new HoaDonChiTietRepo();
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

	public void index(
			HttpServletRequest request,
			HttpServletResponse response
	) throws IOException, ServletException {
		request.setAttribute("list", this.hoaDonChiTietRepo.findAll());
		request.getRequestDispatcher("/views/hoa_don_chi_tiet/index.jsp").forward(request, response);
	}

	public void create(
			HttpServletRequest request,
			HttpServletResponse response
	) throws IOException, ServletException {
		request.getRequestDispatcher("/views/hoa_don_chi_tiet/create.jsp").forward(request, response);
	}

	public void store(
			HttpServletRequest request,
			HttpServletResponse response
	) throws IOException, ServletException {
		try {
			int idHoaDon = Integer.parseInt(request.getParameter("idHoaDon"));
			int idSPCT = Integer.parseInt(request.getParameter("idSPCT"));
			int soLuong = Integer.parseInt(request.getParameter("soLuong"));
			Double donGia = Double.parseDouble(request.getParameter("donGia"));
			int trangThai = Integer.parseInt(request.getParameter("trangThai"));

			Date parsedDate = null;
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
				parsedDate = dateFormat.parse(request.getParameter("thoiGian"));

			} catch (ParseException e) {
				e.printStackTrace();
			}
			Timestamp thoiGian = new Timestamp(parsedDate.getTime());
			HDCT hdct = new HDCT(null, idHoaDon, idSPCT, soLuong, donGia, thoiGian, trangThai);

			this.hoaDonChiTietRepo.create(hdct);
			response.sendRedirect("/hoa-don-chi-tiet/index");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void edit(
			HttpServletRequest request,
			HttpServletResponse response
	) throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		HDCT hdct = this.hoaDonChiTietRepo.findById(id);
		if (hdct != null) {
			request.setAttribute("list", hdct);
			request.getRequestDispatcher("/views/hoa_don_chi_tiet/edit.jsp").forward(request, response);
		} else {
			response.sendRedirect("/hoa-don-chi-tiet/index");
		}
	}

	public void update(
			HttpServletRequest request,
			HttpServletResponse response
	) throws IOException, ServletException {
		HDCT spct = new HDCT();
		try {
			BeanUtils.populate(spct, request.getParameterMap());
			this.hoaDonChiTietRepo.update(spct);
			response.sendRedirect("/hoa-don-chi-tiet/index");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(
			HttpServletRequest request,
			HttpServletResponse response
	) throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		HDCT hdct = this.hoaDonChiTietRepo.findById(id);
		this.hoaDonChiTietRepo.delete(hdct);
		response.sendRedirect("/hoa-don-chi-tiet/index");
	}
}
