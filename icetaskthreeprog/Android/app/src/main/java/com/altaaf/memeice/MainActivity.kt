package com.altaaf.memeice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.altaaf.memeice.api.ApiClient
import com.altaaf.memeice.api.MemeService
import com.altaaf.memeice.model.Meme
import com.altaaf.memeice.ui.MemeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var service: MemeService
    private lateinit var rv: RecyclerView
    private val adapter = MemeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById(R.id.rvMemes)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        service = ApiClient.instance

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val res = service.getMemes()
                withContext(Dispatchers.Main) {
                    adapter.submitList(res.items)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}