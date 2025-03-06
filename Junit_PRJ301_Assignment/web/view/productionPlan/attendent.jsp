<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
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
            .date-header {
                text-align: left;
                background-color: #e9ecef; /* Màu nền xám nhạt */
                font-weight: bold;
                padding: 0.5rem;
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
                margin-right: 5px;
            }
            .create-btn {
                background-color: #00CC66;
                border: none;
                color: white;
                margin-right: 5px;
            }

            .err{
                text-align: center;
                color: red;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid mt-4">
            <div class="header">
                <div>
                    <input class="back-btn" type="button" value="Feature" onclick="redirectToFeature(${requestScope.plan.id});"/>
                    <input class="create-btn" type="button" value="Schedule" onclick="redirectToSchedule(${requestScope.plan.id});"/>
                </div>
                <h1>${requestScope.plan.name} Schedule</h1>
                <input class="create-btn" type="button" value="List" onclick="redirectToList();"/>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="main-content">
                        <form action="attendent" method="post">
                            <input type="hidden" name="planId" value="${plan.id}" />
                            <p class="err">${sessionScope.err}</p>
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>Date</th>
                                        <th>Employee Name</th>
                                        <th>Product Name</th>
                                        <th>Shift</th>
                                        <th>Quantity Assigned</th>
                                        <th>Estimated Effort</th>
                                        <th>Actual Quantity</th>
                                        <th>Alpha</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <!-- Duyệt qua từng ngày trong dateList -->
                                    <c:forEach var="date" items="${dateList}">
                                        <!-- Hàng hiển thị ngày kéo dài toàn bộ chiều ngang -->
                                        <tr>
                                            <th colspan="8" class="date-header">${date}</th>
                                        </tr>

                                        <!-- Duyệt qua danh sách allWorkAssignments để tìm công việc vào ngày này -->
                                        <c:forEach var="workAssignment" items="${allWorkAssignments}">
                                            <c:if test="${workAssignment.details.date == date}">
                                                <tr>
                                                    <td></td>
                                                    <td>${workAssignment.emp.name}</td>
                                                    <td>${workAssignment.details.pheader.product.pname}</td>
                                                    <td>${workAssignment.details.shift.sname}</td>
                                                    <td>
                                                        ${workAssignment.quantity}
                                                        <input type="hidden" 
                                                               name="assignedQuantity_${workAssignment.id}" 
                                                               value="${workAssignment.quantity}" />
                                                    </td>
                                                    <td>${workAssignment.details.pheader.estimatedeffort}</td>
                                                    <td>
                                                        <c:set var="actualQuantity" value="" />
                                                        <c:set var="alpha" value="" />
                                                        <c:forEach var="attendent" items="${attendents}">
                                                            <c:if test="${attendent.work.id == workAssignment.id}">
                                                                <c:set var="actualQuantity" value="${attendent.actualQuantity}" />
                                                                <c:set var="alpha" value="${attendent.alpha}" />
                                                            </c:if>
                                                        </c:forEach>
                                                        <input type="text" 
                                                               name="actualQuantity_${workAssignment.id}" 
                                                               size="4" 
                                                               placeholder="" 
                                                               value="${actualQuantity}"
                                                               <input type="hidden" name="waid_${workAssignment.id}" value="${workAssignment.id}" />
                                                        <input type="hidden" name="date_${workAssignment.id}" value="${workAssignment.details.date}" />
                                                        <input type="hidden" name="empId_${workAssignment.id}" value="${workAssignment.emp.id}" />
                                                    </td>
                                                    <td>${alpha}</td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                </tbody>
                            </table>

                            <button type="submit" class="btn btn-primary">Save Attendance</button>
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

                    function redirectToSchedule(plid) {
                        window.location.href = 'assignment?planId=' + plid;
                    }

                    function redirectToList() {
                        window.location.href = 'list';
                    }

        </script>
    </body>
</html>
