package com.deancrabtree.javatest;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeUnit.*;

public class Stock
{
    private String theSymbol;
    private StockType theType;
    private int theLastDividend;
    private int theFixedDividend;
    private int theParValue;
    private int theTickerPrice;
    private LinkedList<Trade> theTradesLog = new LinkedList<Trade>();

    public Stock()
    {
    }

    public Stock( String pSymbol, StockType pType, int pLastDividend, int pFixedDividend, int pParValue )
    {
        theSymbol = pSymbol;
        theType = pType;
        theLastDividend = pLastDividend;
        theFixedDividend = pFixedDividend;
        theParValue = pParValue;
    }

    //Getters
    public String getSymbol()
    {
        return theSymbol;
    }

    public StockType getType()
    {
        return theType;
    }

    public int getLastDividend()
    {
        return theLastDividend;
    }

    public int getFixedDividend()
    {
        return theFixedDividend;
    }

    public int getParValue()
    {
        return theParValue;
    }

    //Setters
    public void setSymbol( String pSymbol )
    {
        theSymbol = pSymbol;
    }
    public void setType( StockType pType )
    {
        theType = pType;
    }
    public void setLastDividend( int pLastDividend )
    {
        theLastDividend = pLastDividend;
    }
    public void setFixedDividend( int pFixedDividend )
    {
        theFixedDividend = pFixedDividend;
    }
    public void setParValue( int pParValue )
    {
        theParValue = pParValue;
    }

    public float calculateDividendYield(float pTickerPrice )
    {
        if( theType == StockType.COMMON )
        {
            return theLastDividend/pTickerPrice;
        }
        else if( theType == StockType.PREFERRED )
        {
            return ( theFixedDividend*theParValue ) / pTickerPrice;
        }

        return 0;
    }

    public float calculatePERatio( float pTickerPrice )
    {
        return pTickerPrice/theLastDividend;
    }

    public void buy( int pQuantity, float pPrice )
    {
        Trade lTrade = new Trade( new Date(), pQuantity, TradeType.BUY, pPrice );
        theTradesLog.add( lTrade );
    }

    public void sell( int pQuantity, float pPrice )
    {
        Trade lTrade = new Trade( new Date(), pQuantity, TradeType.SELL, pPrice );
        theTradesLog.add( lTrade );
    }

    public float calculateStockPrice()
    {
        //Get relevant trade listings
        LinkedList<Trade> lRelevantTrades = new LinkedList<>();
        long lMaxTimeDifference = TimeUnit.MILLISECONDS.convert( 15, TimeUnit.MINUTES );
        for( Trade lTrade : theTradesLog )
        {
            if( new Date().getTime() - lTrade.getTimestamp().getTime() < lMaxTimeDifference )
            {
                lRelevantTrades.add( lTrade );
            }
        }

        float lSumPricesAndQuantities = 0f;
        int lSumQuantities = 0;
        for( Trade lTrade : lRelevantTrades )
        {
            lSumPricesAndQuantities += lTrade.getPrice() * lTrade.getQuantity();
            lSumQuantities += lTrade.getQuantity();
        }

        return lSumPricesAndQuantities / lSumQuantities;
    }
}
