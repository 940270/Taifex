package com.example.taifex.data

import android.content.Context
import com.example.taifex.R
import kotlinx.serialization.json.Json

class TaifexRepository(private val context: Context)
{
    fun parseBwibbuAllList(): List<BwibbuAll>
    {
        val json = context.resources.openRawResource(R.raw.bwibbu_all).bufferedReader().use { it.readText() }
        return Json.decodeFromString(json)
    }

    fun parseStockDayAllList(): List<StockDayAll>
    {
        val json = context.resources.openRawResource(R.raw.stock_day_all).bufferedReader().use { it.readText() }
        return Json.decodeFromString(json)
    }

    fun parseStockDayAvgAllList(): List<StockDayAvgAll>
    {
        val json = context.resources.openRawResource(R.raw.stock_day_avg_all).bufferedReader().use { it.readText() }
        return Json.decodeFromString(json)
    }

    fun mergeDataClasses(
        bwibbuAll: List<BwibbuAll>,
        stockDayAll: List<StockDayAll>,
        stockDayAvgAll: List<StockDayAvgAll>
    ): List<Taifex>
    {
        val bwibbuAllMap = bwibbuAll.associateBy { it.Code }
        val stockDayAllMap = stockDayAll.associateBy { it.Code }
        val stockDayAvgAllMap = stockDayAvgAll.associateBy { it.Code }

        return bwibbuAllMap.keys.mapNotNull { id ->
            val code = bwibbuAllMap[id]?.Code
            val name = bwibbuAllMap[id]?.Name

            if (code != null && name != null)
            {
                Taifex(code, name).let { it ->
                    it.bwibbuAll = bwibbuAllMap[id]
                    it.stockDayAll = stockDayAllMap[id]
                    it.stockDayAvgAll = stockDayAvgAllMap[id]
                    it
                }
            }
            else null
        }
    }
}