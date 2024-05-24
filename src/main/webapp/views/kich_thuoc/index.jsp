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
</head>
<body>
<div class="container">
	<%--Tìm kiếm--%>
	<form class="form-control" action="/kich-thuoc/index" method="get">
		<div class="row">
			<div class="form-group col-6">
				<label class="form-label">Tìm kiếm</label>
				<input type="text" class="form-control" placeholder="Nhập mã hoặc tên" name="search" value="${search}">
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
		</div>
		<div class="text-center">
			<button class="btn btn-primary me-2" type="submit">Tìm kiếm</button
			<button><a href="/kich-thuoc/index" class="btn btn-light">Làm mới</a></button>
		</div>
	</form>
	<%-- Tìm kiếm--%>
	<a href="/kich-thuoc/create" class="btn btn-success">Thêm</a>
	<a href="/" class="btn btn-light">Quay lại</a>
	<table class="table table-hover table-bordered">
		<thead>
		<tr>
			<th scope="col">STT</th>
			<th scope="col">Ma</th>
			<th scope="col">Ten</th>
			<th scope="col">Trang thai</th>
			<th scope="col">Thao tac</th>
		</tr>
		</thead>

		<tbody>
		<c:forEach items="${data}" var="kt" varStatus="i">
			<tr>
				<th scope="row">${i.index+1}</th>
				<td>${kt.ma}</td>
				<td>${kt.ten}</td>
				<td>${kt.trangThai}</td>
				<td><a href="/kich-thuoc/edit?id=${kt.id}" class="btn btn-warning">Update</a>
					<a href="/kich-thuoc/delete?id=${kt.id}" class="btn btn-danger m-2">Delete</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<nav aria-label="..." class="mt-3 d-flex justify-content-center">
			<ul class="pagination">
				<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
					<a class="page-link" href="?page=${currentPage - 1}&search=${search}&trangThai=${trangThai}"> < </a>
				</li>

				<c:forEach begin="1" end="${totalPage}" var="i">
					<c:if test="${ i < 4 || i > totalPage - 3 }">
						<li class="page-item  ${currentPage == i ? 'active' : ''}">
							<a class="page-link" href="/kich-thuoc/index?page=${i}&search=${search}&trangThai=${trangThai}">${i}</a>
						</li>
					</c:if>

					<c:if test="${ totalPage > 6 && i == 4 }">
						<li class="page-item"><span class="page-link" href="">...</span></li>
					</c:if>
				</c:forEach>

				<li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">
					<a class="page-link" href="?page=${currentPage + 1}&search=${search}&trangThai=${trangThai}"> > </a>
				</li>
			</ul>
		</nav>
</div>
</body>
</html>
