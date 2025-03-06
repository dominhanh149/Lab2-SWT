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
                justify-content: space-between;
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
                width: 70px;
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
                margin-right: 120px;
            }
            .header h1 {
                flex-grow: 1;
                text-align: center;
                margin-right: 100px;
            }
            .err {
                color: red;
                text-align: center;
            }
        </style>
    </head>
    <body>

        <c:if test="${param.notifi ne null}">
            <script>
                alert("${param.notifi}");
            </script>
        </c:if>
        <div class="container mt-4">
            <div class="header">
                <input class="back-btn" type="button" value="Feature" onclick="redirectToFeature(${plan.id});"/>
                <input class="create-btn" type="button" value="Schedule" onclick="redirectToAssignment(${requestScope.plan.id});"/>
                <h1>${requestScope.plan.name}</h1>
                <input class="back-btn" type="button" value="List" onclick="redirectToList();"/>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="main-content">
                        <form action="${pageContext.request.contextPath}/productionPlan/detail" method="post">
                            <input type="hidden" name="plid" value="${plan.id}"/>
                            <input type="hidden" value="${sessionScope.deptId}" name="did"/>
                            <p class="err">${sessionScope.errQuantityDetail}</p>
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Date</th>
                                            <c:forEach items="${plan.headers}" var="head">
                                            <th colspan="3">${head.product.pname}</th>
                                            </c:forEach>
                                    </tr>
                                    <tr>
                                        <th></th>
                                            <c:forEach items="${plan.headers}" var="head">
                                            <th class="shift-header">K1</th>
                                            <th class="shift-header">K2</th>
                                            <th class="shift-header">K3</th>
                                            </c:forEach>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="date" items="${dateList}">
                                        <tr>
                                            <td>${date}</td>
                                    <input type="hidden" name="dateRange" value="${date}"/>
                                    <c:forEach items="${plan.headers}" var="head">
                                        <td>
                                            <c:set var="shift1Quantity" value="0"/>
                                            <c:forEach var="detail" items="${head.details}">
                                                <c:if test="${detail.date == date && detail.shift.sname == 'K1'}">
                                                    <c:set var="shift1Quantity" value="${detail.quantity}"/>
                                                </c:if>
                                            </c:forEach>
                                            <input type="text" name="shift1_quantity_${date}_${head.product.pname}" value="${shift1Quantity}"/>
                                        </td>
                                        <td>
                                            <c:set var="shift2Quantity" value="0"/>
                                            <c:forEach var="detail" items="${head.details}">
                                                <c:if test="${detail.date == date && detail.shift.sname == 'K2'}">
                                                    <c:set var="shift2Quantity" value="${detail.quantity}"/>
                                                </c:if>
                                            </c:forEach>
                                            <input type="text" name="shift2_quantity_${date}_${head.product.pname}" value="${shift2Quantity}"/>
                                        </td>
                                        <td>
                                            <c:set var="shift3Quantity" value="0"/>
                                            <c:forEach var="detail" items="${head.details}">
                                                <c:if test="${detail.date == date && detail.shift.sname == 'K3'}">
                                                    <c:set var="shift3Quantity" value="${detail.quantity}"/>
                                                </c:if>
                                            </c:forEach>
                                            <input type="text" name="shift3_quantity_${date}_${head.product.pname}" value="${shift3Quantity}"/>
                                        </td>
                                    </c:forEach>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <input class="submit-btn" type="submit" value="Save Shift Quantities"/>
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
                        window.location.href = 'feature?plid=' + plid;
                    }

                    function redirectToAssignment(plid) {
                        window.location.href = 'assignment?planId=' + plid;
                    }

                    function redirectToList() {
                        window.location.href = 'list';
                    }

                    document.addEventListener("DOMContentLoaded", function () {
                        // Lấy tất cả các ô input trong bảng
                        const inputs = document.querySelectorAll("input[type='text']");
                        // Duyệt qua từng ô input
                        inputs.forEach(input => {
                            // Kiểm tra nếu giá trị của input khác 0
                            if (input.value !== "0") {
                                // Thay đổi màu chữ thành màu xanh dương
                                input.style.color = "#027ebc";
                                input.style.fontWeight = "bold";
                            }
                        });
                    });
        </script>
    </body>
</html>
