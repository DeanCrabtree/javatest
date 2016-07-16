package com.deancrabtree.javatest;

import java.util.Date;

public class Trade
{
    private Date theTimestamp;
    private int theQuantity;
    private TradeType theType;
    private float thePrice;

    protected Trade( Date pTimeStamp, int pQuantity, TradeType pType, float pPrice )
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

    protected float getPrice()
    {
        return thePrice;
    }

}
