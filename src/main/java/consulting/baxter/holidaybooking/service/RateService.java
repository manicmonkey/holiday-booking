package consulting.baxter.holidaybooking.service;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;

@Component
public class RateService {
    public Money rateForDate(LocalDate date) {
        //high season is December and January
        if (date.getMonth() == Month.DECEMBER || date.getMonth() == Month.JANUARY)
            return Money.of(CurrencyUnit.AUD, 300);
        else
            return Money.of(CurrencyUnit.AUD, 250);
    }
}
