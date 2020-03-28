package com.jpmorgan.trade.report.starter;

import java.util.List;

import com.jpmorgan.trade.report.IReportGenerator;
import com.jpmorgan.trade.report.ReportGenerator;
import com.jpmorgan.trade.report.entities.Trade;
import com.jpmorgan.trade.report.exception.ReportException;

public class Starter {

	public static void main(String[] args) throws ReportException {
		final List<Trade> instructions = TradeGenerator.getTradeInstructions();
		final IReportGenerator reportGenerator = new ReportGenerator();

		System.out.println("TEST GIT HIMMAT");

		System.out.println(reportGenerator.generateInstructionsReport(instructions));
	}
}
