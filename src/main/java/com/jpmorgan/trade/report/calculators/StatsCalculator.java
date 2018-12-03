package com.jpmorgan.trade.report.calculators;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toCollection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.jpmorgan.trade.report.entities.Trade;
import com.jpmorgan.trade.report.entities.TradeAction;
import com.jpmorgan.trade.report.ranking.Rank;

/**
 * Describes a mapping between dates and the trade amount of those dates, based
 * on instructions
 */
public class StatsCalculator {

    /**
     * Calculates the daily trade amount in USD
     * 
     * @param instructions
     *            (List of trades to calculate the stats from)
     * @return a map from trade action to date to total amount
     */
    public static Map<TradeAction, Map<LocalDate, BigDecimal>> calculateDailyAmount(List<Trade> instructions) {
        return instructions.stream().collect(groupingBy(Trade::getAction,
                groupingBy(DateCalculator::calculateSettlementDate, mapping(Trade::getTradeAmount, reducing(BigDecimal.ZERO, BigDecimal::add)))));
    }

    /**
     * Ranks the daily outgoing (BUY) by trade amount in USD
     * 
     * @param instructions
     *            the trade to calculate the stats from
     * @return a map from date to a list of ranks (ranking)
     */
    public static Map<TradeAction, Map<LocalDate, LinkedList<Rank>>> calculateDailyRanking(List<Trade> instructions) {
        final Map<TradeAction, Map<LocalDate, LinkedList<Rank>>> actionDateRankingMap = new HashMap<>();

        // Amount total per action per date per entity
        final Map<TradeAction, Map<LocalDate, Map<String, BigDecimal>>> actionWiseDailyEntityAmountMap = instructions.stream()
                .collect(groupingBy(Trade::getAction, groupingBy(DateCalculator::calculateSettlementDate,
                        groupingBy(Trade::getEntity, mapping(Trade::getTradeAmount, reducing(BigDecimal.ZERO, BigDecimal::add))))));

        actionWiseDailyEntityAmountMap.forEach((action, actionWiseMap) -> {
            final Map<LocalDate, LinkedList<Rank>> rankingMap = new HashMap<>();
            actionWiseMap.forEach((date, datewiseMap) -> {
                final AtomicInteger rank = new AtomicInteger(1);
                final LinkedList<Rank> ranks = datewiseMap.entrySet().stream().sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                        .map(entityWiseMap -> new Rank(rank.getAndIncrement(), entityWiseMap.getKey(), date)).collect(toCollection(LinkedList::new));
                rankingMap.put(date, ranks);
            });
            actionDateRankingMap.put(action, rankingMap);
        });
        return actionDateRankingMap;
    }
}
