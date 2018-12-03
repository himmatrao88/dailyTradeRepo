package com.jpmorgan.trade.report.entities;

public enum TradeAction {
	BUY("B"), SELL("S");

	private String action;

	TradeAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return this.action;
	}

	public static TradeAction fromString(String action) {

		if (action != null) {
			for (TradeAction ta : TradeAction.values()) {
				if (action.equalsIgnoreCase(ta.action)) {
					return ta;
				}
			}

			throw new IllegalArgumentException("No enumeration constant with action " + action + " found!");
		} else {
			throw new NullPointerException("Null value supplied.");
		}
	}
}
