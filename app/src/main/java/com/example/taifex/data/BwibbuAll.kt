package com.example.taifex.data

import kotlinx.serialization.Serializable

/**
 * @param Code 股票代號
 * @param Name 股票名稱
 * @param PEratio 本益比
 * @param DividendYield 殖利率(%)
 * @param PBratio 股票名稱
 * */
@Serializable
data class BwibbuAll(
    val Code: String,
    val Name: String,
    val PEratio: String,
    val DividendYield: String,
    val PBratio: String
)