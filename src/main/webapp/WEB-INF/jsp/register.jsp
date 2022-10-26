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
    <div class="row">
        <form class="col-sm-7 text-black p-3" action="${pageContext.request.contextPath}/register" method="post" enctype="multipart/form-data">

            <h3 class="fw-normal mb-3" style="letter-spacing: 1px;">Register new user</h3>

            <div class="row">
                <div class="col-md-9 mb-4">
                    <div class="form-outline">
                        <label class="form-label" for="form1">Login:</label>
                        <input type="text" name="login" id="form1" class="form-control form-control-lg"/>
                    </div>
                </div>
                <div class="col-md-3 mb-4">
                    <div class="form-outline">
                        <label class="form-label" for="form2">Age:</label>
                        <input type="number" name="user_age" id="form2" class="form-control form-control-lg"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 mb-4">
                    <div class="form-outline">
                        <label class="form-label" for="form3Example1m">Password:</label>
                        <input type="password" id="form3Example1m" name="password"
                               class="form-control form-control-lg"/>
                    </div>
                </div>
                <div class="col-md-6 mb-4">
                    <div class="form-outline">
                        <label class="form-label" for="form3Example1n">Repeat password: </label>
                        <input type="password" id="form3Example1n" name="repeat_password"
                               class="form-control form-control-lg"/>
                    </div>
                </div>
            </div>

            <div class="form-outline mb-4">
                <label for="Image" class="form-label">User's photo:</label>
                <input class="form-control" type="file" id="Image" name="user_image" size = "40">
            </div>

            <div class="row">
                <div class="col-md-9 mb-4">
                    <div class="form-outline">
                        <label class="form-label" for="form3">Dog's name:</label>
                        <input type="text" name="dog_name" id="form3" class="form-control form-control-lg"/>
                    </div>
                </div>
                <div class="col-md-3 mb-4">
                    <div class="form-outline">
                        <label class="form-label" for="form4">Dog's age:</label>
                        <input type="number" name="dog_age" id="form4" class="form-control form-control-lg"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-9 mb-4">
                    <div class="form-outline">
                        <label class="form-label" for="form5">Dog's type:</label>
                        <input type="text" name="dog_type" id="form5" class="form-control form-control-lg"/>
                    </div>
                </div>
                <div class="col-md-3 mb-4">
                    <div class="form-outline">
                        <label class="form-label">Dog's sex:</label><br>
                        <input class="form-check-input" type="radio" name="sex" id="girl" value="girl">
                        <label class="form-check-label" for="girl">Good girl</label><br>
                        <input class="form-check-input" type="radio" name="sex" id="boy" value="boy">
                        <label class="form-check-label" for="boy">Good boy</label>
                    </div>
                </div>
            </div>

            <div class="form-outline mb-4">
                <label for="ImageDog" class="form-label">Dog's photo:</label>
                <input class="form-control" type="file" id="ImageDog" name="dog_image">
                <script>
                </script>
            </div>
            <div class="pt-1 mb-4">
                <button class="btn btn-primary" type="submit">Create</button>
            </div>
        </form>

        <div class="col-sm-5 px-0 d-none d-sm-block p-3">
            <img src="${pageContext.request.contextPath}/register.jpg"
                 alt="image" class="rounded" style="object-fit: cover; object-position: left; height: 42rem;">
        </div>
    </div>
</div>
</body>
</html>
