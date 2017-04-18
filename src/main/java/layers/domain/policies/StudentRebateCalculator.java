package layers.domain.policies;

import java.math.BigDecimal;

/**
 * Policy variant
 */
public class StudentRebateCalculator implements RebateCalculator {

    @Override
    public BigDecimal calculateRebate(BigDecimal stockPrise) {
        return null;
    }
}
