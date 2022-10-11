<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js"
            integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT"
            crossorigin="anonymous"></script>
</head>
<body style="background-color: dimgrey">
<div class="container p-1">
    <div class="row mx-auto">
        <nav class="navbar navbar-expand-lg primary-color rounded text-center p-3" style="background-color: #e3f2fd;">

            <div class="navbar-nav col-2">
                <a class="nav-link mx-4" href="${pageContext.request.contextPath}/dogwalker/dogs">Dogs</a>
                <a class="nav-link mx-1" href="${pageContext.request.contextPath}/dogwalker/places">Places</a>
            </div>

            <div class="dropdown col-9 d-flex justify-content-start">
                <button class="btn dropdown-toggle" type="button"
                        id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                    Walk
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dogwalker/add">Add new</a>
                    </li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dogwalker/join">Join</a></li>
                </ul>
            </div>

        </nav>
    </div>
</div>

<div class="container p-3">
    <div class="card mb-3" style="width: 100%; height: 21rem; background-color: darkgray">
        <div class="row no-gutters">
            <div class="col-4">
                <img src="data:image/jpg;base64,${loggedUser.base64Image}" style="height: 21rem; object-fit: cover;"
                     class="card-img-top rounded">
            </div>
            <div class="col-4">
                <div class="card-body">
                    <h5 class="card-title">Personal data</h5><br>
                    <p class="card-text"><strong>Owner:</strong>
                        ${loggedUser.login} - ${loggedUser.age} years.</p><br>
                    <p class="card-text"><strong>Dog:</strong></p>
                    <p class="card-text">Beloved dog ${loggedUser.dog.name} - (${loggedUser.dog.type}).</p>
                    <p class="card-text">It's age is ${loggedUser.dog.age}. And it is a good
                        <c:choose>
                            <c:when test="${loggedUser.dog.sex == 'MAN'}">
                                boy
                            </c:when>
                            <c:otherwise>
                                girl
                            </c:otherwise>
                        </c:choose>.
                    </p>
                    <br>

                    <form method="post" action="${pageContext.request.contextPath}/dogwalker/personalPage/${loggedUser.id}/changeData">
                        <input type="submit" class="btn btn-secondary" value="Change personal data" name="changeData" >
                    </form>

                </div>
            </div>
            <div class="col-4">
                <img src="data:image/jpg;base64,${loggedUser.dog.base64Image}" style="height: 21rem; object-fit: cover;"
                     class="card-img-top rounded">
            </div>
        </div>
    </div>
</div>
</body>
</html>