package controllers;

import entities.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import repositories.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet({
		"/cart/index",
		"/cart/checkout",
		"/cart/checkout-confirm",
		"/cart/add-to-cart",
		"/cart/update-so-luong",
		"/cart/xoa-san-pham"
})
public class CartServlet extends HttpServlet {

	private SPCTRepo spctRepo;
	private SanPhamRepo spRepo;
	private HoaDonRepo hdRepo;
	private HDCTRepo hdctRepo;
	private NhanVienRepo nvRepo;
	private KhachHangRepo khRepo;

	public CartServlet() {
		this.spctRepo = new SPCTRepo();
		this.spRepo = new SanPhamRepo();
		this.hdRepo = new HoaDonRepo();
		this.hdctRepo = new HDCTRepo();
		this.nvRepo = new NhanVienRepo();
		this.khRepo = new KhachHangRepo();
	}

	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response
	) throws IOException, ServletException {
		String uri = request.getRequestURI();
		if (uri.contains("checkout")) {
			this.checkout(request, response);
		}
		else {
			this.index(request, response);
		}
	}

	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response
	) throws IOException, ServletException {
		String uri = request.getRequestURI();
		if (uri.contains("add-to-cart")) {
			this.addToCart(request, response);
		} else if (uri.contains("update-so-luong")) {
			this.updateSL(request, response);
		} else if (uri.contains("checkout-confirm")) {
			this.checkoutConfirm(request, response);
		}else if (uri.contains("xoa-san-pham")) {
			this.xoaSp(request, response);
		}
		else {
			this.index(request, response);
		}
	}

	public void checkout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		List<NhanVien> listNV = nvRepo.findAll();
		List<KhachHang> listKH = khRepo.findAll();
		request.setAttribute("listNV", listNV);
		request.setAttribute("listKH", listKH);
		request.getRequestDispatcher("/views/gio_hang/checkout.jsp").forward(request, response);
	}

	public void checkoutConfirm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int idKH = Integer.parseInt(request.getParameter("idKH"));
		int idNV = Integer.parseInt(request.getParameter("idNV"));

		String ngayMuaHangStr = request.getParameter("ngayMuaHang");
		Date ngayMuaHang = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			ngayMuaHang = dateFormat.parse(ngayMuaHangStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession();
		HashMap<Integer, SanPhamCart> cart = (HashMap<Integer, SanPhamCart>) session.getAttribute("cart");
//		int totalHoaDon = 0;
//		//HoaDon
//		for (Map.Entry<Integer, SanPhamCart> entry : cart.entrySet()) {
//			totalHoaDon += entry.getValue().soLuong * entry.getValue().spct.getDonGia();
//		}
		HoaDon hd = new HoaDon(idKH, idNV, ngayMuaHang, 1);
		int idHD = hdRepo.insert(hd);
		System.out.println(idHD);
		//HoaDonChiTiet
		String thoiGianStr = request.getParameter("thoiGian");
		Timestamp timestamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateTimeStr = ngayMuaHangStr + " " + thoiGianStr;
			Date dateTime = sdf.parse(dateTimeStr);
			timestamp = new Timestamp(dateTime.getTime());
			System.out.println(timestamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		System.out.println(timestamp);

		for (Map.Entry<Integer, SanPhamCart> entry : cart.entrySet()) {
			int idSPCT = entry.getValue().spct.getId();
			int soLuong = entry.getValue().soLuong;
			Double donGia = Double.valueOf(entry.getValue().spct.getDonGia());
			System.out.println("donGia= " + entry.getValue().spct.getDonGia());
			System.out.println("soL= " + soLuong);
			HoaDonChiTiet hdct = new HoaDonChiTiet(idHD, idSPCT, soLuong, donGia, timestamp, 1);
			hdctRepo.insert(hdct);
		}
		session.setAttribute("cart",null);
		System.out.println("Đặt hàng thành công");
		String message = "Đặt hàng thành công!";
		request.setAttribute("successMessage", message);
		request.getRequestDispatcher("/views/gio_hang/index.jsp").forward(request, response);

	}


	public void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		HashMap<Integer, SanPhamCart> cart = (HashMap<Integer, SanPhamCart>) session.getAttribute("cart");
		req.setAttribute("cart", cart);
		req.getRequestDispatcher("/views/gio_hang/index.jsp").forward(req, resp);
	}

	public void addToCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		SanPhamChiTiet spct = this.spctRepo.findById(id);
		SanPhamCart spCart;

		HttpSession session = req.getSession();

		HashMap<Integer, SanPhamCart> cart = (HashMap<Integer, SanPhamCart>) session.getAttribute("cart");
		if (cart == null) {
			cart = new HashMap<Integer, SanPhamCart>();
			spCart = new SanPhamCart(1, spct);
			cart.put(id, spCart);

		} else {
			if (cart.containsKey(id)) {
				spCart = cart.get(id);
				spCart.soLuongUpdate();
			} else {
				spCart = new SanPhamCart(1, spct);
				cart.put(id, spCart);
			}
		}
//		spct.setSoLuong(spct.getSoLuong() - 1);
//		spctRepo.update(spct);
		session.setAttribute("cart", cart);
		resp.sendRedirect("/cart/index");
	}

	public void updateSL(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		int so_luong = Integer.parseInt(req.getParameter("so_luong_spct"));
		SanPhamChiTiet spct = this.spctRepo.findById(id);
		SanPhamCart spCart;
		HttpSession session = req.getSession();

		HashMap<Integer, SanPhamCart> cart = (HashMap<Integer, SanPhamCart>) session.getAttribute("cart");
		if (cart == null) {
			cart = new HashMap<Integer, SanPhamCart>();
		}

		if (so_luong == 0) {
			cart.remove(id);
		} else {
			spCart = cart.get(id);
			if (spCart != null) {
				spCart.setSoLuong(so_luong);
//				spCart.spct.setDonGia(spct.getDonGia() * so_luong);
			}
		}

		session.setAttribute("cart", cart);
		resp.sendRedirect("/cart/index");
	}

	public void xoaSp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		HttpSession session = req.getSession();
		HashMap<Integer, SanPhamCart> cart = (HashMap<Integer, SanPhamCart>) session.getAttribute("cart");

		if (cart != null) {
			cart.remove(id);
			session.setAttribute("cart", cart);
		}
		resp.sendRedirect("/cart/index");
	}

}
