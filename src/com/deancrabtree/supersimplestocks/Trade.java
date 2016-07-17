package com.deancrabtree.supersimplestocks;

import java.math.BigDecimal;
import java.util.Date;

public class Trade
{
    private Date theTimestamp;
    private int theQuantity;
    private TradeType theType;
    private BigDecimal thePrice;

    protected Trade( Date pTimeStamp, int pQuantity, TradeType pType, BigDecimal pPrice )
    {
        theTimestamp = pTimeStamp;
        theQuantity = pQuantity;
        theType =  pType;
        thePrice = pPrice;
    }

    //Getters
    public Date getTimestamp()
    {
        return theTimestamp;
    }

    public int getQuantity()
    {
        return theQuantity;
    }

    public TradeType getType()
    {
        return theType;
    }

    public BigDecimal getPrice()
    {
        return thePrice;
    }

}
