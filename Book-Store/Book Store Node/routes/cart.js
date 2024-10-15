const express = require('express');
const router = express.Router();

// Cart Page
router.get('/', (req, res) => {
    const cart = req.session.cart || {};
    const books = {
        "Design Patterns: Elements of Reusable Object-Oriented Software": 59.99,
        "Patterns of Enterprise Application Architecture": 47.99,
        "Node.js Design Patterns": 39.99
    };

    let totalPrice = 0;

    const cartItems = Object.keys(cart).map(book => {
        const quantity = cart[book];
        const price = books[book];
        const total = quantity * price;
        totalPrice += total;
        return { book, price, quantity, total };
    });

    res.render('cart', { cartItems, totalPrice });
});

// Update Cart
router.post('/update', (req, res) => {
    const { book, quantity } = req.body;
    const qty = parseInt(quantity);

    if (qty > 0) {
        req.session.cart[book] = qty;
    } else {
        delete req.session.cart[book];
    }

    res.redirect('/cart');
});

// Checkout
router.get('/checkout', (req, res) => {
    req.session.cart = {};  // Clear cart
    res.render('checkout');
});

module.exports = router;
