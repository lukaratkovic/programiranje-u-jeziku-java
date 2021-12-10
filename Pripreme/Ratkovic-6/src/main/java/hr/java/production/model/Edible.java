package hr.java.production.model;

import java.math.BigDecimal;

/**
 * Contains methods for retrieval of kilocalories and total price of item
 */
public interface Edible {
    /**
     * Blueprint for retrieval of kilocalories
     *
     * @return kilocalories
     */
    int kcal();

    /**
     * Blueprint for retrieval of calculated price
     *
     * @return calculated price
     */
    BigDecimal calculatePrice();
}
