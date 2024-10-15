package com.example.bookstoreservlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class BookStoreServlet extends HttpServlet {
    // Hardcoded book data
    private Map<String, Double> books;

    @Override
    public void init() throws ServletException {
        books = new HashMap<>();
        books.put("Design Patterns: Elements of Reusable Object-Oriented Software", 59.99);
        books.put("Patterns of Enterprise Application Architecture", 47.99);
        books.put("Node.js Design Patterns", 39.99);
    }

    // Display the browse page (GET request)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");

        // If cart is null, create a new one
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        // Browse page
        out.println("<html><head><title>Book Store</title>");
        // Link to external CSS file
        out.println("<link rel='stylesheet' type='text/css' href='css/bookstore.css'>");
        out.println("</head><body>");
        out.println("<h1>Welcome to SOEN387 Book Store</h1>");
        out.println("<h2>Available Books</h2>");
        out.println("<ul class='book-list'>");

        // Display the list of books
        for (Map.Entry<String, Double> entry : books.entrySet()) {
            String bookName = entry.getKey();
            Double price = entry.getValue();

            out.println("<li class='book-item'>");
            out.println("<span class='book-title'>" + bookName + "</span> - ");
            out.println("<span class='book-price'>$" + price + "</span>");
            out.println("<form method='POST' action='bookstore'>");
            out.println("<input type='hidden' name='book' value='" + bookName + "'>");
            out.println("Quantity: <input type='number' name='quantity' value='1' min='1'>");
            out.println("<button type='submit'>Add to Cart</button>");
            out.println("</form>");
            out.println("</li>");
        }
        out.println("</ul>");

        // Cart link
        out.println("<div><a href='cart'>Items in Cart: <img src='images/shoppingcart.png' class='cart-icon'></a></div>");
        out.println("</body></html>");
    }

    // Handle adding books to the cart (POST request)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String book = request.getParameter("book");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        // Access the session for storing the cart
        HttpSession session = request.getSession();
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");

        // Add to cart logic
        if (cart.containsKey(book)) {
            cart.put(book, cart.get(book) + quantity);
        } else {
            cart.put(book, quantity);
        }

        // Redirect back to the browse page after adding to cart
        response.sendRedirect("bookstore");
    }
}

