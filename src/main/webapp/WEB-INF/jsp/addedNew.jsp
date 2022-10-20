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

            <img src="${pageContext.request.contextPath}/dogwalker_mini.jpg"
                 alt="logo" class="rounded" style="object-fit: cover; object-position: left; height: 3rem;">

            <div class="navbar-nav col-3">
                <a class="nav-link mx-4 text-white" href="${pageContext.request.contextPath}/dogwalker/main">Home</a>
                <a class="nav-link mx-4 text-white" href="${pageContext.request.contextPath}/dogwalker/dogs">Dogs</a>
                <a class="nav-link mx-4 text-white"
                   href="${pageContext.request.contextPath}/dogwalker/places">Places</a>
            </div>

            <div class="dropdown col-6 d-flex justify-content-start">
                <button class="btn dropdown-toggle text-white" type="button"
                        id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                    Walk
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dogwalker/add">Add new</a>
                    </li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/dogwalker/join">Join</a></li>
                </ul>
            </div>

            <div class="col-1 m-auto">
                <a href="${pageContext.request.contextPath}/dogwalker/personalPage/${loggedUser.id}">
                    <img class="float-right" src="data:image/jpg;base64,${loggedUser.base64Image}" alt="Lights"
                         style="max-height: 3rem;border-radius: 50%">
                </a>
            </div>

        </nav>
    </div>
</div>


<div class="container p-3">
    <div class="row">
        <div class="col-sm-7 text-black p-3 mx-auto">

            <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">New appointment was successfully created</h3>

            <div class="form-outline mb-4">
                <label class="form-label">Address:</label>
                <input
                        class="form-control form-control-lg"
                        type="text"
                        placeholder="${added_address}"
                        disabled
                />
            </div>

            <div class="form-outline mb-4">
                <label class="form-label">Date:</label>
                <input
                        class="form-control form-control-lg"
                        type="text"
                        placeholder="${added_date}"
                        disabled
                />
            </div>

            <div class="form-outline mb-4">
                <label class="form-label">Time:</label>
                <input
                        class="form-control form-control-lg"
                        type="text"
                        placeholder="${added_time}"
                        disabled
                />
            </div>

            <div class="form-outline mb-4">
                <label class="form-label">Description:</label>
                <p>${added_description}</p>
            </div>

        </div>

        <div class="col-sm-1"></div>
        <div class="col-sm-4 p-3 mx-auto">
            <img src="https://i.pinimg.com/564x/9c/3d/25/9c3d252aa0bcebf1bf88d91ca043612a.jpg"
                 alt="image" class="rounded" style="object-fit: cover; height: 33rem">
        </div>
    </div>
</div>


</body>
</html>
