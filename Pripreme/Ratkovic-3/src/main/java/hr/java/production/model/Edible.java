package hr.java.production.model;

import java.math.BigDecimal;

public interface Edible {
    int kcal();
    BigDecimal calculatePrice();
}
