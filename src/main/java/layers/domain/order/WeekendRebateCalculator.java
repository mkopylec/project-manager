package layers.domain.order;

import java.math.BigDecimal;
import java.time.DayOfWeek;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.ZonedDateTime.now;

/**
 * Policy variant
 */
public class WeekendRebateCalculator implements RebateCalculator {

    @Override
    public BigDecimal calculateRebate(BigDecimal stockPrise) {
        return isWeekend() ? stockPrise.multiply(valueOf(0.05)) : ZERO;
    }

    private boolean isWeekend() {
        DayOfWeek day = now().getDayOfWeek();
        return day == SATURDAY || day == SUNDAY;
    }
}
