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

            .abt{
                display: none;
            }
            
            .red{
                background-color: red;
            }
            
            .showbtn{
                margin-right: 10px;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid mt-4">
            <div class="header">
                <input class="back-btn" type="button" value="Feature" onclick="redirectToFeature(${requestScope.plan.id});"/>
                <input class="create-btn" type="button" value="Detail" onclick="redirectToDetail(${requestScope.plan.id});"/>
                <input class="back-btn" type="button" value="Attendent" onclick="redirectToAttendent(${requestScope.plan.id});"/>
                <h1>${requestScope.plan.name} Schedule</h1>
                
                <input class="create-btn" type="button" value="List" onclick="redirectToList();"/>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="main-content">
                        <input class="create-btn showbtn" id="mybtnadd" type="button" value="Add" onclick="toggleDisplay();"/>
                        <input class="create-btn red" id="mybtnhide" type="button" value="Hide" onclick="toggleHide();"/>
                        <form action="${pageContext.request.contextPath}/productionPlan/assignment" method="post">
                            <input type="hidden" name="planId" value="${plan.id}"/>
                            <input type="hidden" name="did" value="${requestScope.plan.dept.id}" />
                            <p class="err">${sessionScope.errQuantitySchedule}</p>
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th class="date-column">Date</th>
                                            <c:forEach items="${plan.headers}" var="h">
                                            <th colspan="3">${h.product.pname}</th>
                                            </c:forEach>
                                    </tr>
                                    <tr>
                                        <th></th>
                                            <c:forEach items="${plan.headers}" var="h">
                                            <th class="shift-header">K1</th>
                                            <th class="shift-header">K2</th>
                                            <th class="shift-header">K3</th>
                                            </c:forEach>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="date" items="${dateList}">
                                        <tr>
                                            <td class="date-column">${date}</td>
                                            <c:forEach items="${plan.headers}" var="h">
                                                <!-- Shift 1 (K1) -->
                                                <td>
                                                    <c:set var="hasK1Product" value="false" />
                                                    <c:set var="employeeAssigned" value="false" />
                                                    <c:set var="displayAddButton" value="true" />

                                                    <c:forEach var="d" items="${h.details}">
                                                        <c:if test="${d.date == date && d.shift.sname == 'K1'}">
                                                            <c:set var="hasK1Product" value="true" />

                                                            <c:forEach var="work" items="${d.works}" varStatus="status">
                                                                <div class="employee-list">
                                                                    <input type="hidden" name="waid_K1_${d.pdid}" value="${work.id}"/>
                                                                    <select name="emp_${d.pdid}_${h.product.pid}_K1_${work.id}">
                                                                        <c:forEach items="${requestScope.emps}" var="e">
                                                                            <c:if test="${work.emp.id eq e.id}">
                                                                                <option value="${e.id}">${e.name}</option>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </select>
                                                                    <input type="text" name="quantity_${d.pdid}_${h.product.pid}_K1_${work.id}" 
                                                                           value="${work.quantity}" size="4" placeholder="Qty" />
                                                                </div>
                                                                <c:set var="employeeAssigned" value="true" />
                                                            </c:forEach>

                                                            <!-- Hiển thị nút Add cho ca làm đã có nhân viên -->
                                                            <c:if test="${employeeAssigned}">
                                                                <a href="${pageContext.request.contextPath}/productionPlan/assignment/create?pdid=${d.pdid}&shift=K1&date=${date}&plid=${param.planId}&did=${requestScope.plan.dept.id}" class="btn btn-sm btn-primary mt-2 abt">+</a>
                                                            </c:if>

                                                            <!-- Hiển thị nút Add cho ca làm có sản phẩm nhưng chưa có nhân viên nào -->
                                                            <c:if test="${hasK1Product && !employeeAssigned && displayAddButton}">
                                                                <a href="${pageContext.request.contextPath}/productionPlan/assignment/create?pdid=${d.pdid}&shift=K1&date=${date}&plid=${param.planId}&did=${requestScope.plan.dept.id}" class="btn btn-sm btn-primary mt-2 abt">+</a>
                                                                <c:set var="displayAddButton" value="false" />
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                </td>

                                                <!-- Shift 2 (K2) -->
                                                <td>
                                                    <c:set var="hasK2Product" value="false" />
                                                    <c:set var="employeeAssigned" value="false" />
                                                    <c:set var="displayAddButton" value="true" />

                                                    <c:forEach var="d" items="${h.details}">
                                                        <c:if test="${d.date == date && d.shift.sname == 'K2'}">
                                                            <c:set var="hasK2Product" value="true" />

                                                            <c:forEach var="work" items="${d.works}" varStatus="status">
                                                                <div class="employee-list">
                                                                    <input type="hidden" name="waid_K2_${d.pdid}" value="${work.id}"/>
                                                                    <select name="emp_${d.pdid}_${h.product.pid}_K2_${work.id}">
                                                                        <c:forEach items="${requestScope.emps}" var="e">
                                                                            <c:if test="${work.emp.id eq e.id}">
                                                                                <option value="${e.id}">${e.name}</option>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </select>
                                                                    <input type="text" name="quantity_${d.pdid}_${h.product.pid}_K2_${work.id}" 
                                                                           value="${work.quantity}" size="4" placeholder="Qty" />
                                                                </div>
                                                                <c:set var="employeeAssigned" value="true" />
                                                            </c:forEach>

                                                            <c:if test="${employeeAssigned}">
                                                                <a href="${pageContext.request.contextPath}/productionPlan/assignment/create?pdid=${d.pdid}&shift=K2&date=${date}&plid=${param.planId}&did=${requestScope.plan.dept.id}" class="btn btn-sm btn-primary mt-2 abt">+</a>
                                                            </c:if>

                                                            <c:if test="${hasK2Product && !employeeAssigned && displayAddButton}">
                                                                <a href="${pageContext.request.contextPath}/productionPlan/assignment/create?pdid=${d.pdid}&shift=K2&date=${date}&plid=${param.planId}&did=${requestScope.plan.dept.id}" class="btn btn-sm btn-primary mt-2 abt">+</a>
                                                                <c:set var="displayAddButton" value="false" />
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                </td>

                                                <!-- Shift 3 (K3) -->
                                                <td>
                                                    <c:set var="hasK3Product" value="false" />
                                                    <c:set var="employeeAssigned" value="false" />
                                                    <c:set var="displayAddButton" value="true" />

                                                    <c:forEach var="d" items="${h.details}">
                                                        <c:if test="${d.date == date && d.shift.sname == 'K3'}">
                                                            <c:set var="hasK3Product" value="true" />

                                                            <c:forEach var="work" items="${d.works}" varStatus="status">
                                                                <div class="employee-list">
                                                                    <input type="hidden" name="waid_K3_${d.pdid}" value="${work.id}"/>
                                                                    <select name="emp_${d.pdid}_${h.product.pid}_K3_${work.id}">
                                                                        <c:forEach items="${requestScope.emps}" var="e">
                                                                            <c:if test="${work.emp.id eq e.id}">
                                                                                <option value="${e.id}">${e.name}</option>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </select>
                                                                    <input type="text" name="quantity_${d.pdid}_${h.product.pid}_K3_${work.id}" 
                                                                           value="${work.quantity}" size="4" placeholder="Qty" />
                                                                </div>
                                                                <c:set var="employeeAssigned" value="true" />
                                                            </c:forEach>

                                                            <c:if test="${employeeAssigned}">
                                                                <a href="${pageContext.request.contextPath}/productionPlan/assignment/create?pdid=${d.pdid}&shift=K3&date=${date}&plid=${param.planId}&did=${requestScope.plan.dept.id}" class="btn btn-sm btn-primary mt-2 abt">+</a>
                                                            </c:if>

                                                            <c:if test="${hasK3Product && !employeeAssigned && displayAddButton}">
                                                                <a href="${pageContext.request.contextPath}/productionPlan/assignment/create?pdid=${d.pdid}&shift=K3&date=${date}&plid=${param.planId}&did=${requestScope.plan.dept.id}" class="btn btn-sm btn-primary mt-2 abt">+</a>
                                                                <c:set var="displayAddButton" value="false" />
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                            </c:forEach>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>

                            <input class="submit-btn" type="submit" value="Save Assignment"/>
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

                    function redirectToAttendent(plid) {
                        window.location.href = 'attendent?planId=' + plid;
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

                    function toggleDisplay() {
                        const elements = document.querySelectorAll(".abt");
                        elements.forEach(element => {
                            element.style.display = "inline-block";
                        });
                    }
                    
                    function toggleHide() {
                        const elements = document.querySelectorAll(".abt");
                        elements.forEach(element => {
                            element.style.display = "none";
                        });
                    }

        </script>
    </body>
</html>
