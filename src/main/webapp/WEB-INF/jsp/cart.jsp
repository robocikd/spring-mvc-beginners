<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.1/angular.min.js"></script>
    <script src="/resource/js/controllers.js"></script>
    <title>Koszyk</title>
</head>
<body>
<section>
</section>
<section class="container" ng-app="cartApp">
    <div ng-controller="cartCtrl" ng-init="initCartId('${cartId}')">
        <div class="jumbotron">
            <div class="container">
                <h1>Koszyk</h1>
                <p>Produkty w Twoim koszyku</p>
            </div>
        </div>
        <div>
            <a class="btn brn-danger pull-left" ng-click="clearCart()">
                <span class="glyphicon glyphicon-remove-sign"></span>Wyczyść koszyk
            </a>
            <a href="<spring:url value="/checkout?cartId=${cartId}"/>" class="btn btn-success pull-right">
                <span class="glyphicon-shopping-cart glyphicon"></span>Kupuję
            </a>
        </div>
        <table class="table table-hover">
            <tr>
                <th>Produkt</th>
                <th>Cena za sztukę</th>
                <th>Liczba sztuk</th>
                <th>Cena</th>
                <th>Akcja</th>
            </tr>
            <tr ng-repeat="item in cart.cartItems" >
                <td>{{item.product.productId}}-{{item.product.name}}</td>
                <td>{{item.product.unitPrice}}</td>
                <td>{{item.quantity}}</td>
                <td>{{item.totalPrice}}</td>
                <td>
                    <a href="#" class="label label-danger" ng-click="removeFromCart(item.product.productId)">
                        <span class="glyphicon glyphicon-remove"></span>Usuń
                    </a>
                </td>
            </tr>
            <tr>
                <th></th>
                <th></th>
                <th>Łączna cena</th>
                <th>{{cart.grandTotal}}</th>
                <th></th>
            </tr>
        </table>
        <a href="<spring:url value="/products"/>" class="btn btndefault">
            <span class="glyphicon-hand-left glyphicon"></span>Wróć do zakupów
        </a>
    </div>
</section>

</body>
</html>
