<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>

<%
    // Hardcoded book data
    Map<String, Double> books = new HashMap<>();
    books.put("Design Patterns: Elements of Reusable Object-Oriented Software", 59.99);
    books.put("Patterns of Enterprise Application Architecture", 47.99);
    books.put("Node.js Design Patterns", 39.99);

    // Access session to retrieve or initialize the cart
    Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");
    if (cart == null) {
        cart = new HashMap<>();
        session.setAttribute("cart", cart);
    }

    // Handle form submission to add items to the cart
    String book = request.getParameter("book");
    String quantityStr = request.getParameter("quantity");

    if (book != null && quantityStr != null) {
        int quantity = Integer.parseInt(quantityStr);

        // Add the selected book to the cart (or update the quantity if it already exists)
        if (cart.containsKey(book)) {
            cart.put(book, cart.get(book) + quantity);  // Add to existing quantity
        } else {
            cart.put(book, quantity);  // Add new book with quantity
        }

        // Save the updated cart in the session
        session.setAttribute("cart", cart);
    }

    // Get total cart items
    int cartItems = 0;
    for (Integer quantity : cart.values()) {
        cartItems += quantity;
    }
%>

<html>
<head>
    <title>Book Store</title>
    <link rel="stylesheet" type="text/css" href="css/bookstore.css">
</head>
<body>
<h1>Welcome to SOEN387 Book Store</h1>
<h2>Available Books</h2>

<ul class="book-list">
    <%
        // Display the list of books with an "Add to Cart" button
        for (Map.Entry<String, Double> entry : books.entrySet()) {
            String bookName = entry.getKey();
            Double price = entry.getValue();
    %>
    <li class="book-item">
        <span class="book-title"><%= bookName %></span> -
        <span class="book-price">$<%= price %></span>
        <form method="POST" action="bookstore.jsp">
            <input type="hidden" name="book" value="<%= bookName %>">
            Quantity: <input type="number" name="quantity" value="1" min="1">
            <button type="submit">Add to Cart</button>
        </form>
    </li>
    <%
        }
    %>
</ul>

<!-- Items in Cart Link -->
<div>
    <a href="cart.jsp">Items in Cart (<%= cartItems %>): <img src="images/shoppingcart.png" class="cart-icon"></a>
</div>
</body>
</html>
