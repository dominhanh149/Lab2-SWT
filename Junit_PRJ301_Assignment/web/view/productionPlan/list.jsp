<!DOCTYPE html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
                margin-right: 40px;
            }

            .create-btn{
                background-color: #00CC66;
                border: none;
                color: white;
                padding: 15px 30px 15px 30px;
                border-radius: 10px;
                margin-right: 300px;
            }

            #plname {
                text-decoration: none;
                color: black;
            }

            #plname:hover {
                border-bottom: 1px solid rgb(215, 215, 215);
            }

            .edit-btn {
                background-color: #00CC66;
                border: none;
                color: white;
                padding: 5px 10px 5px 10px;
                border-radius: 10px;
                margin-right: 10px;
            }

            #option{
                text-decoration: none;
            }

            #option:hover{
                color: #dee2e6;
            }

            .delete-btn {
                background-color: rgb(232, 74, 74);
                border: none;
                color: white;
                padding: 5px 10px 5px 10px;
                border-radius: 10px;

            }
        </style>
    </head>
    <body>
        <div class="container-fluid mt-4">
            <div class="header">
                <input class="back-btn" type="button" value="Home" onclick="HomeRedirect();"/>
                <input class="create-btn" type="button" value="Create" onclick="redirectToCreate();"/>
                <h1>Production Plan List</h1>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="main-content">
                        <table class="table">
                            <tr>
                                <td style="font-weight: bold">Plan Name</td>
                                <td style="font-weight: bold">Start</td>
                                <td style="font-weight: bold">End</td>
                                <td style="font-weight: bold">Product</td>
                                <td style="font-weight: bold">Department</td>
                                <td style="font-weight: bold">Actual Quantity</td>
                                <td style="font-weight: bold">Quantity Expected</td>
                                <td></td>
                            </tr>

                            <c:forEach items="${requestScope.plans}" var="pl">
                                <tr>
                                    <td>
                                        <a id="plname" href="feature?plid=${pl.id}">${pl.name}</a>
                                    </td>
                                    <td>${pl.start}</td>
                                    <td>${pl.end}</td>
                                    <td>
                                        <c:forEach items="${pl.headers}" var="ph">
                                            ${ph.product.pname} </br>
                                        </c:forEach>
                                    </td>
                                    <td>${pl.dept.name}</td>
                                    <td>
                                        <c:forEach items="${pl.headers}" var="ph">
                                            ${ph.actualQuantity} </br>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:forEach items="${pl.headers}" var="ph">
                                            ${ph.quantity} </br>
                                        </c:forEach>
                                    </td>

                                    <td>
                                        <a href="update?plid=${pl.id}" id="option" class="edit-btn">Edit</a>
                                        <a href="delete?plid=${pl.id}" id="option" class="delete-btn" onclick="return confirmDelete(${pl.id}, '${pl.name}')">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
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
                                            ;
                                            
                                            function HomeRedirect(){
                                                window.location.href = '../home';
                                            };

                                            function confirmDelete(plid, plname) {
                                                return confirm("Are you sure you want to delete plan: " + plname);
                                            }
                                            ;
        </script>
    </body>
</html>
