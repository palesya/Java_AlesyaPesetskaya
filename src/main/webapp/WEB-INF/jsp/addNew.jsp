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
<div class="container p-3">
    <div class="row">
        <div class="col-sm-8 text-black p-3">

            <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Add new appointment</h3>

            <form action="${pageContext.request.contextPath}/dogwalker/addAddress/added" method="post">
                <div class="form-outline mb-4">
                    <label class="form-label">Address:</label>
                    <select typeof="submit" class="form-select" aria-label="Default select example"
                            name="selected_address">
                        <option selected>${selected_place.address}</option>
                        <c:forEach items="${allPlaces}" var="place">
                            <option>${place.address}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-outline mb-4">
                    <label class="form-label" for="form2Example18">Date:</label>
                    <input type="date" name="date" id="form2Example18"
                                class="form-control form-control-lg"/>
                </div>

                <div class="form-outline mb-4">
                    <label class="form-label" for="form2Example28">Time:</label>
                    <input type="time" name="time" id="form2Example28"
                           class="form-control form-control-lg" />
                </div>

                <div class="form-outline mb-4">
                    <label class="form-label" for="form3">Description:</label>
                    <input type="text" name="description" id="form3"
                           class="form-control form-control-lg" />
                </div>

                <div class="pt-1 mb-4">
                    <button class="btn btn-primary" type="submit">Create</button>
                </div>
            </form>

        </div>


        <div class="col-sm-4 px-0 d-none d-sm-block p-3">
            <img src="https://i.pinimg.com/564x/c7/68/7f/c7687f80f45bfc445d0b17b0bbd26710.jpg"
                 alt="image" class="rounded" style="object-fit: cover; object-position: left; height: 33rem;">
        </div>
    </div>
</div>

</body>
</html>
