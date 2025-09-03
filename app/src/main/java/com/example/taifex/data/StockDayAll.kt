package com.example.taifex.data

import kotlinx.serialization.Serializable

/**
 * @param Code 股票代號
 * @param Name 股票名稱
 * @param TradeVolume 成交股數
 * @param TradeValue 成交金額
 * @param OpeningPrice 開盤價
 * @param HighestPrice 最高價
 * @param LowestPrice 最低價
 * @param ClosingPrice 收盤
 * @param Change 漲跌價差
 * @param Transaction 成交筆數
 * */
@Serializable
class StockDayAll(
    val Code: String,
    val Name: String,
    val TradeVolume: String,
    val TradeValue: String,
    val OpeningPrice: String,
    val HighestPrice: String,
    val LowestPrice: String,
    val ClosingPrice: String,
    val Change: String,
    val Transaction: String
)
