import java.util.HashMap;
import java.util.Map;

public class ShippingService {
    private final static ShippingService INSTANCE = new ShippingService();
    private final Map<Customer, Map<Shippable, Integer>> shipments = new HashMap<>();

    private ShippingService() {
    }

    public static ShippingService getInstance() {
        return INSTANCE;
    }

    public void addShipment(Customer customer, Shippable product, int quantity) {
        Map<Shippable, Integer> map = shipments.get(customer);
        if (map == null) {
            map = new HashMap<>();
        }

        map.put(product, quantity);
        shipments.put(customer, map);
    }

    public void addShipment(Customer customer, Map<Shippable, Integer> shippables) {
        shipments.put(customer, shippables);
    }

    public void removeShipment(Customer customer, Shippable product) {
        Map<Shippable, Integer> map = shipments.get(customer);
        if (map != null) {
            map.remove(product);
        }
    }

    public void shipToCustomer(Customer customer) {
        Map<Shippable, Integer> map = shipments.get(customer);
        if (map == null || map.isEmpty()) {
            System.out.println(AnsiColors.ANSI_RED + "No customer shipping found" + AnsiColors.ANSI_RESET);
            ;
            return;
        }
        System.out.printf(AnsiColors.ANSI_GREEN + "Shipment For Customer: %s\n", customer);
        for (Map.Entry<Shippable, Integer> entry : map.entrySet()) {
            Shippable product = entry.getKey();
            int quantity = entry.getValue();
            System.out.printf("%2dx %-10s\n", quantity, product.getName());
        }
        System.out.println("Shipment complete." + AnsiColors.ANSI_RESET);
        System.out.println("------------------");
    }
}
