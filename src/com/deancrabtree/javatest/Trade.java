package com.deancrabtree.javatest;

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
    protected Date getTimestamp()
    {
        return theTimestamp;
    }

    protected int getQuantity()
    {
        return theQuantity;
    }

    protected TradeType getType()
    {
        return theType;
    }

    protected BigDecimal getPrice()
    {
        return thePrice;
    }

}
