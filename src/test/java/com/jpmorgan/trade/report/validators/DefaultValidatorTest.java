package com.jpmorgan.trade.report.validators;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.trade.report.entities.Trade;
import com.jpmorgan.trade.report.entities.TradeAction;
import com.jpmorgan.trade.report.entities.TradeDetails;
import com.jpmorgan.trade.report.exception.ReportException;
import com.jpmorgan.trade.report.validators.DefaultValidator;

public class DefaultValidatorTest {

	private DefaultValidator validator;
	private Trade trade;

	@Before
	public void setup() {
		validator = new DefaultValidator();
	}

	@Test(expected = ReportException.class)
	public void testValidateTradeDetailsMissing() throws ReportException {
		trade = new Trade("E1", TradeAction.BUY, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 2), null);
		validator.validate(trade);
	}

	@Test(expected = ReportException.class)
	public void testValidateEntityMissing() throws ReportException {
		trade = new Trade(null, TradeAction.BUY, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 2),
				new TradeDetails(Currency.getInstance("GBP"), BigDecimal.TEN, 80, BigDecimal.TEN));
		validator.validate(trade);
	}

	@Test(expected = ReportException.class)
	public void testValidateInstructionDateMissing() throws ReportException {
		trade = new Trade("E1", TradeAction.BUY, null, LocalDate.of(2018, 12, 2),
				new TradeDetails(Currency.getInstance("GBP"), BigDecimal.TEN, 80, BigDecimal.TEN));
		validator.validate(trade);
	}

	@Test(expected = ReportException.class)
	public void testValidateSettlementDateMissing() throws ReportException {
		trade = new Trade("E1", TradeAction.BUY, LocalDate.of(2018, 11, 30), null,
				new TradeDetails(Currency.getInstance("GBP"), BigDecimal.TEN, 80, BigDecimal.TEN));
		validator.validate(trade);
	}

	@Test(expected = ReportException.class)
	public void testValidateCurrencyMissing() throws ReportException {
		trade = new Trade("E1", TradeAction.BUY, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 2),
				new TradeDetails(null, BigDecimal.TEN, 80, BigDecimal.TEN));
		validator.validate(trade);
	}

	@Test(expected = ReportException.class)
	public void testValidateAgreedFxMissing() throws ReportException {
		trade = new Trade("E1", TradeAction.BUY, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 2),
				new TradeDetails(Currency.getInstance("GBP"), BigDecimal.ZERO, 80, BigDecimal.TEN));
		validator.validate(trade);
	}

	@Test(expected = ReportException.class)
	public void testValidatePricePerUnitMissing() throws ReportException {
		trade = new Trade("E1", TradeAction.BUY, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 2),
				new TradeDetails(Currency.getInstance("GBP"), BigDecimal.TEN, 80, BigDecimal.ZERO));
		validator.validate(trade);
	}

	@Test(expected = ReportException.class)
	public void testValidateUnitsMissing() throws ReportException {
		trade = new Trade("E1", TradeAction.BUY, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 2),
				new TradeDetails(Currency.getInstance("GBP"), BigDecimal.TEN, 0, BigDecimal.TEN));
		validator.validate(trade);
	}

	@Test(expected = ReportException.class)
	public void testValidateActionMissing() throws ReportException {
		trade = new Trade("E1", null, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 2),
				new TradeDetails(Currency.getInstance("GBP"), BigDecimal.TEN, 80, BigDecimal.TEN));
		validator.validate(trade);
	}
	
	@Test
	public void testValidateNoException() throws ReportException {
		trade = new Trade("E1", TradeAction.BUY, LocalDate.of(2018, 11, 30), LocalDate.of(2018, 12, 2),
				new TradeDetails(Currency.getInstance("GBP"), BigDecimal.TEN, 80, BigDecimal.TEN));
		validator.validate(trade);
	}

}
