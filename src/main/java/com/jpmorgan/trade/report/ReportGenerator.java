package com.jpmorgan.trade.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.jpmorgan.trade.report.calculators.StatsCalculator;
import com.jpmorgan.trade.report.constants.Constant;
import com.jpmorgan.trade.report.entities.Trade;
import com.jpmorgan.trade.report.entities.TradeAction;
import com.jpmorgan.trade.report.exception.ReportException;
import com.jpmorgan.trade.report.ranking.Rank;
import com.jpmorgan.trade.report.validators.DefaultValidator;

/**
 * Main class to generate the reports of the trades
 */
public class ReportGenerator implements IReportGenerator {
    
	private StringBuilder stringBuilder = new StringBuilder();
	
	@Override
	public String generateInstructionsReport(List<Trade> trade) throws ReportException {

		// validate the trade input
		DefaultValidator validator = new DefaultValidator();
		for (Trade t : trade) {
			validator.validate(t);
		}

		generateDailyRanking(trade, 
		        generateDailyAmount(trade, stringBuilder));
		return stringBuilder.toString();
	}

	private static StringBuilder generateDailyAmount(final List<Trade> trade, final StringBuilder stringBuilder) {
	    // Call statCalculator to get actionWiseDailyAmountMap
		final Map<TradeAction, Map<LocalDate, BigDecimal>> actionWiseDailyAmountMap = StatsCalculator
				.calculateDailyAmount(trade);
		
		// call display method for each action type
		actionWiseDailyAmountMap.forEach((action, dailyAmountMap) -> {
		    dailyAmountDisplay(dailyAmountMap, action, stringBuilder);
		});
		return stringBuilder;
	}

	private static StringBuilder generateDailyRanking(final List<Trade> trade, final StringBuilder stringBuilder) {
		final Map<TradeAction, Map<LocalDate, LinkedList<Rank>>> actionWiseDailyRankingMap = StatsCalculator
				.calculateDailyRanking(trade);

		// call display method for each action type
		actionWiseDailyRankingMap.forEach((action, dailyRankMap) -> {
		    dailyRankingDisplay(dailyRankMap, action, stringBuilder);
        });
        return stringBuilder;
	}
	
	private static StringBuilder dailyAmountDisplay(final Map<LocalDate, BigDecimal> amountMap, final TradeAction tradeAction, final StringBuilder stringBuilder) {
        stringBuilder.append("\n----------------------------------------\n")
                .append("         ")
                .append(TradeAction.BUY.equals(tradeAction)?Constant.BUY_TRADE_DISPLAY:Constant.SELL_TRADE_DISPLAY)
                .append(" Daily Amount          \n")
                .append("----------------------------------------\n")
                .append("      Date       |    Trade Amount      \n")
                .append("----------------------------------------\n");
        
        amountMap.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .forEachOrdered(e -> stringBuilder.append(e.getKey()).append("       |      ").append(e.getValue()).append("\n"));
        return stringBuilder;
    }

	private static StringBuilder dailyRankingDisplay(final Map<LocalDate, LinkedList<Rank>> dailyRanking, final TradeAction tradeAction, final StringBuilder stringBuilder) {
		stringBuilder.append("\n----------------------------------------\n")
                .append("         ")
                .append(TradeAction.BUY.equals(tradeAction)?Constant.BUY_TRADE_DISPLAY:Constant.SELL_TRADE_DISPLAY)
                .append(" Daily Ranking          \n")
				.append("----------------------------------------\n")
				.append("     Date    |     Entity   |   Rank     \n")
				.append("----------------------------------------\n");

		dailyRanking.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .forEachOrdered(e -> e.getValue().forEach((rank) -> {
            stringBuilder.append(e.getKey()).append("   |      ").append(rank.getEntity()).append("     |    ")
            .append(rank.getRank()).append("\n");
        }));
		return stringBuilder;
	}
}
