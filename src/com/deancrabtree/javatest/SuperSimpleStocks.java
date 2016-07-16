package com.deancrabtree.javatest;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class SuperSimpleStocks
{
    private static HashMap<String, Stock> theStocks = new HashMap<String, Stock>();

    public static void main( String[] args ) throws InterruptedException {
        theStocks.put("TEA", new Stock("TEA", StockType.COMMON, 0, 0, 100));
        theStocks.put("POP", new Stock("POP", StockType.COMMON, 8, 0, 100));
        theStocks.put("ALE", new Stock("ALE", StockType.COMMON, 23, 0, 60));
        theStocks.put("GIN", new Stock("GIN", StockType.PREFERRED, 8, 2, 100));
        theStocks.put("JOE", new Stock("JOE", StockType.COMMON, 13, 0, 250));

        boolean isVerified = false;
        Scanner lInput = new Scanner( System.in );
        String lStockSymbol;
        Stock lStock = new Stock();
        float lDividendYield;
        float lPERatio;
        System.out.println( "Choose a stock: TEA, POP, ALE, GIN, JOE: " );

        while( !isVerified )
        {
            lStockSymbol = lInput.nextLine();
            lStock = theStocks.get( lStockSymbol );

            if ( lStock != null )
            {
                System.out.println("Stock chosen: " + lStockSymbol);
                isVerified = true;
            }
            else
            {
                System.out.println("Unsupported, try again: ");
            }
        }

        float lMin = 0.1f;
        float lMax = 100.0f;

        DecimalFormat lDecimalFormat = new DecimalFormat("#.##");
        lDecimalFormat.setRoundingMode(RoundingMode.CEILING);

        Random lRand = new Random();

        float lTickerPrice = lRand.nextFloat() * (lMax - lMin) + lMin;
        lTickerPrice = Float.valueOf( lDecimalFormat.format( lTickerPrice ));

        //Calculate dividend yield for given stock
        lDividendYield = lStock.calculateDividendYield( lTickerPrice );
        System.out.println( "The Dividend Yield for " + lStock.getSymbol() + " at ticker price " + lTickerPrice + " equals: " + lDividendYield );

        //Calculate P/E ratio for given stock
        lPERatio = lStock.calculatePERatio( lTickerPrice );
        System.out.println( "The P/E Ratio for " + lStock.getSymbol() + " at ticker price " + lTickerPrice + " equals: " + lPERatio );


        lStock.buy( 100, lTickerPrice );
        Thread.sleep(1000);
        lStock.sell( 50, lTickerPrice * 2 );
        System.out.println( "The Stock Price for " + lStock.getSymbol() + " equals: " + lStock.calculateStockPrice() );

    }
}
