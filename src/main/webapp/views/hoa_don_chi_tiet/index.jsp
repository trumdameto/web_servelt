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
<h3 class="text-center mb-3">Danh Sách</h3>
<div class="d-flex justify-content-center mb-3">
    <a href="/hoa-don-chi-tiet/create" class="btn btn-success me-2">Thêm mới</a>
    <a href="/" class="btn btn-primary">Quay lại</a>
</div>
<table class="table table-hover">
    <thead>
    <tr>
        <th>STT</th>
        <th>ID</th>
        <th>ID HD</th>
        <th>ID SPCT</th>
        <th>Số lượng</th>
        <th>Đơn giá</th>
        <th>Thời gian</th>
        <th>Trạng thái</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="spct" varStatus="i">
        <tr>
            <td>${i.index+1}</td>
            <td>${spct.id}</td>
            <td>${spct.idHoaDon}</td>
            <td>${spct.idSPCT}</td>
            <td>${spct.soLuong}</td>
            <td><fmt:formatNumber value="${spct.donGia}" type="currency" currencySymbol="VNĐ"></fmt:formatNumber></td>
            <td>${spct.thoiGian}</td>
            <td>${spct.trangThai}</td>
            <td colspan="2">
                <a href="/hoa-don-chi-tiet/edit?id=${spct.id}" class="btn btn-warning">Update</a>
                <a href="/hoa-don-chi-tiet/delete?id=${spct.id}" class="btn btn-danger">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
