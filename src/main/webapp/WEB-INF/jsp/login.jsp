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
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-6 text-black">

            <div class="px-5 ms-xl-4">
                <i class="fas fa-crow fa-2x me-3 pt-5 mt-xl-4" style="color: #709085;"></i>
                <span class="h1 fw-bold mb-0">Logo</span>
            </div>

            <br class="d-flex align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">

            <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Log in</h3>
            <form class="vh-100" action="${pageContext.request.contextPath}/process_login" method="post">
                <div class="form-outline mb-4">
                    <input type="text" name="username" id="form2Example18"
                           class="form-control form-control-lg"/>
                    <label class="form-label" for="form2Example18">Login</label>
                </div>

                <div class="form-outline mb-4">
                    <input type="password" name="password" id="form2Example28"
                           class="form-control form-control-lg"/>
                    <label class="form-label" for="form2Example28">Password</label>
                </div>

                <div class="pt-1 mb-4">
                    <button class="btn btn-primary" type="submit">Login</button>
                </div>

            </form>

            <p>Don't have an account? <a href="#!" class="link-info">Register here</a></p>

        </div>
        <div class="col-sm-6 px-0 d-none d-sm-block">
            <img src="https://i.pinimg.com/564x/0b/9b/ec/0b9bec6eb80ff1cd3d8fc90fe340a716.jpg"
                 alt="Login image" class="w-100 vh-100" style="object-fit: cover; object-position: left;">
        </div>
    </div>
</div>
</body>
</html>
