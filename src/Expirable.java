import java.time.LocalDate;

public interface Expirable {
    LocalDate getExpiryDate();
    void setExpiryDate(LocalDate expiryDate);
}
