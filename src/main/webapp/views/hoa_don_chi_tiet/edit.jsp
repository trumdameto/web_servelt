<%--
  Created by IntelliJ IDEA.
  User: phuc
  Date: 2024-03-16
  Time: 2:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
	<div class="row justify-content-center">
		<div class="col-md-10">
			<h3 class="text-center mb-4">Sửa hóa đơn chi tiết</h3>
			<form method="POST" action="/hoa-don-chi-tiet/update?id=${list.id}">
				<div class="row mb-3">
					<div class="col-md-6">
						<label class="form-label">ID HD</label>
						<input type="text" name="idHoaDon" class="form-control"
						       value="${list.idHoaDon}"/>
					</div>
					<div class="col-md-6">
						<label class="form-label">ID SPCT</label>
						<input type="text" name="idSPCT" class="form-control"
						       value="${list.idSPCT}"/>
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-md-6">
						<label class="form-label">Số lượng</label>
						<input type="text" name="soLuong" class="form-control"
						       value="${list.soLuong}"/>
					</div>
					<div class="col-md-6">
						<label for="donGia" class="form-label">Đơn Giá</label>
						<input type="text" name="donGia" id="donGia" class="form-control"
						       value="${list.donGia}"/>
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-md-6">
						<label class="form-label">Thời gian</label>
						<input type="datetime-local" name="thoiGian" class="form-control"
						       value="${list.thoiGian}"/>
					</div>
					<div class="col-md-6">
						<label class="form-label">Trạng Thái</label><br>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="trangThai" value="1"
							${list.trangThai == 1 ? "checked":""}>
							<label class="form-check-label">Đang hoạt động</label>
						</div>
						<div class="form-check form-check-inline">
							<input class="form-check-input" type="radio" name="trangThai" value="0"
							${list.trangThai == 0 ? "checked":""}>
							<label class="form-check-label">Ngừng hoạt động</label>
						</div>
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-md-12 text-center">
						<button type="submit" class="btn btn-success">Cập nhật</button>
						<a href="/hoa-don-chi-tiet/index" class="btn btn-primary">Quay laị</a>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>
