package layers.domain.order;

import java.math.BigDecimal;

/**
 * Policy variant
 */
public class WeekendRebateCalculator implements RebateCalculator {

    @Override
    public BigDecimal calculateRebate(BigDecimal stockPrise) {
        return null;
    }
}
