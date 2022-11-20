<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

            <div class="navbar-nav col-3">
                <a class="nav-link mx-4 text-white"
                   href="${pageContext.request.contextPath}/dogwalker/user/main">Home</a>
                <a class="nav-link mx-4 text-white"
                   href="${pageContext.request.contextPath}/dogwalker/user/dogs">Dogs</a>
                <a class="nav-link mx-4 text-white"
                   href="${pageContext.request.contextPath}/dogwalker/user/places">Places</a>
            </div>

            <div class="dropdown col-1 d-flex justify-content-start">
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

            <div class="col-6 d-flex justify-content-end px-1">
                <a href="${pageContext.request.contextPath}/process_logout">
                    <img src="${pageContext.request.contextPath}/button_logout.jpg" alt="Logout"
                         style="max-height: 3rem;border-radius: 50%;object-position: right;">
                </a>
            </div>

        </nav>
    </div>
</div>
<c:if test="${not empty success}">
    <div class="container p-3 alert alert-success alert-dismissible fade show" role="alert">
        <strong>${success}</strong>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>
<div class="container p-3">
    <h3 class="d-flex justify-content-center">Appointment's info</h3><br>
    <div class="card" style="background-color: darkgray;width: 100%;height: 21rem;">
        <div class="row no-gutters">
            <div class="col-4">
                <img src="data:image/jpg;base64,${appointment.place.base64Image}" alt="Lights"
                     style="height: 21rem; object-fit: cover;"
                     class="card-img-top rounded">
            </div>
            <div class="col-8">
                <div class="card-body">
                    <h5>${appointment.place.address}</h5>
                    <p class="card-text">The nearest public transport stop: ${appointment.place.transportStop}</p>
                    <p class="card-text">Date: <fmt:formatDate value="${appointment.date}"
                                                               pattern="dd-MM-YYYY"/></p>
                    <p class="card-text">Time: ${appointment.time}</p>
                    <p class="card-text">Additional info: ${appointment.description}</p>
                    <p class="card-text">Number of joined people: ${appointment.numberOfPeople}</p>
                    <c:choose>
                        <c:when test="${isUserAdded == true}">
                            <form method="post"
                                  action="${pageContext.request.contextPath}/dogwalker/user/appointment/${appointment.id}/leaveAppointment/${loggedUser.id}">
                                <input type="submit" class="btn btn-primary" value="Leave appointment"
                                       name="appointment_id" style="position: absolute; bottom: 10px;"/>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form method="post"
                                  action="${pageContext.request.contextPath}/dogwalker/user/appointment/${appointment.id}/join">
                                <input type="submit" class="btn btn-primary" value="Join appointment"
                                       name="appointment_id"
                                       style="position: absolute; bottom: 10px;"/>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container p-3">
    <h3 class="d-flex justify-content-center">Participants</h3><br>
    <div class="row mx-auto">
        <c:forEach items="${allUsers}" var="user">
            <div class="card m-2" style="background-color: darkgray;width: 48%;">
                <div class="row no-gutters">
                    <div class="col-4">
                        <img src="data:image/jpg;base64,${user.base64Image}"
                             style="height: 21rem; object-fit: cover;"
                             class="card-img-top rounded">
                    </div>
                    <div class="col-4">
                        <div class="card-body">
                            <h5 class="card-title">Personal data</h5><br>
                            <p class="card-text"><strong>Owner:</strong>
                                    ${user.login} - ${user.age} years.</p><br>
                            <p class="card-text"><strong>Dog:</strong></p>
                            <p class="card-text">Beloved dog ${user.dog.name} - (${user.dog.type}).</p>
                            <p class="card-text">It's age is ${user.dog.age}. And it is a good
                                <c:choose>
                                    <c:when test="${user.dog.sex == 'MAN'}">
                                        boy
                                    </c:when>
                                    <c:otherwise>
                                        girl
                                    </c:otherwise>
                                </c:choose>.
                            </p>
                        </div>
                    </div>
                    <div class="col-4">
                        <img src="data:image/jpg;base64,${user.dog.base64Image}"
                             style="height: 21rem; object-fit: cover;"
                             class="card-img-top rounded">
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>

