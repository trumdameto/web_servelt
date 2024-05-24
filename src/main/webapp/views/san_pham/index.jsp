<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>
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
<form class="form-control" action="/san-pham/index" method="get">
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
        <button><a href="/san-pham/index" class="btn btn-light">Làm mới</a></button>
    </div>
</form>
<h3 class="text-center mb-3">Sản phẩm</h3>
<div class="d-flex justify-content-center mb-3">
    <a href="/san-pham/create" class="btn btn-success me-2">Thêm mới</a>
    <a href="/" class="btn btn-primary">Quay lại</a>
</div>
<table class="table table-hover">
    <thead>
    <tr>
        <th>STT</th>
        <th>ID</th>
        <th>Mã SP</th>
        <th>Tên</th>
        <th>Trạng thái</th>
        <th>Thao tác</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${sp}" var="sp" varStatus="i">
        <tr>
            <td>${i.index+1}</td>
            <td>${sp.id}</td>
            <td>${sp.ma}</td>
            <td>${sp.ten}</td>
            <td>${sp.trangThai}</td>
            <td colspan="2">
                <a href="/san-pham/edit?id=${sp.id}" class="btn btn-warning">Sửa</a>
                <a href="/sp-ct/index?san_pham_id=${sp.id}" class="btn btn-primary">Xem chi tiết</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<nav aria-label="" class="d-flex justify-content-center">
    <ul class="pagination pagination-md">
        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
            <a class="page-link" href="/san-pham/index?page=${currentPage - 1}&search=${search}&trangThai=${trangThai}"> < </a>
        </li>
        <c:forEach begin="1" end="${totalPage}" var="i">
            <c:if test="${ i < 4 || i > totalPage - 3 }">
                <li class="page-item ${currentPage == i ?'active':''}">
                    <a class="page-link" href="/san-pham/index?page=${i}&search=${search}&trangThai=${trangThai}">${i}</a>
                </li>
            </c:if>
            <c:if test="${ totalPage > 6 && i == 4 }">
                <li class="page-item"><span class="page-link" href="">...</span></li>
            </c:if>
        </c:forEach>
        <li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">
            <a class="page-link" href="/san-pham/index?page=${currentPage + 1}&search=${search}&trangThai=${trangThai}"> > </a>
        </li>
    </ul>
</nav>
</body>
</html>

