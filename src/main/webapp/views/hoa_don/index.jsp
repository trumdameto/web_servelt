<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <a href="/hoa-don/create" class="btn btn-success me-2">Thêm mới</a>
    <a href="/" class="btn btn-primary">Quay lại</a>
</div>
<table class="table table-hover">
    <thead>
    <tr>
        <th>STT</th>
        <th>ID</th>
        <th>ID NV</th>
        <th>ID KH</th>
        <th>Ngày Mua</th>
        <th>Trạng thái</th>
        <th>Thao tác</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${ data }" var="ms" varStatus="i">
        <tr>
            <td>${i.index+1}</td>
            <td>${ms.id}</td>
            <td>${ms.idNV}</td>
            <td>${ms.idKH}</td>
            <td>
                <fmt:formatDate value="${ms.ngayMuaHang}" pattern="yyyy-MM-dd" var="o"/>
                    ${o}
            </td>
            <td>${ms.trangThai}</td>
            <td colspan="2">
                <a href="/hoa-don/edit?id=${ms.id}" class="btn btn-warning">Update</a>
                <a href="/hoa-don/delete?id=${ms.id}" class="btn btn-danger">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<nav aria-label="..." class="mt-3 d-flex justify-content-center">
    <ul class="pagination pagination-md">
        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
            <a class="page-link" href="/hoa-don/index?page=${currentPage - 1}"> < </a>
        </li>

        <c:forEach begin="1" end="${totalPage}" var="i">
            <c:if test="${ i < 4 || i > totalPage - 3 }">
                <li class="page-item ${currentPage == i ? 'active' : ''}">
                    <a class="page-link" href="/hoa-don/index?page=${i}">${i}</a>
                </li>
            </c:if>
            <c:if test="${ totalPage > 6 && i == 4 }">
                <li class="page-item"><span class="page-link" href="">...</span></li>
            </c:if>
        </c:forEach>
        <li class="page-item ${currentPage == totalPage ? 'disabled' : ''}">
            <a class="page-link" href="/hoa-don/index?page=${currentPage + 1}"> > </a>
        </li>
    </ul>
</nav>
</body>
</html>
