package com.example.taifex

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taifex.data.Taifex

class TaifexAdapter(private var taifex: List<Taifex>, private val onItemClick: (Taifex) -> Unit)
    : RecyclerView.Adapter<TaifexAdapter.TaifexViewHolder>()
{

    class TaifexViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val textCode: TextView = itemView.findViewById(R.id.code)
        val textName: TextView = itemView.findViewById(R.id.name)
        val textOpen: TextView = itemView.findViewById(R.id.open)
        val textClose: TextView = itemView.findViewById(R.id.close)
        val textHigh: TextView = itemView.findViewById(R.id.high)
        val textLow: TextView = itemView.findViewById(R.id.low)
        val textChange: TextView = itemView.findViewById(R.id.change)
        val textAvg: TextView = itemView.findViewById(R.id.avg)
        val textTransaction: TextView = itemView.findViewById(R.id.transaction)
        val textTradeVolume: TextView = itemView.findViewById(R.id.trade_volume)
        val textTradeValue: TextView = itemView.findViewById(R.id.trade_value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaifexViewHolder
    {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_taifex, parent, false)
        return TaifexViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaifexViewHolder, position: Int)
    {
        val item = taifex[position]
        holder.textCode.text = item.code
        holder.textName.text = item.name
        holder.textOpen.text = item.stockDayAll?.OpeningPrice
        holder.textClose.text = item.stockDayAll?.ClosingPrice
        holder.textHigh.text = item.stockDayAll?.HighestPrice
        holder.textLow.text = item.stockDayAll?.LowestPrice
        holder.textChange.text = item.stockDayAll?.Change
        holder.textAvg.text = item.stockDayAvgAll?.MonthlyAveragePrice

        if (item.stockDayAll?.Transaction != null)
        {
            holder.textTransaction.text = formatNumber(item.stockDayAll!!.Transaction.toLong())
        }
        if (item.stockDayAll?.TradeVolume != null)
        {
            holder.textTradeVolume.text = formatNumber(item.stockDayAll!!.TradeVolume.toLong())
        }
        if (item.stockDayAll?.TradeValue != null)
        {
            holder.textTradeValue.text = formatNumber(item.stockDayAll!!.TradeValue.toLong())
        }

        if (item.stockDayAll?.ClosingPrice != null && item.stockDayAvgAll?.MonthlyAveragePrice != null)
        {
            val change = item.stockDayAll!!.ClosingPrice.toFloat() - item.stockDayAvgAll!!.MonthlyAveragePrice.toFloat()
            if (change > 0)
            {
                holder.textClose.setTextColor(Color.RED)
            }
            else if (change < 0)
            {
                holder.textClose.setTextColor(Color.GREEN)
            }
        }

        if (item.stockDayAll?.Change != null)
        {
            val change = item.stockDayAll!!.Change.toFloat()
            if (change > 0)
            {
                holder.textChange.setTextColor(Color.RED)
            }
            else if (change < 0)
            {
                holder.textChange.setTextColor(Color.GREEN)
            }
        }

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = taifex.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newTaifex: List<Taifex>)
    {
        taifex = newTaifex
        notifyDataSetChanged()
    }

    @SuppressLint("DefaultLocale")
    fun formatNumber(value: Long): String
    {
        return when
        {
            value >= 1_000_000_000 -> String.format("%.1fB", value / 1_000_000_000.0)
            value >= 1_000_000 -> String.format("%.1fM", value / 1_000_000.0)
            value >= 1_000 -> String.format("%.1fK", value / 1_000.0)
            else -> value.toString()
        }
    }
}