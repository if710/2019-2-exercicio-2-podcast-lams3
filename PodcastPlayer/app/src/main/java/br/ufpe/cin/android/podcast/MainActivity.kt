package br.ufpe.cin.android.podcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val xmlLink = "https://s3-us-west-1.amazonaws.com/podcasts.thepolyglotdeveloper.com/podcast.xml"

    private lateinit var itemFeedDatabase: ItemFeedDatabase
    private var itemFeeds: MutableList<ItemFeed> = mutableListOf()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Requisitando instancia da Database
        itemFeedDatabase = ItemFeedDatabase.getInstance(this)

        // Atribuindo o Adapter implementado, assim como um LinearLayoutManager ao RecyclerView
        recyclerView = layoutRecyclerView
        recyclerView.adapter = ItemFeedAdapter(itemFeeds, this)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Carregamento assincrono do XML
        doAsync {
            try {
                // Download do XML
                val xmlText = URL(xmlLink).readText()

                // Parse do XML
                val xmlItems = Parser.parse(xmlText).toTypedArray()

                // Atualiza base de dados
                itemFeedDatabase.itemFeedDao().insert(*xmlItems)
            } catch (exception: Exception) {
                Log.d("DEBUG", exception.toString())
            } finally {
                // Atualiza copia local
                val allItems = itemFeedDatabase.itemFeedDao().getAll()
                itemFeeds.addAll(allItems)

                Log.d("DEBUG", allItems.size.toString())

                // Atualiza a RecyclerView na thread principal
                runOnUiThread {
                    (recyclerView.adapter as ItemFeedAdapter).notifyDataSetChanged()
                }
            }
        }
    }
}
