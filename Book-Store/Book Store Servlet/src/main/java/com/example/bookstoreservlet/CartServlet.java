package com.example.bookstoreservlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");

        out.println("<html><head><title>Shopping Cart</title>");
        out.println("<link rel='stylesheet' type='text/css' href='css/cart.css'>");
        out.println("</head><body>");
        out.println("<h1>Your Shopping Cart</h1>");

        if (cart == null || cart.isEmpty()) {
            out.println("<p>Your cart is empty.</p>");
        } else {
            out.println("<table border='1'>");
            out.println("<tr><th>Type</th><th>Price</th><th>Quantity</th><th>Total</th><th>Action</th></tr>");

            double totalPrice = 0;
            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                String bookName = entry.getKey();
                Integer quantity = entry.getValue();
                double price = getBookPrice(bookName);
                double total = price * quantity;

                out.println("<tr>");
                out.println("<td>" + bookName + "</td>");
                out.println("<td>$" + price + "</td>");
                out.println("<td>");
                out.println("<form method='POST' action='cart'>");
                out.println("<input type='hidden' name='book' value='" + bookName + "'>");
                out.println("<input type='number' name='quantity' value='" + quantity + "' min='1'>");
                out.println("<button type='submit' name='action' value='update'>Update</button>");
                out.println("</form>");
                out.println("</td>");
                out.println("<td>$" + total + "</td>");
                out.println("<td>");
                out.println("<form method='POST' action='cart'>");
                out.println("<input type='hidden' name='book' value='" + bookName + "'>");
                out.println("<button type='submit' name='action' value='remove'>Remove</button>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");

                totalPrice += total;
            }
            out.println("</table>");
            out.println("<h3>Total Price: $" + totalPrice + "</h3>");
        }

        out.println("<a href='bookstore'>Continue Shopping</a>");
        out.println("<br>");
        out.println("<a href='checkout'>Checkout</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String book = request.getParameter("book");

        HttpSession session = request.getSession();
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");

        if (action.equals("update")) {
            // Check if quantity is not null or empty before parsing
            String quantityStr = request.getParameter("quantity");
            if (quantityStr != null && !quantityStr.isEmpty()) {
                int quantity = Integer.parseInt(quantityStr);
                if (quantity == 0) {
                    request.setAttribute("error", "Quantity cannot be zero");
                } else {
                    cart.put(book, quantity);
                }
            }
        } else if (action.equals("remove")) {
            // For remove action, no need to parse quantity
            cart.remove(book);
        }

        response.sendRedirect("cart");
    }

    private double getBookPrice(String book) {
        switch (book) {
            case "Design Patterns: Elements of Reusable Object-Oriented Software":
                return 59.99;
            case "Patterns of Enterprise Application Architecture":
                return 47.99;
            case "Node.js Design Patterns":
                return 39.99;
            default:
                return 0;
        }
    }
}

