package com.deancrabtree.javatest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class SuperSimpleStocks
{
    private static HashMap<String, Stock> theStocks = new HashMap<String, Stock>();

    public static void main( String[] args ) throws InterruptedException
    {
        theStocks.put("TEA", new Stock("TEA", StockType.COMMON, new BigDecimal( 0 ), 0, new BigDecimal( 100 )));
        theStocks.put("POP", new Stock("POP", StockType.COMMON, new BigDecimal( 8 ), 0, new BigDecimal( 100 )));
        theStocks.put("ALE", new Stock("ALE", StockType.COMMON, new BigDecimal( 23 ), 0, new BigDecimal( 60 )));
        theStocks.put("GIN", new Stock("GIN", StockType.PREFERRED, new BigDecimal( 8 ), 2, new BigDecimal( 100 )));
        theStocks.put("JOE", new Stock("JOE", StockType.COMMON, new BigDecimal( 13 ), 0, new BigDecimal( 250 )));

        BigDecimal lDividendYield;
        BigDecimal lPERatio;
        Stock lStock;
        System.out.println( "Choose a stock: TEA, POP, ALE, GIN, JOE: " );

        for( Map.Entry<String, Stock> lStockEntry : theStocks.entrySet() )
        {
            lStock = lStockEntry.getValue();

            System.out.println("*****For Stock " + lStockEntry.getKey() + "*****");
            Random lRand = new Random();

            //Ticker price in pennies; random value up to £5
            BigDecimal lTickerPrice = new BigDecimal(lRand.nextInt(500));

            //Calculate dividend yield for given stock
            lDividendYield = lStock.calculateDividendYield(lTickerPrice);
            System.out.println("The Dividend Yield for " + lStock.getSymbol() + " at ticker price " + lTickerPrice + "p equals: " + lDividendYield);

            //Calculate P/E ratio for given stock
            lPERatio = lStock.calculatePERatio(lTickerPrice);
            System.out.println("The P/E Ratio for " + lStock.getSymbol() + " at ticker price " + lTickerPrice + "p equals: " + lPERatio);


            //Perform and record trades
            lStock.buy(100, lTickerPrice);
            Thread.sleep(1000); //If this sleep is set to greater than 15 minutes, the buy trade will not be included in price calculation
            lStock.sell(50, lTickerPrice.multiply(new BigDecimal(2)));

            //Calculate and display Stock Price
            System.out.println("The Stock Price for " + lStock.getSymbol() + " equals: " + lStock.calculateStockPrice() + "p");
        }

        System.out.println( "The GBCE All Share Index is: " + calculateGBCE() );
    }

    private static double calculateGBCE( )
    {
        BigDecimal lStockPrices[] = new BigDecimal[5];
        Stock lStock;
        int i = 0;
        for( Map.Entry<String, Stock> lStockEntry : theStocks.entrySet() )
        {
            lStock = lStockEntry.getValue();
            lStockPrices[i] = lStock.getPrice();
        }

        BigDecimal lSum = lStockPrices[0];

        for( int j = 1; j < lStockPrices.length; j++ )
        {
            lSum = lSum.multiply( lStockPrices[i] );
        }
        return Math.pow( lSum.doubleValue(), 1.0 / lStockPrices.length);
    }
}
