# Fawry Internship Challenge

The Solution to Fawry Internship Challenge

A Cart for an e-commerse system

## Features

- **Product Types**:
  - Regular products
  - Shippable products (with weight)
  - Expirable products (with expiry date)
  - Products that are both shippable and expirable

- **Cart Functionality**:
  - Add or remove products
  - Quantity validation
  - Expiration date checking

- **Checkout Process**:
  - Balance verification
  - Receipt generation
  - Shipping notice for shippable products
  - inventory deduction

- **Shipping Service**:
  - Manages Shipments for Customers

## Class Structure

- `Product`: Base product class
- `ShippableProduct`: Products that can be shipped implements `Shippable` interface.
- `ExpirableProduct`: Products with expiration dates implements `Expirable` interface.
- `ShippableExpirableProduct`: Products with both shipping and expiration implements both `Expirable` and `Shippable` interfaces.
- `Cart`: Manages products and quantities
- `CartService`: Handles cart operations
- `ShippingService`: Manages product shipments
- `Customer`: Customer information and balance

## Usage Example

```java
Product p1 = new ShippableProduct("TV", 9000, 12, 5000);
Product p2 = new ShippableProduct("Phone", 5000, 20, 240);
Product p3 = new ShippableExpirableProduct("Cheese", 50, 1, 1000, LocalDate.now().plusMonths(6));
Product p4 = new ShippableExpirableProduct("Biscuit", 50, 40, 20, LocalDate.now().minusDays(6));
Product p5 = new Product("Phone Card", 50, 100);

Customer customer = new Customer("John");
customer.setBalance(20000);

CartService cartService = CartService.getInstance();
cartService.createCart(customer);

cartService.addProduct(customer, p1, 1); // Ok
cartService.checkout(customer);
cartService.addProduct(customer, p2, 1); // Ok
cartService.checkout(customer);
cartService.addProduct(customer, p3, 2); // Error: Insufficient Quantity
cartService.checkout(customer);
cartService.addProduct(customer, p4, 3); // Error: Expired Product
cartService.checkout(customer);
cartService.addProduct(customer, p5, 4); // Ok Non-Shippable Non-Expirable Product So Shipping Fails
cartService.checkout(customer);
cartService.addProduct(customer, p1, 1); // Error: Insufficient Balance
cartService.checkout(customer);
```

## Error Handling

The system provides clear error messages for:
- Insufficient product quantities
- Expired products
- Insufficient customer balance
- Checkout with Empty Cart

## Utilities

- `WeighUtils`: Converts between grams and kilograms
- `AnsiColors`: Provides colored console output
