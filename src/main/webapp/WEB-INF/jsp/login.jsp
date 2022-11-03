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
<div class="container-fluid p-3">
    <div class="row">
        <div class="col-sm-1"></div>
        <div class="col-sm-5 text-black">

            <div class="p-3">
                <img src="../dogwalker.jpg"
                     alt="Login image" class="rounded" style="object-fit: cover; object-position: left; height: 7rem;">
            </div>

            <div class="p-4">
                <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Log in</h3>
                <form class="vh-100" action="${pageContext.request.contextPath}/process_login" method="post" >
                    <div class="form-outline mb-4">
                        <c:if test="${param.error!=null}">
                            <p class="text-warning" style="line-height: 25px;">You've entered wrong credentials!</p>
                        </c:if>
                        <label class="form-label" for="login">Login</label>
                        <input type="text" name="username" id="login" class="form-control form-control-lg"
                                  placeholder="4-30 symbols"/>
                    </div>

                    <div class="form-outline mb-3">
                        <label class="form-label" for="password">Password</label>
                        <input type="password" id="password" name="password" class="form-control form-control-lg"
                                  placeholder="4-20 symbols"/>
                    </div>
                    <br>
                    <div class="pt-1 mb-3">
                        <button class="btn btn-primary" type="submit">Login</button>
                    </div>
                    <br>
                    <p class="d-flex justify-content-center">Don't have an account?&nbsp;<a
                            href="${pageContext.request.contextPath}/register" class="link-info">Register
                        here</a></p>

                </form>
            </div>

        </div>
        <div class="col-sm-5 p-3 d-flex justify-content-center">
            <img src="${pageContext.request.contextPath}/login.jpg"
                 alt="Login image" class="rounded" style="object-fit: cover; object-position: left; height: 34rem;">
        </div>
    </div>
</div>
</body>
</html>
