<!DOCTYPE html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <title>Plan Details</title>
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
                align-items: center;
            }
            .main-content {
                background-color: white;
                border-radius: 10px;
                padding: 15px;
                margin-top: 15px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 1rem;
                background-color: #fff;
            }
            th, td {
                text-align: center;
                vertical-align: middle;
                padding: 0.5rem;
                border: 1px solid #dee2e6;
            }
            .shift-header {
                text-align: center;
                background-color: #f8f9fa;
                padding: 0.5rem;
            }
            input[type="text"], input[type="date"], select {
                width: 70px; /* Kích thước nhỏ hơn cho input */
                padding: 6px;
                box-sizing: border-box;
                text-align: center;
            }

            .submit-btn, .back-btn, .create-btn {
                padding: 10px 20px;
                border-radius: 5px;
            }

            .submit-btn {
                background-color: #4a69bd;
                border: none;
                color: white;
            }

            .back-btn {
                background-color: white;
                color: #4a69bd;
                border: 1px solid #4a69bd;
                margin-right: 20px;
            }

            .create-btn {
                background-color: #00CC66;
                border: none;
                color: white;
                margin-right: 20px;
            }

            .header h1 {
                flex-grow: 1; /* Cho phép thẻ h1 chiếm không gian còn lại */
                text-align: center; /* Căn giữa văn bản trong h1 */
                margin-right: 150px;
            }

            .err{
                color: red;
                text-align: center;
            }

            .date-column {
                width: 90px; /* Giảm kích thước cột Date */
            }

            /* Mở rộng kích thước thẻ select */
            select {
                width: 90px; /* Tăng chiều ngang để hiển thị tên đầy đủ của các employee */
                padding: 6px;
                box-sizing: border-box;
                text-align: center;
            }
            .employee-list {
                align-items: center; /* Căn giữa dọc cho các phần tử trong một hàng */
                justify-content: space-between; /* Cách đều các phần tử */
            }

            .employee-list select{
                width: 95px; /* Thu nhỏ chiều rộng của các phần tử */
                padding: 3px; /* Giảm padding */
                font-size: 0.7rem;
                text-align: center;
            }
            .employee-list input[type="text"] {
                width: 25px; /* Thu nhỏ chiều rộng của các phần tử */
                padding: 3px; /* Giảm padding */
                font-size: 0.7rem;
                text-align: center;
            }

        </style>
    </head>
    <body>
        <div class="container-fluid mt-4">
            <div class="header">
                <input class="back-btn" type="button" value="Feature" onclick="redirectToFeature(${requestScope.plan.id});"/>
                <input class="create-btn" type="button" value="Detail" onclick="redirectToDetail(${requestScope.plan.id});"/>
                <h1>Employee Work Infor</h1>
                <input class="create-btn" type="button" value="List" onclick="redirectToList();"/>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="main-content">
                        <form action="" method="post">
                            <input type="date" name="from"/>
                            <input type="date" name="to"/>
                            <input type="submit" value="View" />
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>Employee ID</th>
                                        <th>Employee Name</th>
                                        <th>Wage</th>
                                        <th>Date</th>
                                        <th>Product</th>
                                        <th>Estimated Effort</th>
                                        <th>Quantity Assigned</th>
                                        <th>Actual Quantity</th>
                                        <th>Alpha</th>
                                        <th>Total Shifts Per Product</th>
                                        <th>Calculated Salary</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="lastEmployeeId" value="-1" />

                                    <!-- Duyệt qua từng workAssignment trong danh sách -->
                                    <c:forEach var="workAssignment" items="${workAssignments}">
                                        <tr>
                                            <!-- Hiển thị Employee ID, Name, Salary chỉ khi Employee ID khác với bản ghi trước đó -->
                                            <td><c:if test="${lastEmployeeId != workAssignment.eid}">${workAssignment.eid}</c:if></td>
                                            <td><c:if test="${lastEmployeeId != workAssignment.eid}">${workAssignment.ename}</c:if></td>
                                            <td><c:if test="${lastEmployeeId != workAssignment.eid}">${workAssignment.salary}</c:if></td>

                                                <!-- Hiển thị Shift, Product, Estimated Effort, Quantity Assigned, Actual Quantity, Alpha, Total Shifts, Calculated Salary -->
                                                <td>${workAssignment.date}</td>
                                            <td>${workAssignment.productName}</td>
                                            <td>${workAssignment.estimatedEffort}</td>
                                            <td>${workAssignment.quantityAssigned}</td>
                                            <td>${workAssignment.actualQuantity}</td>
                                            <td>${workAssignment.alpha}</td>
                                            <td>${workAssignment.totalShifts}</td>
                                            <td>${workAssignment.calculatedSalary}</td>
                                        </tr>

                                        <!-- Cập nhật lastEmployeeId để kiểm tra cho bản ghi tiếp theo -->
                                        <c:set var="lastEmployeeId" value="${workAssignment.eid}" />
                                    </c:forEach>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <script>
                    function redirectToFeature(plid) {
                        window.location.href = 'feature?plid=' + plid; // Đường dẫn đến trang đích
                    }
                    function redirectToDetail(plid) {
                        window.location.href = 'detail?plid=' + plid; // Đường dẫn đến trang đích
                    }

                    function redirectToList() {
                        window.location.href = 'list'; // Đường dẫn đến trang đích
                    }

                    function redirectToAssignment(plid) {
                        window.location.href = 'assignment?planId=' + plid; // Đường dẫn đến trang đích
                    }

                    function redirectToCreate() {
                        window.location.href = 'create'; // Đường dẫn đến trang đích
                    }

                    function confirmDelete(plid, plname) {
                        return confirm("Are you sure you want to delete plan: " + plname);
                    }
        </script>
    </body>
</html>
