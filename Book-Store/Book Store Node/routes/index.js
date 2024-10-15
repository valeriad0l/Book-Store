const express = require('express');
const router = express.Router();

// Hardcoded book data
const books = {
  "Design Patterns: Elements of Reusable Object-Oriented Software": 59.99,
  "Patterns of Enterprise Application Architecture": 47.99,
  "Node.js Design Patterns": 39.99
};

// Browse Page
router.get('/', (req, res) => {
  let cartItems = 0;
  if (req.session.cart) {
    cartItems = Object.values(req.session.cart).reduce((acc, curr) => acc + curr, 0);
  }
  res.render('bookstore', { books, cartItems });
});

// Handle adding to cart
router.post('/', (req, res) => {
  const { book, quantity } = req.body;
  const qty = parseInt(quantity);

  if (!req.session.cart) {
    req.session.cart = {};
  }

  if (req.session.cart[book]) {
    req.session.cart[book] += qty;
  } else {
    req.session.cart[book] = qty;
  }

  res.redirect('/');
});

module.exports = router;

