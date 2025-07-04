import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private final Map<Product, Integer> products = new HashMap<>();

    public void add(Product product, int quantity) {
        products.put(product, quantity);
    }

    public void remove(Product product) {
        products.remove(product);
    }

    public void clear() {
        products.clear();
    }

    private void doChecks() {
        if (products.isEmpty()) {
            throw new RuntimeException("Empty Cart");
        }
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if (quantity <= 0 || quantity > product.getQuantity()) {
                remove(product);
                throw new RuntimeException("Insufficient Quantity of Product: " + product.getName() +
                        "\nRequired: " + quantity + " Stock: " + product.getQuantity());
            }
            if (product instanceof Expirable) {
                Expirable expirable = (Expirable) product;
                if (expirable.getExpiryDate().isBefore(LocalDate.now())) {
                    remove(product);
                    throw new RuntimeException("Expired Product: " + product.getName() +
                            "\nExpired at: " + expirable.getExpiryDate());
                }
            }

        }
    }

    public double checkout() {
        doChecks();
        StringBuilder shipmentNotice = new StringBuilder();
        StringBuilder checkoutNotice = new StringBuilder();
        double subtotal = 0.0;
        int totalWeightInGrams = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double due = product.getPrice() * quantity;
            subtotal += due;
            checkoutNotice.append(String.format("%2dx %-10s %-4.2f", quantity, product.getName(), due));
            checkoutNotice.append("\n");
            if (product instanceof Shippable) {
                Shippable shippable = (Shippable) product;
                shipmentNotice.
                        append(String.format("%2dx %-10s %-4s",
                                quantity,
                                product.getName(),
                                WeighUtils.getWeighInKilograms(shippable.getWeightInGrams())));
                shipmentNotice.append("\n");
                totalWeightInGrams += shippable.getWeightInGrams();
            }
        }
        if (shipmentNotice.length() > 0 && checkoutNotice.length() > 0) {
            shipmentNotice.replace(shipmentNotice.length() - 1, shipmentNotice.length(), "");
            checkoutNotice.replace(checkoutNotice.length() - 1, checkoutNotice.length(), "");
        }
        double shippingTotal = WeighUtils.getWeighInKilograms(totalWeightInGrams) * 0.25;
        printCart(shipmentNotice.toString(), checkoutNotice.toString(), totalWeightInGrams, subtotal, shippingTotal);
        return subtotal + shippingTotal;
    }

    public void completeCheckout(Customer customer) {
        Map<Shippable, Integer> shipping = new HashMap<>();
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.deductQuantity(quantity);
            if (product instanceof Shippable) {
                Shippable shippable = (Shippable) product;
                shipping.put(shippable, quantity);
            }
        }
        ShippingService.getInstance().addShipment(customer, shipping);
        ShippingService.getInstance().shipToCustomer(customer);
        clear();
    }

    private void printCart(String shipmentNotice, String checkoutNotice, int totalWeightInGrams, double subtotal, double shippingTotal) {
        if (!shipmentNotice.isEmpty()) {
            System.out.println("** Shipment Notice **");
            System.out.println(shipmentNotice);
            System.out.println("Total Weight: " + WeighUtils.weightToString(totalWeightInGrams) + "\n");
        }
        System.out.println("** Checkout Receipt **");
        System.out.println(checkoutNotice);
        System.out.println("------------------");
        System.out.printf("Subtotal       %4.2f\n", subtotal);
        System.out.printf("Shipping       %4.2f\n", shippingTotal);
        System.out.printf("Total Amount   %4.2f\n", subtotal + shippingTotal);
    }
}
