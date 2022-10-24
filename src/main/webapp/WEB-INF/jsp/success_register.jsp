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

            <img src="${pageContext.request.contextPath}/dogwalker_mini.jpg"
                 alt="logo" class="rounded" style="object-fit: cover; object-position: left; height: 3rem;">

        </nav>
    </div>
</div>

<div class="container p-3">
    <div class="row mx-auto">
        <div class="col d-flex justify-content-center">
            <div class="card" style="width: 40rem;background-color: #63646A">
                <div class="card-body">
                    <h5 class="card-title">Successful registration!</h5><br>
                    <h6>Now you can visit&nbsp;<a
                            href="${pageContext.request.contextPath}/login" class="link-info">login page</a> to
                        join/create
                        appointments. Wish you pleasant walks. </h6>
                </div>

                <img src="${pageContext.request.contextPath}/success_register.jpg" alt="Lights"
                     style="height: 25rem; object-fit: cover;"
                     class="card-img-bottom rounded">
            </div>
        </div>
    </div>
</div>
</body>
</html>
