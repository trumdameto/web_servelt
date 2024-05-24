<%--
  Created by IntelliJ IDEA.
  User: phuc
  Date: 2024-03-12
  Time: 1:51 PM
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
<div class="row">
    <form action="/khach-hang/store" method="post" class="form-control">
        <div class="form-group">
            <label class="form-label">Mã</label>
            <input type="text" class="form-control" placeholder="Nhập mã" name="ma">
        </div>
        <div class="form-group">
            <label class="form-label">Tên</label>
            <input type="text" class="form-control" placeholder="Nhập tên" name="ten">
        </div>
        <div class="form-group">
            <label class="form-label">SDT</label>
            <input type="text" class="form-control" placeholder="Nhập sdt" name="sdt">
        </div>
        <div class="form-group">
            <label class="form-label">Trạng Thái</label><br>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="trangThai" value="1" checked>
                <label class="form-check-label">Đang hoạt động</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="trangThai" value="0">
                <label class="form-check-label"
                >Ngừng hoạt động</label>
            </div>
        </div>
        <div class="text-center">
            <button class="btn btn-success" type="submit">Thêm</button
            <button class="btn btn-primary"><a href="/">Quay laị</a></button>
        </div>
    </form>
</div>
</body>
</html>
