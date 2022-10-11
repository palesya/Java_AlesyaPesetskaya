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
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-6 text-black">

            <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">New appointment was successfully created</h3>

                <div class="form-outline mb-4">
                    <label class="form-label">Address:</label>
                    <p>${added_address}</p>
                </div>

                <div class="form-outline mb-4">
                    <label class="form-label" >Date:</label>
                    <p>${added_date}</p>
                </div>

                <div class="form-outline mb-4">
                    <label class="form-label" >Time:</label>
                    <p>${added_time}</p>
                </div>

                <div class="form-outline mb-4">
                    <label class="form-label" >Description:</label>
                    <p>${added_description}</p>
                </div>

        </div>


        <div class="col-sm-6 px-0 d-none d-sm-block">
            <img src="https://i.pinimg.com/564x/05/ca/d7/05cad708c6fbfdeec648bf21b9d1bc27.jpg"
                 alt="image" class="w-100 vh-100" style="object-fit: cover; object-position: left;">
        </div>
    </div>
</div>

</body>
</html>
