package com.jpmorgan.trade.report.validators;

import java.math.BigDecimal;

import com.jpmorgan.trade.report.constants.Constant;
import com.jpmorgan.trade.report.entities.Trade;
import com.jpmorgan.trade.report.exception.ReportException;

/**
 * Validate the incoming input
 */
public class DefaultValidator {

	public void validate(Trade trade) throws ReportException {
		if (trade == null) {
			throw new ReportException(Constant.TRADE_NOT_SENT);
		} else {
			// since we need all the data to generate the report hence check for all fields to be not null.
			if (trade.getDetails() == null) {
				throw new ReportException(Constant.TRADE_DETAILS_MISSING);
			} else if (trade.getAction() == null) {
				throw new ReportException(Constant.ACTION_MISSING);
			} else if (trade.getAgreedFx() == BigDecimal.ZERO) {
				throw new ReportException(Constant.AGREED_FX_MISSING);
			} else if (trade.getCurrency() == null) {
				throw new ReportException(Constant.CURRENCY_MISSING);
			} else if (trade.getEntity() == null) {
				throw new ReportException(Constant.ENTITY_NAME_MISSING);
			} else if (trade.getInstructionDate() == null) {
				throw new ReportException(Constant.INSTRUCTION_DATE_MISSING);
			} else if (trade.getSettlementDate() == null) {
				throw new ReportException(Constant.SETTLEMENT_DATE_MISSING);
			} else if (trade.getTradeAmount() == null) {
				throw new ReportException(Constant.TRADE_AMOUNT_MISSING);
			} else if (trade.getPricePerUnit() == BigDecimal.ZERO) {
				throw new ReportException(Constant.PRICE_MISSING);
			} else if (trade.getUnits() == 0) {
				throw new ReportException(Constant.UNITS_MISSING);
			}
			// check if instruction date is before settlement date
			if (trade.getInstructionDate().isAfter(trade.getSettlementDate())) {
				throw new ReportException(Constant.INSTRUCTION_GREATER_THAN_SETTLEMENT);
			}
		}
	}

}
