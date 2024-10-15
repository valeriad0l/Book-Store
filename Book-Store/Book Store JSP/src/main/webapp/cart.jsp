<%@ page import="java.util.Map" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.util.HashMap" %>

<%
    // Access session to retrieve the cart
    Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
    if (cart == null || cart.isEmpty()) {
        cart = new HashMap<>();
    }

    // Remove item if requested
    String removeBook = request.getParameter("remove");
    if (removeBook != null && cart.containsKey(removeBook)) {
        cart.remove(removeBook);
    }

    // Update quantity if requested
    String updateBook = request.getParameter("book");
    String quantityStr = request.getParameter("quantity");
    if (updateBook != null && quantityStr != null) {
        int quantity = Integer.parseInt(quantityStr);
        if (quantity > 0) {
            cart.put(updateBook, quantity);
        }
    }

    session.setAttribute("cart", cart);  // Save updated cart
%>

<html>
<head>
    <title>Your Shopping Cart</title>
    <link rel="stylesheet" type="text/css" href="css/cart.css">
</head>
<body>
<h1>Your Shopping Cart</h1>

<%
    if (cart != null && !cart.isEmpty()) {
%>
<table>
    <thead>
    <tr>
        <th>Type</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Total</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <%
        double totalPrice = 0;
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String bookName = entry.getKey();
            int quantity = entry.getValue();
            double price = 0;

            if (bookName.equals("Design Patterns: Elements of Reusable Object-Oriented Software")) {
                price = 59.99;
            } else if (bookName.equals("Patterns of Enterprise Application Architecture")) {
                price = 47.99;
            } else if (bookName.equals("Node.js Design Patterns")) {
                price = 39.99;
            }

            double total = price * quantity;
            totalPrice += total;
    %>
    <tr>
        <td><%= bookName %></td>
        <td>$<%= price %></td>
        <td>
            <form method="POST" action="cart.jsp">
                <input type="hidden" name="book" value="<%= bookName %>">
                <input type="number" name="quantity" value="<%= quantity %>" min="1">
                <button type="submit">Update</button>
            </form>
        </td>
        <td>$<%= total %></td>
        <td>
            <form method="POST" action="cart.jsp">
                <input type="hidden" name="remove" value="<%= bookName %>">
                <button type="submit">Remove</button>
            </form>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
    <tfoot>
    <tr>
        <td colspan="3">Total Price:</td>
        <td colspan="2">$<%= totalPrice %></td>
    </tr>
    </tfoot>
</table>

<div>
    <a href="bookstore.jsp">Continue Shopping</a> |
    <a href="checkout.jsp">Checkout</a>
</div>
<%
} else {
%>
<p>Your cart is empty.</p>
<a href="bookstore.jsp">Continue Shopping</a>
<%
    }
%>
</body>
</html>





