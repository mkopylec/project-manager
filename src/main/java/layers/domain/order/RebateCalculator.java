package layers.domain.order;

import java.math.BigDecimal;

/**
 * Policy
 */
public interface RebateCalculator {

    BigDecimal calculateRebate(BigDecimal stockPrise);
}
