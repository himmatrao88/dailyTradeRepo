package com.jpmorgan.trade.report.calculators;

import java.time.LocalDate;
import java.util.Currency;

import com.jpmorgan.trade.report.entities.Trade;
import com.jpmorgan.trade.report.workingdays.ArabicWorkingDays;
import com.jpmorgan.trade.report.workingdays.DefaultWorkingDays;
import com.jpmorgan.trade.report.workingdays.IWorkingDays;

/**
 * A settlement date calculator
 */
public class DateCalculator {

    /**
     * Calculate the settlementDate Based on some logic
     * @param instruction the instruction of which the settlement date will be calculated
     */
    public static LocalDate calculateSettlementDate(Trade instruction) {
        // If effectiveSettlementDate has already been calculated and set
        // We do not need to calculate it again
        if(instruction.getEffectiveSettlementDate() != null) {
            return instruction.getEffectiveSettlementDate();
        }
        
        //Going for calculating effectiveSettlementDate.
        // Select proper strategy based on the Currency
        final IWorkingDays workingDaysMechanism = getWorkingDaysStrategy(instruction.getCurrency());

        // find the correct settlement date
        final LocalDate newSettlementDate =
                workingDaysMechanism.findFirstWorkingDate(instruction.getSettlementDate());
        
        // Setting effective Settlement Date for future use.
        if(newSettlementDate != null) {
            instruction.setEffectiveSettlementDate(newSettlementDate);
        }

        return newSettlementDate;
    }

    /**
     * Select proper strategy based on the Currency
     * @param currency the currency to choose
     * @return the proper working days strategy
     */
    private static IWorkingDays getWorkingDaysStrategy(Currency currency) {
        if ((currency.getCurrencyCode().equals("AED")) ||
            (currency.getCurrencyCode().equals("SAR")))
        {
            return ArabicWorkingDays.getInstance();
        }
        return DefaultWorkingDays.getInstance();
    }
}
