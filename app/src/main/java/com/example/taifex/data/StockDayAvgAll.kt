package com.example.taifex.data

import kotlinx.serialization.Serializable

/**
 * @param Code 股票代號
 * @param Name 股票名稱
 * @param ClosingPrice 收盤價
 * @param MonthlyAveragePrice 月平均價
 * */
@Serializable
class StockDayAvgAll(
    val Code: String,
    val Name: String,
    val ClosingPrice: String,
    val MonthlyAveragePrice: String
)