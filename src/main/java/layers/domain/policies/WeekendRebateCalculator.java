package layers.domain.policies;

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
