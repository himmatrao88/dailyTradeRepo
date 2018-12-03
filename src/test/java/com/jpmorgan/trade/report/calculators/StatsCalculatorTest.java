package com.jpmorgan.trade.report.calculators;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Test;

import com.jpmorgan.trade.report.calculators.StatsCalculator;
import com.jpmorgan.trade.report.entities.Trade;
import com.jpmorgan.trade.report.entities.TradeAction;
import com.jpmorgan.trade.report.entities.TradeDetails;
import com.jpmorgan.trade.report.ranking.Rank;

public class StatsCalculatorTest {

    private static final LocalDate MONDAY    = LocalDate.of(2018, 12, 3);
    private static final LocalDate TUESDAY   = LocalDate.of(2018, 12, 4);
    private static final LocalDate WEDNESDAY = LocalDate.of(2018, 12, 5);
    private static final LocalDate SATURDAY  = LocalDate.of(2018, 12, 1);
    private static final LocalDate SUNDAY    = LocalDate.of(2018, 12, 2);

    private static List<Trade> getListOfTrades() {
        final List<Trade> instructions = new ArrayList<>();

        // ===========================================================================
        // All these should be under the same settlement date (3/12/2018)
        // ===========================================================================
        instructions.add(new Trade(
                "E1",
                TradeAction.BUY,
                LocalDate.of(2018, 11 ,30),
                MONDAY,
                new TradeDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(1),
                        100,
                        BigDecimal.valueOf(1))));

        instructions.add(new Trade(
                "E2",
                TradeAction.BUY,
                LocalDate.of(2018, 11 ,30),
                MONDAY,
                new TradeDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(1),
                        200,
                        BigDecimal.valueOf(1))));

        instructions.add(new Trade(
                "E3",
                TradeAction.BUY,
                LocalDate.of(2018, 11 ,30),
                SATURDAY,
                new TradeDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(1),
                        300,
                        BigDecimal.valueOf(1))));

        instructions.add(new Trade(
                "E4",
                TradeAction.SELL,
                LocalDate.of(2018, 11 ,30),
                SUNDAY,
                new TradeDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(1),
                        200,
                        BigDecimal.valueOf(1))));

        // ===========================================================================
        // All these should be under the same settlement date (4/12/2018)
        // ===========================================================================
        instructions.add(new Trade(
                "E5",
                TradeAction.BUY,
                LocalDate.of(2018, 11 ,30),
                TUESDAY,
                new TradeDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(1),
                        400,
                        BigDecimal.valueOf(1))));

        instructions.add(new Trade(
                "E6",
                TradeAction.SELL,
                LocalDate.of(2018, 11 ,30),
                TUESDAY,
                new TradeDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(1),
                        1000,
                        BigDecimal.valueOf(1))));

        // ===========================================================================
        // All these should be under the same settlement date (5/12/2018)
        // ===========================================================================
        instructions.add(new Trade(
                "E7",
                TradeAction.BUY,
                LocalDate.of(2018, 11 ,30),
                WEDNESDAY,
                new TradeDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(1),
                        7000,
                        BigDecimal.valueOf(1))));

        return instructions;
    }

    @Test
    public void testDailyIncomingAmount() throws Exception {
        final Map<LocalDate, BigDecimal> dailyIncomingAmount =
                StatsCalculator.calculateDailyAmount(getListOfTrades()).get(TradeAction.SELL);

        assertEquals(2, dailyIncomingAmount.size());
        assertTrue(Objects.equals(dailyIncomingAmount.get(MONDAY), BigDecimal.valueOf(200.00).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
        assertTrue(Objects.equals(dailyIncomingAmount.get(TUESDAY), BigDecimal.valueOf(1000.00).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
    }

    @Test
    public void testDailyOutgoingAmount() throws Exception {
        final Map<LocalDate, BigDecimal> dailyOutgoingAmount =
                StatsCalculator.calculateDailyAmount(getListOfTrades()).get(TradeAction.BUY);

        assertEquals(3, dailyOutgoingAmount.size());
        assertTrue(Objects.equals(dailyOutgoingAmount.get(MONDAY), BigDecimal.valueOf(600.00).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
        assertTrue(Objects.equals(dailyOutgoingAmount.get(TUESDAY), BigDecimal.valueOf(400.00).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
    }

    @Test
    public void testDailyIncomingRanking() throws Exception {
        final Map<LocalDate, LinkedList<Rank>> dailyIncomingRanking =
                StatsCalculator.calculateDailyRanking(getListOfTrades()).get(TradeAction.SELL);

        assertEquals(2, dailyIncomingRanking.size());

        assertEquals(1, dailyIncomingRanking.get(MONDAY).size());
        assertEquals(1, dailyIncomingRanking.get(TUESDAY).size());

        assertTrue(dailyIncomingRanking.get(MONDAY).contains(new Rank(1, "E4", MONDAY)));
        assertTrue(dailyIncomingRanking.get(TUESDAY).contains(new Rank(1, "E6", TUESDAY)));

    }

    @Test
    public void testDailyOutgoingRanking() throws Exception {
        final Map<LocalDate, LinkedList<Rank>> dailyOutgoingRanking =
                StatsCalculator.calculateDailyRanking(getListOfTrades()).get(TradeAction.BUY);

        assertEquals(3, dailyOutgoingRanking.size());

        assertEquals(3, dailyOutgoingRanking.get(MONDAY).size());
        assertEquals(1, dailyOutgoingRanking.get(TUESDAY).size());
        assertEquals(1, dailyOutgoingRanking.get(WEDNESDAY).size());

        assertTrue(dailyOutgoingRanking.get(MONDAY).contains(new Rank(1, "E3", MONDAY)));
        assertTrue(dailyOutgoingRanking.get(MONDAY).contains(new Rank(2, "E2", MONDAY)));
        assertTrue(dailyOutgoingRanking.get(MONDAY).contains(new Rank(3, "E1", MONDAY)));

        assertTrue(dailyOutgoingRanking.get(TUESDAY).contains(new Rank(1, "E5", TUESDAY)));

        assertTrue(dailyOutgoingRanking.get(WEDNESDAY).contains(new Rank(1, "E7", WEDNESDAY)));
    }
}