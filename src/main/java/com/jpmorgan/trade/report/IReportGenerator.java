package com.jpmorgan.trade.report;

import java.util.List;

import com.jpmorgan.trade.report.entities.Trade;
import com.jpmorgan.trade.report.exception.ReportException;

/**
 * The Interface IReportGenerator.
 */
public interface IReportGenerator {
    
    /**
     * Generate instructions report.
     *
     * @param instructions the instructions
     * @return the string
     * @throws ReportException the report exception
     */
    String generateInstructionsReport(List<Trade> instructions) throws ReportException;
}
