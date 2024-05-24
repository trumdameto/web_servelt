<%--
  Created by IntelliJ IDEA.
  User: phuc
  Date: 2024-03-16
  Time: 2:31 PM
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
            <h3 class="text-center mb-4">Thêm sản phẩm chi tiết</h3>
            <form method="POST" action="/sp-ct/store">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label">Ma SPCT</label>
                        <input type="text" name="maSPCT" class="form-control"/>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">ID Kích Thước</label>
                        <input type="text" name="idKichThuoc" class="form-control"/>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label">ID Màu Sắc</label>
                        <input type="text" name="idMauSac"  class="form-control"/>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">ID Sản Phẩm</label>
                        <input type="text" name="idSanPham" class="form-control"/>
                    </div>
                </div>
                <div class="row mb-3">

                    <div class="col-md-6">
                        <label class="form-label">Số Lượng</label>
                        <input type="text" name="soLuong" class="form-control"/>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Đơn Giá</label>
                        <input type="text" name="donGia" class="form-control"/>
                    </div>
                </div>
                <div class="row mb-3">

                    <div class="col-md-6">
                        <label class="form-label">Trạng Thái</label><br>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="trangThai" value="1"
                                   checked>
                            <label class="form-check-label">Đang hoạt động</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="trangThai" value="0">
                            <label class="form-check-label">Ngừng hoạt động</label>
                        </div>

                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col-md-12 text-center">
                        <button type="submit" class="btn btn-success">Thêm</button>
                        <a href="/sp-ct/index" class="btn btn-primary">Quay laị</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
