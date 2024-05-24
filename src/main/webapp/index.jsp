<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
	<title>Bảng điều khiển</title>
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

        .headerlogo {
            margin-bottom: 30px;
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
        }

        .menubar a:hover {
            background-color: white;
            color: black;
        }

        .submenu {
            display: none;
            background-color: #567;
            padding-left: 0px;
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
            menuItem.innerText = "Quản lý Hóa đơn <<";
        }
    }
</script>


</body>
</html>
