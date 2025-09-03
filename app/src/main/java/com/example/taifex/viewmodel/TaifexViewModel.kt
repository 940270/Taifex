package com.example.taifex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taifex.data.Taifex
import com.example.taifex.data.TaifexRepository

class TaifexViewModel(private val repository: TaifexRepository) : ViewModel()
{
    private val _taifex = MutableLiveData<List<Taifex>>()
    val taifex: LiveData<List<Taifex>> = _taifex

    fun loadTaifex()
    {
        val bwibbuAllList = repository.parseBwibbuAllList()
        val stockDayAllList = repository.parseStockDayAllList()
        val stockDayAvgAllList = repository.parseStockDayAvgAllList()
        _taifex.value = repository.mergeDataClasses(bwibbuAllList, stockDayAllList, stockDayAvgAllList)

    }

    fun sortByCodeDesc()
    {
        _taifex.value = _taifex.value?.sortedByDescending { it.code }
    }

    fun sortByCodeAsc()
    {
        _taifex.value = _taifex.value?.sortedBy { it.code }
    }
}