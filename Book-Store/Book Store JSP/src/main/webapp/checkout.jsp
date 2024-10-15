<%@ page import="java.util.Map" %>
<%
    // Clear the cart after checkout
    Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
    if (cart != null) {
        cart.clear();
    }
%>
<html>
<head>
    <title>Checkout</title>
</head>
<body>
<h1>Checkout Complete!</h1>
<a href="bookstore.jsp">Go Back to Book Store</a>
</body>
</html>
