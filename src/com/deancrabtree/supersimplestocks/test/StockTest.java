package com.deancrabtree.supersimplestocks.test;

import com.deancrabtree.supersimplestocks.Stock;
import com.deancrabtree.supersimplestocks.StockType;
import com.deancrabtree.supersimplestocks.Trade;
import com.deancrabtree.supersimplestocks.TradeType;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

public class StockTest
{
    @Test
    public void calculateDividendYield() throws Exception
    {
        Stock lStockJOE = new Stock("JOE", StockType.COMMON, new BigDecimal( 13 ), 0, new BigDecimal( 250 ));
        Stock lStockGIN = new Stock("GIN", StockType.PREFERRED, new BigDecimal( 8 ), 2, new BigDecimal( 100 ));

        BigDecimal lJoeDividend = lStockJOE.calculateDividendYield( BigDecimal.TEN );
        BigDecimal lExpectedJoeDividend = lStockJOE.getLastDividend().divide(BigDecimal.TEN, 3, RoundingMode.HALF_UP );
        assertEquals( lJoeDividend, lExpectedJoeDividend);

        BigDecimal lGinDividend = lStockGIN.calculateDividendYield( BigDecimal.TEN );
        BigDecimal lExpectedGinDividend = ( lStockGIN.getParValue().multiply( new BigDecimal( lStockGIN.getFixedDividend()))).divide( BigDecimal.TEN, 3, RoundingMode.HALF_UP );
        assertEquals( lGinDividend, lExpectedGinDividend );
    }

    @Test
    public void calculatePERatio() throws Exception
    {
        Stock lStockJOE = new Stock("JOE", StockType.COMMON, new BigDecimal( 13 ), 0, new BigDecimal( 250 ));
        BigDecimal lJoePERatio = lStockJOE.calculatePERatio( BigDecimal.TEN );
        BigDecimal lExpectedJoePERatio = BigDecimal.TEN.divide(lStockJOE.getLastDividend(),3, RoundingMode.HALF_UP );
        assertEquals( lJoePERatio, lExpectedJoePERatio );
    }

    @Test
    public void buy() throws Exception
    {
        Stock lStockJOE = new Stock("JOE", StockType.COMMON, new BigDecimal( 13 ), 0, new BigDecimal( 250 ));
        lStockJOE.buy(100, BigDecimal.TEN );

        Trade lTrade = lStockJOE.getTradesLog().getFirst();
        assertEquals( lTrade.getPrice(), BigDecimal.TEN );
        assertEquals( lTrade.getQuantity(), 100 );
        assertEquals( lTrade.getType(), TradeType.BUY );
    }

    @Test
    public void sell() throws Exception
    {
        Stock lStockJOE = new Stock("JOE", StockType.COMMON, new BigDecimal( 13 ), 0, new BigDecimal( 250 ));
        lStockJOE.sell(100, BigDecimal.TEN );

        Trade lTrade = lStockJOE.getTradesLog().getFirst();
        assertEquals( lTrade.getPrice(), BigDecimal.TEN );
        assertEquals( lTrade.getQuantity(), 100 );
        assertEquals( lTrade.getType(), TradeType.SELL );
    }

    @Test
    public void calculateStockPrice() throws Exception
    {
        Stock lStockJOE = new Stock("JOE", StockType.COMMON, new BigDecimal( 13 ), 0, new BigDecimal( 250 ));

        lStockJOE.sell(100, BigDecimal.TEN );
        lStockJOE.buy(50, BigDecimal.ONE );

        BigDecimal lExpectedStockPrice = (BigDecimal.TEN.multiply( new BigDecimal( 100 ) )).add(BigDecimal.ONE.multiply( new BigDecimal( 50 ))).divide( new BigDecimal(150), 3, RoundingMode.HALF_UP);
        BigDecimal lStockPrice = lStockJOE.calculateStockPrice();
        System.out.println(lStockPrice);
        assertEquals( lExpectedStockPrice, lStockPrice );

    }

}