package hr.java.production.model;

import java.math.BigDecimal;

public class CurrencyUtils {
    public static String format(BigDecimal value, String currency) {
        if (currency.equals("kn")) return value + currency;
        else return currency + value;
    }
}
