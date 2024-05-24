<%--
  Created by IntelliJ IDEA.
  User: phuc
  Date: 2024-04-01
  Time: 6:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page import="java.util.HashMap" %>
<%@page import="entities.SanPhamCart" %>
<%@page import="java.util.Map" %>
<%@ page import="entities.SanPham" %>
<%@ page import="repositories.SanPhamRepo" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.TimeZone" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<title>Checkout</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
	      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
	        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
	        crossorigin="anonymous"></script>
	<style>
        .sidebar {
            width: 200px;
            height: 100%;
            background-color: #333;
            position: fixed;
            left: 0;
            top: 0;
            padding-top: 20px;
        }

        .headerlogo img {
            width: 150px;
            display: block;
            margin: 0 auto;
        }

        .menubar a {
            display: block;
            color: white;
            text-decoration: none;
            padding: 10px;
            transition: background-color 0.3s;
        }

        .menubar a:hover {
            background-color: #567;
        }

        .submenu {
            display: none;
            background-color: #567;
            padding-left: 20px;
            position: absolute;
            max-height: 0;
            overflow-y: auto; /* Tạo thanh scrollbar khi nội dung dài */
            transition: max-height 0.3s ease-out;
        }

        .submenu a {
            color: white;
            background-color: #567;
            display: block;
            padding: 10px 15px;
            text-decoration: none;
        }

        .submenu a:hover {
            background-color: black;
            color: white;
        }

        /* Sử dụng JavaScript để hiển thị submenu */
        .submenu.show {
            display: block;
            max-height: 200px;
        }

        .content {
            margin-left: 200px;
            padding: 20px;
        }
	</style>
</head>
<body>
<div class="sidebar">
	<div class="headerlogo">
		<a href="/"><img src="./logo.jpg" alt="#Logo"></a>
	</div>
	<div class="menubar">
		<a href="/khach-hang/index">Quản lý Khách Hàng</a>
		<a href="/nhan-vien/index">Quản lý Nhân Viên</a>
		<a href="#" class="qlhoadon-btn" onclick="toggleSubMenu('submenu-hoadon')">Quản lý Hóa đơn</a>
		<div class="submenu" id="submenu-hoadon">
			<a href="/hoa-don/index">Hóa đơn</a>
			<a href="/hoa-don-chi-tiet/index">Hóa đơn chi tiết</a>
		</div>
		<a href="#" class="qlsanpham-btn" onclick="toggleSubMenu('submenu-sanpham')">Quản lý sản phẩm</a>
		<div class="submenu" id="submenu-sanpham">
			<a href="/mau-sac/index">Màu Sắc</a>
			<a href="/kich-thuoc/index">Kích Thước</a>
			<a href="/san-pham/index">Sản phẩm</a>
		</div>
		<a href="/cart/index">Bán Hàng</a>
	</div>
</div>

<div class="content">
<h1 class="text-center">Thanh toán</h1>
<%
	Date currentDate = new Date();

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	String currentDateStr = dateFormat.format(currentDate);
	String currentTimeStr = timeFormat.format(currentDate);
%>

	<div class="col justify-content-center">
		<form method="POST" action="/cart/checkout-confirm" class="form-control mx-auto bg-light">
			<div class="row mb-3">
				<div class="col-md-3">
					<label class="form-label">Nhân viên</label>
					<select class="form-select" name="idNV">
						<c:forEach items="${listNV}" var="nv">
							<option class="form-control" value="${nv.id}">${nv.ten}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-3">
					<label class="form-label">Khách hàng</label>
					<select class="form-select" name="idKH">
						<c:forEach items="${listKH}" var="kh">
							<option class="form-control" value="${kh.id}">${kh.ten}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-3">
					<label class="form-label">Ngày mua hàng</label>
					<input type="date" name="ngayMuaHang" class="form-control" value="<%= currentDateStr %>"/>
				</div>
				<div class="col-md-3">
					<label class="form-label">Thời gian</label>
					<input type="time" name="thoiGian" class="form-control" value="<%= currentTimeStr %>"/>
				</div>
			</div>
			<%--			--%>
			<div class="row mb-3">
				<%int totalItems = 0;%>
				<%double totalPrices = 0;%>
				<%String tenSp = "";%>
				<c:if test="${not empty cart}">
					<table class="table table-secondary">
						<thead>
							<tr>
								<th>Sản phẩm</th>
								<th>Đơn Giá</th>
								<th>Số lượng</th>
								<th>Thành tiền</th>
							</tr>
						</thead>
						<tbody>
							<%
								HashMap<Integer, SanPhamCart> cart = (HashMap<Integer, SanPhamCart>) session.getAttribute("cart");
								for (Map.Entry<Integer, SanPhamCart> entry : cart.entrySet()) {
									Integer key = entry.getKey();
									SanPhamCart value = entry.getValue();
									SanPham sp = SanPhamRepo.findById(value.spct.getIdSanPham());
									tenSp = sp.getTen();
									totalItems++;
									totalPrices += value.spct.getDonGia() * value.soLuong;
							%>
							<tr>
								<td><%= tenSp %></td>
								<td><fmt:formatNumber value="<%= value.spct.getDonGia()%>" type="currency" currencySymbol="VNĐ"></fmt:formatNumber></td>
								<td><%= value.soLuong %></td>
								<td>Tổng tiền <%= value.soLuong %> sản phẩm: <fmt:formatNumber value="<%= value.spct.getDonGia()* value.soLuong%>" type="currency" currencySymbol="VNĐ"></fmt:formatNumber></td>
							</tr>
							<% } %>
						</tbody>
					</table>
				</c:if>
				<c:if test="${ empty cart}">
					<div class="alert alert-danger">Chưa có sản phẩm nào được thêm
						<a href="/san-pham/index" class="btn btn-link">Thêm?</a>
					</div>
				</c:if>
			</div>
			<%--			--%>
			<div class="row mb-3">
				<h5>Tổng tiền hàng : <fmt:formatNumber value="<%=totalPrices%>" type="currency" currencySymbol="VNĐ"></fmt:formatNumber></h5>
				<h5>Tổng thanh toán : <fmt:formatNumber value="<%=totalPrices%>" type="currency" currencySymbol="VNĐ"></fmt:formatNumber></h5>
				<div class="col-md-12 d-flex justify-content-center">
					<button type="submit" class="btn btn-secondary mt-3">Đặt hàng</button>
				</div>
			</div>
		</form>
	</div>
</div>
<script>
    function toggleSubMenu(submenuId) {
        var submenu = document.getElementById(submenuId);
        if (submenu.classList.contains('show')) {
            submenu.classList.remove('show');
        } else {
            // Ẩn tất cả các submenu trước khi hiển thị submenu mới
            document.querySelectorAll('.submenu').forEach(submenu => {
                submenu.classList.remove('show');
            });
            submenu.classList.add('show');
        }
    }
</script>
</body>
</html>
