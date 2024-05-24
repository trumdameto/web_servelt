<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
	<title>Title</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
	      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
	        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
	        crossorigin="anonymous"></script>
</head>
<body>
<form class="form-control" action="/sp-ct/index" method="get">
	<input type="hidden" name="san_pham_id" value="${sanPham.id}" />
	<div class="row">
		<div class="form-group col-6">
			<label class="form-label">Tìm kiếm</label>
			<input type="text" class="form-control" placeholder="Nhập mã ..." name="search" value="${search}">
		</div>
		<div class="form-group col-6">
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
	</div>
	<div class="text-center mt-3">
		<button class="btn btn-primary me-2" type="submit">Tìm kiếm</button
		<button><a href="/sp-ct/index?san_pham_id=${sanPham.id}" class="btn btn-light">Làm mới</a></button>
	</div>
</form>
<h3 class="text-center mb-3">Chi tiết sản phẩm</h3>
<div class="d-flex justify-content-center mb-3">
	<a href="/sp-ct/create" class="btn btn-success me-2">Thêm mới</a>
	<a href="/san-pham/index" class="btn btn-primary">Quay lại</a>
</div>
<table class="table table-hover">
	<thead>
	<tr>
		<th>STT</th>
		<th>Mã SPCT</th>
		<th>Sản Phẩm</th>
		<th>Màu sắc</th>
		<th>Kích Thước</th>
		<th>Số lượng</th>
		<th>Đơn giá</th>
		<th>Trạng thái</th>
		<th>Thao tác</th>

	</tr>
	</thead>
	<tbody>
	<c:forEach items="${ data }" var="spct" varStatus="i">
		<tr>
			<td>${i.index+1}</td>
			<td>${spct.maSPCT}</td>
			<td>${sanPham.ten}</td>
			<td>${listMS[spct.idMauSac]}</td>
			<td>${listKT[spct.idKichThuoc]}</td>
			<td>${spct.soLuong}</td>
			<td><fmt:formatNumber value="${spct.donGia}" type="currency" currencySymbol="VNĐ"/></td>
			<td>${spct.trangThai}</td>
			<td colspan="2">
					<%--                <a href="/sp-ct/edit?id=${spct.id}" class="btn btn-warning">Update</a>--%>
				<form action="/cart/add-to-cart" method="post">
					<input type="hidden" value="${spct.id}" name="id">
					<button type="submit" class="btn btn-outline-primary">Thêm vào giỏ</button>
				</form>
					    <a href="/sp-ct/edit?id=${spct.id}" class="btn btn-warning">Sửa</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<%--<nav aria-label="" class="d-flex justify-content-center">--%>
<%--	<ul class="pagination pagination-md">--%>
<%--		<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">--%>
<%--			<a class="page-link" href="/sp-ct/index?san_pham_id=${sanPham.id}?page=${currentPage - 1}&search=${search}&trangThai=${trangThai}"> < </a>--%>
<%--		</li>--%>
<%--		<c:forEach begin="1" end="${totalPage}" var="i">--%>
<%--			<c:if test="${ i < 4 || i > totalPage - 3 }">--%>
<%--				<li class="page-item ${currentPage == i ?'active':''}">--%>
<%--					<a class="page-link" href="/sp-ct/index?san_pham_id=${sanPham.id}?page=${i}&search=${search}&trangThai=${trangThai}">${i}</a>--%>
<%--				</li>--%>
<%--			</c:if>--%>
<%--			<c:if test="${ totalPage > 6 && i == 4 }">--%>
<%--				<li class="page-item"><span class="page-link" href="">...</span></li>--%>
<%--			</c:if>--%>
<%--		</c:forEach>--%>
<%--		<li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">--%>
<%--			<a class="page-link" href="/sp-ct/index?san_pham_id=${sanPham.id}?page=${currentPage + 1}&search=${search}&trangThai=${trangThai}"> > </a>--%>
<%--		</li>--%>
<%--	</ul>--%>
<%--</nav>--%>
</body>
</html>
