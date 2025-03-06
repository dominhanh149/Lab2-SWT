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
            input[type="text"], select, .date-input {
                width: 150px; /* Tăng kích thước cho input date */
                padding: 6px;
                box-sizing: border-box;
                text-align: center;
            }
            input[type="date"].date-input {
                width: 150px; /* Kích thước rộng hơn cho input date */
                padding: 6px;
                margin-bottom: 20px;
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
                flex-grow: 1;
                text-align: center;
                margin-right: 150px;
            }
            .err {
                color: red;
                text-align: center;
            }
            .date-column {
                width: 90px;
            }

            .mid-item{
                display: flex;
                justify-content: space-between;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid mt-4">
            <div class="header">
                <input class="back-btn" type="button" value="Home" onclick="redirectToHome();"/>
                <input class="create-btn" type="button" value="Salary Infor" onclick="redirectToSalaryInfor();"/>
                <h1>View Salary</h1>

            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="main-content">
                        <form id="fm-salary" action="viewsalary" method="post">
                            <div class="mid-item">
                                <div>
                                    <input type="date" class="date-input" name="from" value="${requestScope.from}"/>
                                    <input type="date" class="date-input" name="to" value="${requestScope.to}"/>
                                    <input class="submit-btn" type="submit" value="View" />
                                </div>
                                    <select name="did" style="font-size: 18px; height: 45px; width: 260px; padding: 2px; margin-right: 30px;" onchange="submitForm();">
                                    <option value="0">-----All-----</option>
                                    <c:forEach items="${requestScope.depts}" var="d">
                                        <option value="${d.id}"
                                                <c:if test="${requestScope.did eq d.id}">
                                                    selected ="selected"
                                                </c:if>
                                                >${d.name}</option>
                                    </c:forEach>
                                </select>
                            </div><!-- comment -->
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th style="width: 15%;">Employee ID</th>
                                        <th style="width: 55%;">Employee Name</th>
                                        <th style="width: 30%;">Total Salary</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="lastEmployeeId" value="-1" />
                                    <c:forEach var="s" items="${requestScope.salaryEmps}" varStatus="status">
                                        <tr>
                                            <td>${s.eid}</td>
                                            <td>${s.ename}</td>
                                            <td>${s.calculatedSalary}</td>
                                        </tr>
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
                        window.location.href = 'feature?plid=' + plid;
                    }
                    function redirectToDetail(plid) {
                        window.location.href = 'detail?plid=' + plid;
                    }
                    function redirectToList() {
                        window.location.href = 'list';
                    }
                    
                    function submitForm(){
                        var form = document.getElementById("fm-salary");
                        form.submit();
                    }
                    function redirectToSalaryInfor(){
                        window.location.href = 'workinfor';
                    }
                    function redirectToHome(){
                        window.location.href = '../home';
                    }
        </script>
    </body>
</html>
