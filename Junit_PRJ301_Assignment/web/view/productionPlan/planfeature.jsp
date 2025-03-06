<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <title>Plan Functions</title>
    <style>
        body {
            background-color: rgb(235, 235, 235);
        }
        .header {
            display: flex;
            justify-content: space-between;
            background-color: #4a69bd;
            padding: 20px;
            border-radius: 10px;
            color: white;
            align-items: center;
        }
        .header h1 {
            flex-grow: 1;
            text-align: center;
            margin: 0;
        }
        .btn-container {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            margin-top: 20px;
            background-color: #f7f7f7; /* Màu xám nhạt */
            padding: 20px;
            border-radius: 10px;
        }
        .function-btn {
            width: 150px;
            padding: 15px;
            margin: 10px;
            font-size: 16px;
            font-weight: bold;
            color: white;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            text-align: center;
        }
        .detail-btn {
            background-color: #5a9;
        }
        .schedule-btn {
            background-color: #4a69bd;
        }
        .attendent-btn {
            background-color: #ff6f61;
        }
        .update-btn {
            background-color: #f0ad4e;
        }
        .create-btn {
            background-color: #5bc0de;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <div class="header">
            <input class="btn btn-light" type="button" value="Back" onclick="redirectToList();" />
            <h1>${plan.name} Functions</h1>
        </div>

        <div class="btn-container">
            <!-- Nút điều hướng đến trang Detail -->
            <button class="function-btn detail-btn" onclick="redirectToDetail(${plan.id});">Detail</button>

            <!-- Nút điều hướng đến trang Schedule -->
            <button class="function-btn schedule-btn" onclick="redirectToSchedule(${plan.id});">Schedule</button>

            <!-- Nút điều hướng đến trang Attendent -->
            <button class="function-btn attendent-btn" onclick="redirectToAttendent(${plan.id});">Attendent</button>

        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <script>
        function redirectToList() {
            window.location.href = 'list'; // Đường dẫn đến trang danh sách kế hoạch
        }
        function redirectToDetail(planId) {
            window.location.href = 'detail?plid=' + planId; // Đường dẫn đến trang chi tiết kế hoạch
        }
        function redirectToSchedule(planId) {
            window.location.href = 'assignment?planId=' + planId; // Đường dẫn đến trang lịch trình kế hoạch
        }
        function redirectToAttendent(planId) {
            window.location.href = 'attendent?planId=' + planId; // Đường dẫn đến trang chấm công
        }
        function redirectToUpdate(planId) {
            window.location.href = 'update?plid=' + planId; // Đường dẫn đến trang cập nhật kế hoạch
        }
        function redirectToCreate() {
            window.location.href = 'create'; // Đường dẫn đến trang tạo mới kế hoạch
        }
    </script>
</body>
</html>
