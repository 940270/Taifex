package com.example.taifex

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taifex.data.Taifex
import com.example.taifex.data.TaifexRepository
import com.example.taifex.viewmodel.TaifexViewModel

class MainActivity : AppCompatActivity()
{
    private lateinit var viewModel: TaifexViewModel
    private lateinit var adapter: TaifexAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaifexAdapter(emptyList()) { it ->
            showAlert(it)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val repository = TaifexRepository(applicationContext)
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TaifexViewModel(repository) as T
            }
        }

        viewModel = ViewModelProvider(this, factory)[TaifexViewModel::class.java]

        viewModel.taifex.observe(this) { it ->
            adapter.updateData(it)
            recyclerView.smoothScrollToPosition(0)
        }

        viewModel.loadTaifex()
    }

    private fun showAlert(item: Taifex)
    {
        AlertDialog.Builder(this)
            .setTitle(item.name)
            .setMessage("本益比: ${item.bwibbuAll?.PEratio}\n殖利率(%): ${item.bwibbuAll?.DividendYield}%\n股價淨值比: ${item.bwibbuAll?.PBratio}")
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when (item.itemId)
        {
            R.id.action_filter ->
            {
                showSortMenu(findViewById(R.id.action_filter))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSortMenu(anchor: View)
    {
        val popup = PopupMenu(this, anchor)
        popup.menuInflater.inflate(R.menu.menu_sort_options, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId)
            {
                R.id.sort_desc -> viewModel.sortByCodeDesc()
                R.id.sort_asc -> viewModel.sortByCodeAsc()
            }
            true
        }
        popup.show()
    }
}