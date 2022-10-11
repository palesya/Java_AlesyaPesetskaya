<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
        <nav class="navbar navbar-expand-lg primary-color rounded text-center" style="background-color: #e3f2fd;">

            <div class="navbar-nav col-2">
                <a class="nav-link mx-4" href="${pageContext.request.contextPath}/dogwalker/main">Home</a>
                <a class="nav-link mx-1" href="${pageContext.request.contextPath}/dogwalker/dogs">Dogs</a>
            </div>

            <div class="dropdown col-3 d-flex justify-content-start">
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

            <form class="col-2 m-auto mx-4" action="${pageContext.request.contextPath}/dogwalker/places/city"
                  method="get">
                <select typeof="submit" class="form-select" aria-label="Default select example" name="selected_city"
                        onchange="this.form.submit();">
                    <option selected>Select city</option>
                    <option>All cities</option>
                    <c:forEach items="${allCities}" var="city">
                        <option>${city}</option>
                    </c:forEach>
                </select>
            </form>

            <form class="col-3 m-auto p-2" action="${pageContext.request.contextPath}/dogwalker/places/search"
                  method="post">
                <input class="form-control" type="text" placeholder="Search by street or transport stop."
                       aria-label="Search" name="search_text">
            </form>
            <div class="col-1 m-auto p-2">
                <a href="${pageContext.request.contextPath}/dogwalker/personalPage/${loggedUser.id}">
                    <img class="float-right" src="data:image/jpg;base64,${loggedUser.base64Image}" alt="Lights"
                         style="max-height: 3rem;border-radius: 50%">
                </a>
            </div>
        </nav>
    </div>
</div>

<div class="container">
    <div class="row mx-auto">
        <c:forEach items="${allPlaces}" var="place">
            <div class="col-md-4 p-3">
                <div class="card" style="width: 20rem;background-color: darkgray">
                    <img class="card-img-top rounded" src="data:image/jpg;base64,${place.base64Image}"
                         alt="Card image cap" style="height: 10rem;">
                    <div class="card-body">
                        <h5 class="card-title">${place.address}</h5>
                        <p class="card-text">The nearest public transport stop: ${place.transportStop}</p>
                        <p class="card-text">The presence of a fence:
                            <c:choose>
                                <c:when test="${place.fence}">
                                    <img src="/check.jpg" class="mx-auto rounded-circle" style="width: 25px;"/>
                                </c:when>
                                <c:otherwise>
                                    <img src="/minus.jpg" class="mx-auto rounded-circle" style="width: 25px;"/>
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <p class="card-text">The availability of training equipment:
                            <c:choose>
                                <c:when test="${place.trainingEqyipment}">
                                    <img src="/check.jpg" class="mx-auto rounded-circle" style="width: 25px;"/>
                                </c:when>
                                <c:otherwise>
                                    <img src="/minus.jpg" class="mx-auto rounded-circle" style="width: 25px;"/>
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <form method="post" action="${pageContext.request.contextPath}/dogwalker/addWithSelectedPlace/${place.id}">
                            <input type="submit" class="btn btn-primary" value="Add appointment" name="place_id">
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
