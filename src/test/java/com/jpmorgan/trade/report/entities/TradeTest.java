package com.jpmorgan.trade.report.entities;

import org.junit.Test;

import com.jpmorgan.trade.report.entities.Trade;
import com.jpmorgan.trade.report.entities.TradeAction;
import com.jpmorgan.trade.report.entities.TradeDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.Assert.assertEquals;

public class TradeTest {

    @Test
    public void testTradeAmountCalc() throws Exception {
        final BigDecimal agreedFx = BigDecimal.valueOf(0.50);
        final BigDecimal pricePerUnit = BigDecimal.valueOf(100.25);
        final int units = 200;

        final Trade trade = new Trade(
                "E1",
                TradeAction.BUY,
                LocalDate.of(2018, 12, 3),
                LocalDate.of(2018, 12, 3), // Its a Monday
                new TradeDetails(
                        Currency.getInstance("SGD"),
                        agreedFx,
                        units,
                        pricePerUnit));

        assertEquals(agreedFx, trade.getAgreedFx());
        assertEquals(pricePerUnit, trade.getPricePerUnit());
        assertEquals(units, trade.getUnits());

        final BigDecimal correct = pricePerUnit
                                    .multiply(agreedFx)
                                    .multiply(BigDecimal.valueOf(units))
                                    .setScale(2, BigDecimal.ROUND_HALF_EVEN);
        assertEquals(correct, trade.getTradeAmount());
    }
}