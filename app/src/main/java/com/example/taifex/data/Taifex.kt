package com.example.taifex.data

/**
 * @param code 股票代號
 * @param name 股票名稱
 * */
data class Taifex(val code: String, val name: String)
{
    var bwibbuAll: BwibbuAll? = null
    var stockDayAll: StockDayAll? = null
    var stockDayAvgAll: StockDayAvgAll? = null
}
