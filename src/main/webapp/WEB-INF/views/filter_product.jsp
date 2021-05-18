<%--
  Created by IntelliJ IDEA.
  User: julia
  Date: 02.05.2021
  Time: 13:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<header>
    <meta charset="utf-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/file.css">
    <script  type="text/javascript" src="/js/common/filter.js"></script>
</header>
<body onload="loadCategory()">

<h6 align="center"></h6>
<form class="filterDiv" id="filterProduct">
    <h3>Filter</h3>

    <div class="form-group ">
        <label for="filterTitle">Product name</label>
        <input class="form-control" type="text" id="filterTitle" pattern="[abc]">
    </div>
    <div  id="categoryNameForm">
        <label for="category" >Category</label>
        <select class="form-select" id="category" name="category">
            <option >Choose...</option>
        </select>
    </div>
    <div>
        <label for="gender">Gender</label>
        <select class="form-select" id="gender" name="gender">
            <option value="0">Choose...</option>
            <option id="female" value="1">Female</option>
            <option id="mail" value="2">Mail</option>
        </select>
    </div>
    <div class="form-group ">
        <label for="filterMinCost">Min price, $</label>
        <input class="form-control" type="number" id="filterMinCost">
    </div>
    <div class="form-group">
        <label for="filterMaxCost">Max price, $</label>
        <input class="form-control" type="number" id="filterMaxCost">
    </div>
    <div >
        <div class="form-check">
            <input id="available" type="checkbox" class="form-check-input">
            <label class="form-check-label" for="available" style="margin-left: 6px">Available</label>
        </div>
    </div>
    <div id="notAvailableDiv"></div>

    <div style="margin-top: 10px">
        <button class="w-100 btn btn-success btn-lg" type="submit" id="filterButton">Apply</button>
    </div>
</form>
</body>
</html>
