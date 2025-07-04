import java.time.LocalDate;

public class ShippableExpirableProduct extends Product implements Shippable, Expirable {
    private int weightInGrams;
    private LocalDate expiryDate;

    public ShippableExpirableProduct(String name, double price, int quantity, int weightInGrams, LocalDate expiryDate) {
        super(name, price, quantity);
        this.weightInGrams = weightInGrams;
        this.expiryDate = expiryDate;
    }

    @Override
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    @Override
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public int getWeightInGrams() {
        return weightInGrams;
    }

    @Override
    public void setWeightInGrams(int weightInGrams) {
        this.weightInGrams = weightInGrams;
    }
}
