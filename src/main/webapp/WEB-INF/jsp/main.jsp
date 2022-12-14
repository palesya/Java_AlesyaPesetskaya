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
        <nav class="navbar navbar-expand-lg primary-color rounded text-center"
             style="background-image: linear-gradient(to right, black, #63646A)">

            <div class="navbar-nav col-2 px-1">
                <img src="${pageContext.request.contextPath}/dogwalker_mini.jpg"
                     alt="logo" class="rounded" style="object-fit: cover; object-position: left; height: 3rem;">

                <a class="mx-2" href="${pageContext.request.contextPath}/dogwalker/user/personalPage/${loggedUser.id}">
                    <img class="float-right" src="data:image/jpg;base64,${loggedUser.base64Image}" alt="Lights"
                         style="max-height: 3rem;border-radius: 50%">
                </a>
            </div>

            <div class="navbar-nav col-2">
                <a class="nav-link mx-4 text-white"
                   href="${pageContext.request.contextPath}/dogwalker/user/dogs">Dogs</a>
                <a class="nav-link mx-4 text-white"
                   href="${pageContext.request.contextPath}/dogwalker/user/places">Places</a>
            </div>

            <div class="dropdown col-7 d-flex justify-content-start">
                <button class="btn dropdown-toggle text-white" type="button"
                        id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                    Walk
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dogwalker/user/add">Add
                        new</a>
                    </li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dogwalker/user/join">Join</a>
                    </li>
                </ul>
            </div>
            <div class="col-1 d-flex justify-content-end px-1">
                <a href="${pageContext.request.contextPath}/process_logout">
                    <img src="${pageContext.request.contextPath}/button_logout.jpg" alt="Logout"
                         style="max-height: 3rem;border-radius: 50%;object-position: right;">
                </a>
            </div>
        </nav>
    </div>
</div>

<div class="container p-3">
    <div class="row mx-auto">
        <c:forEach items="${users}" var="user">
            <div class="col-md-4 p-3">
                <div class="card" style="width: 20rem;background-color: darkgray">
                    <img src="data:image/jpg;base64,${user.dog.base64Image}" alt="Lights"
                         style="height: 20rem; object-fit: cover;"
                         class="card-img-top rounded">
                    <div class="card-body">
                        <h5>${user.dog.name} - <h6>(${user.dog.type})</h6></h5>
                        <p>age: ${user.dog.age}, good
                            <c:choose>
                                <c:when test="${user.dog.sex == 'MAN'}">
                                    boy
                                </c:when>
                                <c:otherwise>
                                    girl
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <p>Owner:</p>
                        <div class="row">
                            <div class="col-md-4">
                                <img src="data:image/jpg;base64,${user.base64Image}" alt="Lights"
                                     style="max-height: 5rem;border-radius: 50%;max-width: 5rem;">
                            </div>
                            <div class="col-md-4">
                                    ${user.login} - ${user.age}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>
