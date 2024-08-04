/**
 * This package contains an implementation of a stock-application (main server).
 */
module stock.application {
    requires stocks.main;
    requires messagequeue;
    requires networking;
    requires static lombok;
    requires org.slf4j;
    requires util;
    requires command;
    requires stock.market.ui;
    requires awaitility;
    requires org.testng;
}