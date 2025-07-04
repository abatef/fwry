public class ShippableProduct extends Product implements Shippable {

    private int weightInGrams;

    public ShippableProduct(String name, double price, int quantity, int weightInGrams) {
        super(name, price, quantity);
        this.weightInGrams = weightInGrams;
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
