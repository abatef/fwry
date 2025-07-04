public class WeighUtils {
    public static double getWeighInKilograms(int weightInGrams) {
        return (double) weightInGrams / 1000;
    }

    public static String weightToString(int weightInGrams) {
        if (weightInGrams >= 1000) {
            return String.format("%.2fkg", (double) weightInGrams / 1000);
        }
        return (weightInGrams) + "g";
    }
}
