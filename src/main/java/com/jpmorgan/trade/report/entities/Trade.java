package com.jpmorgan.trade.report.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

/**
 * Class to store trades information
 */
public class Trade {

    private final String entity;

    private final TradeAction action;

    private final LocalDate instructionDate;

    private final LocalDate settlementDate;
    
    private LocalDate effectiveSettlementDate;
    
    private final TradeDetails details;

    public Trade(
            String entity,
            TradeAction action,
            LocalDate instructionDate,
            LocalDate settlementDate,
            TradeDetails details)
    {
        this.entity = entity;
        this.action = action;
        this.instructionDate = instructionDate;
        this.settlementDate = settlementDate;
        this.details = details;
    }

    public String getEntity() {
        return entity;
    }
    
    public TradeAction getAction() {
        return action;
    }

    public LocalDate getInstructionDate() {
        return instructionDate;
    }

    public LocalDate getSettlementDate() {
        return settlementDate;
    }
    
    public LocalDate getEffectiveSettlementDate() {
        return effectiveSettlementDate;
    }
    
    public void setEffectiveSettlementDate(final LocalDate effectiveSettlementDate) {
        this.effectiveSettlementDate = effectiveSettlementDate;
    }

    public TradeDetails getDetails() {
        return details;
    }

    public Currency getCurrency() {
        return getDetails().getCurrency();
    }

    public BigDecimal getAgreedFx() {
        return getDetails().getAgreedFx();
    }

    public int getUnits() {
        return getDetails().getUnits();
    }

    public BigDecimal getPricePerUnit() {
        return getDetails().getPricePerUnit();
    }

    public BigDecimal getTradeAmount() {
        return getDetails().getTradeAmount()
                .setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    @Override
    public String toString() {
        return entity;
    }
}
