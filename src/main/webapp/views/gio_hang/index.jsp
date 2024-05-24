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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<title>Cart</title>
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

	<div class="container">
		<% if (request.getAttribute("successMessage") != null) { %>
		<div class="alert alert-dark alert-dismissible fade show" role="alert">
			<strong><%= request.getAttribute("successMessage") %>
			</strong>
			<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
		<script>
            document.addEventListener("DOMContentLoaded", function () {
                var alertElement = document.querySelector('.alert');

                function hideAlert() {
                    alertElement.style.display = 'none';
                }

                setTimeout(hideAlert, 2000);
            });
		</script>
		<% } %>
	</div>
	<div class="container">
		<div class="row">
			<h1 class="text-center">Giỏ hàng</h1>
			<%int totalItems = 0;%>
			<%double totalPrices = 0;%>
			<%String tenSp = "";%>
			<c:if test="${not empty cart}">
				<table class="table table-striped">
					<thead>
					<tr>
						<th>STT</th>
						<th>Tên sản phẩm</th>
						<th>Đơn Giá</th>
						<th>Thành tiền</th>
						<th>Số lượng</th>
					</tr>
					</thead>
					<tbody>
					<%
						HashMap<Integer, SanPhamCart> cart = (HashMap<Integer, SanPhamCart>) session.getAttribute("cart");
						int stt = 1;
						for (Map.Entry<Integer, SanPhamCart> entry : cart.entrySet()) {
							Integer key = entry.getKey();
							SanPhamCart value = entry.getValue();
							SanPham sp = SanPhamRepo.findById(value.spct.getIdSanPham());
							tenSp = sp.getTen();
							totalItems++;
							totalPrices += value.spct.getDonGia() * value.soLuong;
					%>
					<tr>
						<td><%= stt++ %></td>
						<td><%= tenSp %></td>
						</td>
						<td><fmt:formatNumber value="<%= value.spct.getDonGia()%>" type="currency" currencySymbol="VNĐ"></fmt:formatNumber></td>
						<td><fmt:formatNumber value="<%= value.spct.getDonGia()* value.soLuong%>" type="currency" currencySymbol="VNĐ"></fmt:formatNumber></td>
						<td>
							<form action="/cart/update-so-luong" method="post">
								<input type="text" value="<%= value.soLuong %>" name="so_luong_spct">
								<input type="hidden" value="<%= value.spct.getId() %>" name="id">
								<button type="submit" class="btn btn-outline-dark">Cập nhật</button>
							</form>
						</td>
						<td>
							<form action="/cart/xoa-san-pham" method="post">
								<input type="hidden" value="<%= value.spct.getId() %>" name="id">
								<button type="submit" class="btn btn-outline-dark">Xóa</button>
							</form>
						</td>
					</tr>
					<% } %>
					</tbody>
				</table>
				<div class="d-flex justify-content-center">
					<a type="submit" class="btn btn-dark col-md-3"
					   href="/cart/checkout">Thanh toán</a>
				</div>
			</c:if>
			<c:if test="${ empty cart}">
				<div class="alert alert-dark">Chưa có sản phẩm nào được thêm
					<a href="/san-pham/index" class="btn btn-light">Thêm?</a>
				</div>
			</c:if>
		</div>
	</div>
	<h3 class="text-center">Tổng thanh toán (<%= totalItems %>) sản phẩm:
		<fmt:formatNumber value="<%= totalPrices %>" type="currency" currencySymbol="VNĐ"></fmt:formatNumber>
	</h3>

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
