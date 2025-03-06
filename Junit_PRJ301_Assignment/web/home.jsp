<!DOCTYPE html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <title>Home Page</title>
        <style>
            body {
                background-color: rgb(235, 235, 235);
            }
            .header {
                display: flex;
                background-color: #4a69bd;
                padding: 20px;
                border-radius: 10px;
                color: white;
                text-align: center;
                justify-content: space-around;
            }
            .main-content {
                background-color: rgb(255, 255, 255);
                border-radius: 10px;
                padding: 30px;
                margin-top: 30px;
                text-align: center;
            }
            .btn-home {
                background-color: #4a69bd;
                border: none;
                color: white;
                padding: 15px 40px;
                border-radius: 10px;
                margin: 10px;
                font-size: 1.2rem;
                text-decoration: none;
            }
            .btn-home:hover {
                background-color: #365b99;
                color: white;
            }
            .product-news {
                margin-top: 40px;
            }
            .product-news h2 {
                color: #4a69bd;
                margin-bottom: 20px;
            }
            .product-card {
                border: 1px solid #dee2e6;
                border-radius: 10px;
                padding: 15px;
                text-align: center;
                margin-bottom: 20px;
                background-color: #fff;
            }
            .product-card img {
                width: 100%;
                height: auto;
                border-radius: 10px;
                margin-bottom: 15px;
            }
            .product-card h3 {
                color: #333;
            }

            .product-card img {
                width: 400px;
                height: 250px;
                object-fit: cover;
                border-radius: 10px;
                margin-bottom: 15px;
                margin-top: 10px;
                overflow: hidden;
            }

            .user{
                border: none;
                border-radius: 6px;
                padding: 3px;
            }

        </style>
    </head>
    <body>
        <div class="container-fluid mt-4">
            <div class="header">
                <h1>Welcome to the Production Management System</h1>
                <div>
                    <button class="user">${sessionScope.account.username}</button>
                    <button class="user" onclick="logout();">Logout</button>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="main-content">
                        <a href="productionPlan/list" class="btn btn-home">Production Plan List</a>
                        <a href="viewemp" class="btn btn-home">View Employees</a>
                        <a href="productionPlan/viewsalary" class="btn btn-home">View Salary</a>
                    </div>
                </div>
            </div>

            <!-- Product News Section -->
            <div class="row product-news">
                <div class="col-md-12">
                    <h2>Latest Product News</h2>
                </div>

                <!-- Product: Thúng -->
                <div class="col-md-4">
                    <div class="product-card">
                        <img src="img/thung.jpg" alt="Thúng Product Image">
                        <h3>Thúng</h3>
                        <p>The "Thúng" is a traditional Vietnamese basket made from bamboo. Known for its durability and natural appeal, it is commonly used in agriculture and daily life.</p>
                    </div>
                </div>

                <!-- Product: Giỏ -->
                <div class="col-md-4">
                    <div class="product-card">
                        <img src="img/gio.jpg" alt="Giỏ Product Image">
                        <h3>Giỏ</h3>
                        <p>The "Giỏ" is a handmade basket that serves a variety of purposes, from carrying goods to decorating homes. Crafted with care, it represents traditional Vietnamese craftsmanship.</p>
                    </div>
                </div>

                <!-- Product: Mẹt -->
                <div class="col-md-4">
                    <div class="product-card">
                        <img src="img/met.jpg" alt="Mẹt Product Image">
                        <h3>Mẹt</h3>
                        <p>The "Mẹt" is a versatile bamboo tray often used for drying food, serving meals, or displaying items. Its lightweight design and natural material make it a household essential.</p>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <script>
                        function redirectToCreate() {
                            window.location.href = 'create'; // Đường dẫn đến trang đích
                        }

                        function logout() {
                            window.location.href = 'logout'; // Đường dẫn đến trang đích
                        }

                        function confirmDelete(plid, plname) {
                            return confirm("Are you sure you want to delete plan: " + plname);
                        }
        </script>
    </body>
</html>
