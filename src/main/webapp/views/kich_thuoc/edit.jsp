<%--
  Created by IntelliJ IDEA.
  User: phuc
  Date: 2024-03-12
  Time: 1:52 PM
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
            <h3 class="text-center mb-4">Cật nhật màu sắc</h3>
            <form method="POST" action="/kich-thuoc/update?id=${kt.id}">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label">Mã</label>
                        <input type="text" name="ma" value="${ kt.ma }" class="form-control"/>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Tên màu</label>
                        <input type="text" name="ten" value="${ kt.ten }" class="form-control"/>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label">Trạng Thái</label><br>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="trangThai" value="1"
                            ${kt.trangThai == 1 ? "checked":""}>
                            <label class="form-check-label">Đang hoạt động</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="trangThai" value="0"
                            ${kt.trangThai == 0 ? "checked":""}>
                            <label class="form-check-label">Ngừng hoạt động</label>
                        </div>
                    </div>
                </div>
                <div class="text-center">
                    <button class="btn btn-success">Cập nhật</button>
                    <a class="btn btn-primary" href="/kich-thuoc/index">Quay lại</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
