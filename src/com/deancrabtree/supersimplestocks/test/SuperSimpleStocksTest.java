package com.deancrabtree.supersimplestocks.test;

import com.deancrabtree.supersimplestocks.Stock;
import com.deancrabtree.supersimplestocks.StockType;
import com.deancrabtree.supersimplestocks.SuperSimpleStocks;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;


public class SuperSimpleStocksTest
{
    @Test
    public void calculateGBCE()
    {
        SuperSimpleStocks.theStocks.put("JOE", new Stock("JOE", StockType.COMMON, new BigDecimal( 13 ), 0, new BigDecimal( 250 )));
        Stock lStock = SuperSimpleStocks.theStocks.get("JOE");
        lStock.buy(100, BigDecimal.TEN);
        lStock.sell(50, BigDecimal.ONE);
        lStock.calculateStockPrice();

        SuperSimpleStocks.theStocks.put("POP", new Stock("POP", StockType.COMMON, new BigDecimal( 8 ), 0, new BigDecimal( 100 )));
        Stock lStock2 = SuperSimpleStocks.theStocks.get("POP");
        lStock2.sell(50, BigDecimal.ONE);
        lStock2.calculateStockPrice();

        BigDecimal lSum = lStock.getPrice().multiply( lStock2.getPrice());
        double lExpectedGBCE = Math.pow( lSum.doubleValue(), 1.0/2.0);
        double lGBCE = SuperSimpleStocks.calculateGBCE();

        assertEquals( lExpectedGBCE, lGBCE, 0.01);
    }

}