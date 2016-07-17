package com.deancrabtree.supersimplestocks;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Stock
{
    private String theSymbol;
    private StockType theType;
    private BigDecimal theLastDividend;
    private double theFixedDividend;
    private BigDecimal theParValue;
    private BigDecimal thePrice;
    private LinkedList<Trade> theTradesLog = new LinkedList<Trade>();

    public Stock()
    {
    }

    public Stock( String pSymbol, StockType pType, BigDecimal pLastDividend, double pFixedDividend, BigDecimal pParValue )
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

    public BigDecimal getLastDividend()
    {
        return theLastDividend;
    }

    public double getFixedDividend()
    {
        return theFixedDividend;
    }

    public BigDecimal getParValue()
    {
        return theParValue;
    }

    public BigDecimal getPrice()
    {
        return thePrice;
    }

    public LinkedList<Trade> getTradesLog()
    {
        return theTradesLog;
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
    public void setLastDividend( BigDecimal pLastDividend )
    {
        theLastDividend = pLastDividend;
    }
    public void setFixedDividend( double pFixedDividend )
    {
        theFixedDividend = pFixedDividend;
    }
    public void setParValue( BigDecimal pParValue )
    {
        theParValue = pParValue;
    }

    public BigDecimal calculateDividendYield( BigDecimal pTickerPrice )
    {
        if( theType == StockType.COMMON )
        {
            BigDecimal lDividendYield = theLastDividend.divide( pTickerPrice, 3, RoundingMode.HALF_UP);
            return lDividendYield;
        }
        else if( theType == StockType.PREFERRED )
        {
            return ( theParValue.multiply( new BigDecimal( theFixedDividend ))).divide( pTickerPrice, 3, RoundingMode.HALF_UP );
        }
        return new BigDecimal( 0 );
    }

    public BigDecimal calculatePERatio( BigDecimal pTickerPrice )
    {
        if( theLastDividend.compareTo( BigDecimal.ZERO ) != 0)
        {
            return pTickerPrice.divide( theLastDividend, 3, RoundingMode.HALF_UP);
        }
        else
        {
            return new BigDecimal( 0 );
        }
    }

    public void buy( int pQuantity, BigDecimal pPrice )
    {
        Trade lTrade = new Trade( new Date(), pQuantity, TradeType.BUY, pPrice );
        theTradesLog.add( lTrade );
    }

    public void sell( int pQuantity, BigDecimal pPrice )
    {
        Trade lTrade = new Trade( new Date(), pQuantity, TradeType.SELL, pPrice );
        theTradesLog.add( lTrade );
    }

    public BigDecimal calculateStockPrice()
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

        BigDecimal lSumPricesAndQuantities = new BigDecimal( 0 );
        int lSumQuantities = 0;
        for( Trade lTrade : lRelevantTrades )
        {
            lSumPricesAndQuantities = lSumPricesAndQuantities.add( lTrade.getPrice().multiply( new BigDecimal( lTrade.getQuantity() )) );
            lSumQuantities += lTrade.getQuantity();
        }

        thePrice = lSumPricesAndQuantities.divide( new BigDecimal( lSumQuantities ), 3, RoundingMode.HALF_UP );
        return thePrice;
    }
}
