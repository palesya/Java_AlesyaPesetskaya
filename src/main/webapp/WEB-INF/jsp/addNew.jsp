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
                <a class="nav-link mx-4 text-white" href="${pageContext.request.contextPath}/dogwalker/user/places">Places</a>
            </div>

            <div class="dropdown col-6 d-flex justify-content-start">
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
    <div class="row">
        <div class="col-sm-8 text-black p-3">

            <h3 class="fw-normal mb-3" style="letter-spacing: 1px;">Add new appointment</h3>
            <h6>New appointment can be scheduled at least one hour before the existing one,
                or not earlier than one hour after the beginning of the existing one.</h6>
            <%--@elvariable id="appointmentForm" type=""--%>
            <sf:form action="${pageContext.request.contextPath}/dogwalker/user/addedNew" method="post" modelAttribute="appointmentForm">
                <div class="form-outline mb-4 mt-4">
                    <label class="form-label">Address:</label>
                    <sf:select typeof="submit" class="form-select" aria-label="Default select example"
                            name="selected_address" path="address">
                        <option selected>${selected_place.address}</option>
                        <c:forEach items="${allPlaces}" var="place">
                            <option>${place.address}</option>
                        </c:forEach>
                    </sf:select>
                    <sf:errors path="address" class="text-warning" cssStyle="line-height: 25px;" role="alert"/>
                </div>

                <div class="form-outline mb-4">
                    <label class="form-label" for="form2Example18">Date:</label>
                    <sf:input type="date" name="date" id="form2Example18"
                           class="form-control form-control-lg" path="date"/>
                    <sf:errors path="date" class="text-warning" cssStyle="line-height: 25px;" role="alert"/>
                </div>

                <div class="form-outline mb-4">
                    <label class="form-label" for="form2Example28">Time:</label>
                    <sf:input type="time" name="time" id="form2Example28"
                           class="form-control form-control-lg" path="time"/>
                    <sf:errors path="time" class="text-warning" cssStyle="line-height: 25px;" role="alert"/>
                    <p class="text-warning" cssStyle="line-height: 25px;" role="alert">${error}</p>
                </div>

                <div class="form-outline mb-4">
                    <label class="form-label" for="form3">Description:</label>
                    <sf:input type="text" name="description" id="form3"
                           class="form-control form-control-lg" path="description"/>
                    <sf:errors path="description" class="text-warning" cssStyle="line-height: 25px;" role="alert"/>
                </div>

                <div class="pt-1 mb-4">
                    <button class="btn btn-primary" type="submit">Create</button>
                </div>
            </sf:form>

        </div>


        <div class="col-sm-4 px-0 d-none d-sm-block p-3">
            <img src="https://i.pinimg.com/564x/c7/68/7f/c7687f80f45bfc445d0b17b0bbd26710.jpg"
                 alt="image" class="rounded" style="object-fit: cover; object-position: left; height: 33rem;">
        </div>
    </div>
</div>

</body>
</html>
