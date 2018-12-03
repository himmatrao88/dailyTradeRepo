package com.jpmorgan.trade.report.calculators;

import org.junit.Test;

import com.jpmorgan.trade.report.calculators.DateCalculator;
import com.jpmorgan.trade.report.entities.Trade;
import com.jpmorgan.trade.report.entities.TradeAction;
import com.jpmorgan.trade.report.entities.TradeDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.Assert.assertEquals;

public class DateCalculatorTest {
    @Test
    public void calculateSettlementDate_default_Friday() throws Exception {
        final LocalDate initialSettlementDate = LocalDate.of(2018, 11, 30); // Its a Friday

        final Trade trade = new Trade(
                "E1",
                TradeAction.BUY,
                LocalDate.of(2018, 11, 30),
                initialSettlementDate,
                new TradeDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(0.50),
                        200,
                        BigDecimal.valueOf(100.25)));

        // calculate new settlement day
        DateCalculator.calculateSettlementDate(trade);

        // should be the same
        assertEquals(initialSettlementDate, trade.getEffectiveSettlementDate());
    }

    @Test
    public void calculateSettlementDate_default_Sunday() throws Exception {
        final LocalDate initialSettlementDate = LocalDate.of(2018, 12, 2); // Its a Sunday

        final Trade trade = new Trade(
                "E1",
                TradeAction.BUY,
                LocalDate.of(2018, 11, 30),
                initialSettlementDate,
                new TradeDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(1),
                        200,
                        BigDecimal.valueOf(100.25)));

        // calculate new settlement day
        DateCalculator.calculateSettlementDate(trade);

        // should be the first monday (3/12/2018)
        assertEquals(LocalDate.of(2018, 12, 3), trade.getEffectiveSettlementDate());
    }

    @Test
    public void calculateSettlementDate_arabia_Friday() throws Exception {
        final LocalDate initialSettlementDate = LocalDate.of(2018, 11, 30); // Its a Friday

        final Trade trade = new Trade(
                "E1",
                TradeAction.BUY,
                LocalDate.of(2018, 11, 30),
                initialSettlementDate,
                new TradeDetails(
                        Currency.getInstance("AED"), // Its Arabia (AED)
                        BigDecimal.valueOf(0.50),
                        200,
                        BigDecimal.valueOf(100.25)));

        // calculate new settlement day
        DateCalculator.calculateSettlementDate(trade);

        // should be the first Sunday (2/12/2018)
        assertEquals(LocalDate.of(2018, 12, 2), trade.getEffectiveSettlementDate());
    }

    @Test
    public void calculateSettlementDate_arabia_Sunday() throws Exception {
        final LocalDate initialSettlementDate = LocalDate.of(2018, 12, 2); // Its a Sunday

        final Trade trade = new Trade(
                "E1",
                TradeAction.BUY,
                LocalDate.of(2018, 11, 30),
                initialSettlementDate,
                new TradeDetails(
                        Currency.getInstance("SAR"), // Its Arabia (SAR)
                        BigDecimal.valueOf(0.50),
                        200,
                        BigDecimal.valueOf(100.25)));

        // calculate new settlement day
        DateCalculator.calculateSettlementDate(trade);

        // should be the same
        assertEquals(initialSettlementDate, trade.getEffectiveSettlementDate());
    }
}