<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<header>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/file.css">
    <script  type="text/javascript" src="/js/common/filter.js"></script>
</header>
<head>
    <title></title>
</head>
<body onload="loadCategory()">

<h6 align="center"></h6>
<form class="filterDiv" id="filterProduct">
    <h3><fmt:message key="label.filter" /></h3>

    <div class="form-group ">
        <label for="filterTitle"><fmt:message key="label.name" /></label>
        <input class="form-control" type="text" id="filterTitle">
    </div>
    <div  id="categoryNameForm">
        <label for="category" ><fmt:message key="label.category" /></label>
        <select class="form-select" id="category" name="category">
            <option  value="0"><fmt:message key="label.choose" /></option>
        </select>
    </div>
    <div>
        <label for="gender"><fmt:message key="label.gender" /></label>
        <select class="form-select" id="gender" name="gender">
            <option value="0"><fmt:message key="label.choose" /></option>
            <option id="female" value="1"><fmt:message key="label.female" /></option>
            <option id="mail" value="2"><fmt:message key="label.male" /></option>
        </select>
    </div>
    <div class="form-group ">
        <label for="filterMinCost"><fmt:message key="label.Min_price" />, $</label>
        <input class="form-control" type="number" id="filterMinCost">
    </div>
    <div class="form-group">
        <label for="filterMaxCost"><fmt:message key="label.Max_price" /> , $</label>
        <input class="form-control" type="number" id="filterMaxCost">
    </div>
    <div >
        <div class="form-check">
            <input id="available" type="checkbox" class="form-check-input">
            <label class="form-check-label" for="available" style="margin-left: 6px"><fmt:message key="label.Available" /></label>
        </div>
    </div>
    <div id="notAvailableDiv"></div>

    <div style="margin-top: 10px">
        <button class="w-100 btn btn-success btn-lg" type="submit" id="filterButton"><fmt:message key="label.Apply" /></button>
    </div>
</form>
</body>
</html>
