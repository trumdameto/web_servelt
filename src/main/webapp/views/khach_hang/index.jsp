<%--
  Created by IntelliJ IDEA.
  User: phuc
  Date: 2024-03-12
  Time: 1:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
	<title>Title</title>
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
	<%--Tìm kiếm--%>
	<form class="form-control" action="/khach-hang/index" method="get">
		<div class="row">
			<div class="form-group col-6">
				<label class="form-label">Tìm kiếm</label>
				<input type="text" class="form-control" placeholder="Nhập mã hoặc tên để tìm..." name="search"
				       value="${search}">
			</div>
			<div class="form-group col-6">
				<label class="form-label">SDT</label>
				<input type="text" class="form-control" placeholder="Nhập sdt để tìm..." name="soDT" value="${soDT}">
			</div>
		</div>
		<div class="form-group">
			<label class="form-label">Trạng Thái</label><br>
			<div class="form-check form-check-inline">
				<input class="form-check-input" type="radio" name="trangThai" value="1"
				${trangThai == 1 ? "checked":""}>
				<label class="form-check-label">Đang hoạt động</label>
			</div>
			<div class="form-check form-check-inline">
				<input class="form-check-input" type="radio" name="trangThai" value="0"
				${trangThai == 0 ? "checked":""}>
				<label class="form-check-label">Ngừng hoạt động</label>
			</div>
		</div>
		<div class="text-center">
			<button class="btn btn-dark me-2" type="submit">Tìm kiếm</button
			<button><a href="/khach-hang/index" class="btn btn-dark">Làm mới</a></button>
		</div>
	</form>
	<%-- Tìm kiếm--%>
	<a href="/khach-hang/create" class="btn btn-dark">Thêm</a>
	<a href="/" class="btn btn-dark">Quay lại</a>
	<table class="table table-hover table-bordered">
		<thead>
		<tr>
			<th scope="col">STT</th>
			<th scope="col">Ma</th>
			<th scope="col">Ten</th>
			<th scope="col">SDT</th>
			<th scope="col">Trang thai</th>
			<th scope="col">Thao tac</th>
		</tr>
		</thead>

		<tbody>
		<c:forEach items="${kh}" var="ms" varStatus="i">
			<tr>
				<th scope="row">${i.index+1}</th>
				<td>${ms.ma}</td>
				<td>${ms.ten}</td>
				<td>${ms.sdt}</td>
				<td>${ms.trangThai}</td>
				<td><a href="/khach-hang/edit?id=${ms.id}" class="btn btn-dark">Update</a>
					<a href="/khach-hang/delete?id=${ms.id}" class="btn btn-dark m-2">Delete</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<nav aria-label="..." class="mt-3 d-flex justify-content-center">
		<ul class="pagination">
			<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
				<a class="page-link"
				   href="?page=${currentPage - 1}&search=${search}&soDT=${soDT}&trangThai=${trangThai}"> < </a>
			</li>

			<c:forEach begin="1" end="${totalPage}" var="i">
				<c:if test="${ i < 4 || i > totalPage - 3 }">
					<li class="page-item  ${currentPage == i ? 'active' : ''}">
						<a class="page-link"
						   href="/khach-hang/index?page=${i}&search=${search}&soDT=${soDT}&trangThai=${trangThai}">${i}</a>
					</li>
				</c:if>

				<c:if test="${ totalPage > 6 && i == 4 }">
					<li class="page-item"><span class="page-link" href="">...</span></li>
				</c:if>
			</c:forEach>

			<li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">
				<a class="page-link"
				   href="?page=${currentPage + 1}&search=${search}&soDT=${soDT}&trangThai=${trangThai}"> > </a>
			</li>

		</ul>
	</nav>
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
