package br.ufpe.cin.android.podcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.jetbrains.anko.doAsync
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val xmlLink = "https://s3-us-west-1.amazonaws.com/podcasts.thepolyglotdeveloper.com/podcast.xml"

    private var itemFeeds: ArrayList<ItemFeed> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Carregamento assincrono do XML
        doAsync {
            try {
                // Download do XML
                val xmlText = URL(xmlLink).readText()

                // Parse do XML
                val xmlItems = Parser.parse(xmlText)

                itemFeeds.addAll(xmlItems)

            } catch (exception : Exception) {
                Log.d("DEBUG", exception.toString())
            }
        }
    }
}
