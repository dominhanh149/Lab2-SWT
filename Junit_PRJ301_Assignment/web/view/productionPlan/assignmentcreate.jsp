<!DOCTYPE html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <title>JSP Page</title>
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
            }
            .main-content {
                background-color: rgb(255, 255, 255);
                border-radius: 10px;
                padding: 15px;
                margin-top: 15px;
            }
            .left-table table {
                width: 100%;
                margin-bottom: 1rem;
                background-color: #fff;
                border: 1px solid #dee2e6;
            }
            .left-table th, .left-table td {
                text-align: center;
                vertical-align: middle;
                padding: 0.75rem;
                border: 1px solid #dee2e6;
            }
            input[type="text"], input[type="date"], select {
                width: 100%;
                padding: 8px;
                box-sizing: border-box;
            }

            table th {
                text-align: center;
            }

            .submit-btn{
                background-color: #4a69bd;
                border: none;
                color: white;
                padding: 15px 40px 15px 40px;
                border-radius: 10px;
            }

            .back-btn{
                background-color: white;
                border: none;
                color: #4a69bd;
                padding: 15px 30px 15px 30px;
                border-radius: 10px;
                margin-right: 170px;
            }

            .err{
                color: red;
            }
        </style>
    </head>
    <body>
        <div class="container mt-4">
            <div class="header">
                <input class="back-btn" type="button" value="Back" onclick="redirectToList(${param.plid});"/>
                <h1>Employee Production Create</h1>
            </div>

            <form action="create" method="post">
                <div class="row">
                    <div class="col-md-6">
                        <div class="main-content">
                            <table class="table">
                                <tr>
                                    <td>Employee:</td>
                                    <td>
                                        <select name="eid">
                                            <c:forEach items="${requestScope.emps}" var="e">
                                                <option value="${e.id}">${e.name}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Quantity:</td>
                                    <td>
                                        <input type="text" name="quantity"/> <br><!-- comment -->
                                        <span class="err">${sessionScope.errQuantityScheduleCreate}</span>
                                    </td>
                                </tr>

                                <tr>
                                    <td><input type="hidden" value="${requestScope.pdid}" name="pdid"/> <br></td>
                                    <td>
                                        <input class="submit-btn" type="submit" value="Add"/> <br><!-- comment -->
                                    </td>
                                </tr>

                            </table>
                            <input type="hidden" value="${param.shift}" name="shift"/>
                            <input type="hidden" value="${param.date}" name="date"/>
                            <input type="hidden" value="${param.plid}" name="plid"/>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="main-content">
                            <table class="table">
                                <tr>
                                    <th>Date:</th>
                                    <td>
                                        ${param.date}
                                    </td>
                                </tr>

                                <tr>
                                    <th>Shift:</th>
                                    <td>
                                        ${param.shift}
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>

                </div>               
            </form>

        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
                    function redirectToList(plid) {
                        window.location.href = '../assignment?planId=' + plid; // Đường dẫn đến trang đích
                    }
        </script>

    </body>
</html>
