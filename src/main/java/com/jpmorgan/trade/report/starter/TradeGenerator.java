package com.jpmorgan.trade.report.starter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import com.jpmorgan.trade.report.entities.Trade;
import com.jpmorgan.trade.report.entities.TradeAction;
import com.jpmorgan.trade.report.entities.TradeDetails;

public class TradeGenerator {
	public static List<Trade> getTradeInstructions() {
		return Arrays.asList(
		        
		//  ALL THESE SHOULD BE SETTLED ON 30-11-2018
		new Trade("Enity1", TradeAction.SELL, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 11, 30), new TradeDetails(
				Currency.getInstance("GBP"), BigDecimal.valueOf(0.50), 200, BigDecimal.valueOf(100.25))),
		
		new Trade("Enity2", TradeAction.BUY, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 11, 30), new TradeDetails(
                Currency.getInstance("GBP"), BigDecimal.valueOf(0.50), 200, BigDecimal.valueOf(100.25))),

		new Trade("Enity2", TradeAction.SELL, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 11, 30), new TradeDetails(
				Currency.getInstance("SGD"), BigDecimal.valueOf(0.22), 450, BigDecimal.valueOf(150.5))),
		
		new Trade("Enity3", TradeAction.BUY, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 11, 30),
				new TradeDetails(Currency.getInstance("GBP"), BigDecimal.valueOf(0.34), 250, BigDecimal.valueOf(100.25))),
		
		// NOTHING GETS SETTLED ON SATURDAY
		// ALL THESE SHOULD BE SETTLED ON 02-12-2018 - SUNDAY
		new Trade("Enity1", TradeAction.BUY, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 11, 30), new TradeDetails(
                Currency.getInstance("SAR"), BigDecimal.valueOf(0.22), 350, BigDecimal.valueOf(150.5))),
		
		new Trade("Enity2", TradeAction.SELL, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 1), new TradeDetails(
                Currency.getInstance("SAR"), BigDecimal.valueOf(0.22), 450, BigDecimal.valueOf(150.5))),
		
		new Trade("Enity3", TradeAction.BUY, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 1), new TradeDetails(
                Currency.getInstance("AED"), BigDecimal.valueOf(0.20), 550, BigDecimal.valueOf(50.5))),
		
		
		// ALL THESE SHOULD BE SETTLED ON 03-12-2018
		new Trade("Enity1", TradeAction.SELL, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 2),
                new TradeDetails(Currency.getInstance("SGD"), BigDecimal.valueOf(0.34), 50, BigDecimal.valueOf(500.6))),
		
		new Trade("Enity2", TradeAction.SELL, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 3),
	                new TradeDetails(Currency.getInstance("AED"), BigDecimal.valueOf(0.34), 50, BigDecimal.valueOf(500.6))),

		new Trade("Enity3", TradeAction.SELL, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 2), new TradeDetails(
				Currency.getInstance("GBP"), BigDecimal.valueOf(0.50), 1000, BigDecimal.valueOf(160.6))),

		new Trade("Enity4", TradeAction.SELL, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 1), new TradeDetails(
				Currency.getInstance("GBP"), BigDecimal.valueOf(0.50), 120, BigDecimal.valueOf(500.6))),
		
		new Trade("Enity1", TradeAction.BUY, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 2),
                new TradeDetails(Currency.getInstance("SGD"), BigDecimal.valueOf(0.34), 50, BigDecimal.valueOf(500.6))),
        
        new Trade("Enity2", TradeAction.BUY, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 3),
                    new TradeDetails(Currency.getInstance("AED"), BigDecimal.valueOf(0.34), 50, BigDecimal.valueOf(500.6))),

        new Trade("Enity3", TradeAction.BUY, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 2), new TradeDetails(
                Currency.getInstance("GBP"), BigDecimal.valueOf(0.50), 1000, BigDecimal.valueOf(160.6))),

        new Trade("Enity4", TradeAction.BUY, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 1), new TradeDetails(
                Currency.getInstance("GBP"), BigDecimal.valueOf(0.50), 120, BigDecimal.valueOf(500.6))));
	}
}
