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
<div class="container">
    <div class="row mx-auto">
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
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
