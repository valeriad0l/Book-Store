package com.example.bookstoreservlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");

        out.println("<html><head><title>Checkout</title></head><body>");
        out.println("<h1>Checkout Complete!</h1>");

        if (cart != null) {
            cart.clear();  // Clear the cart after checkout
        }

        out.println("<a href='bookstore'>Go Back to Browse</a>");
        out.println("</body></html>");
    }
}
