package br.ufpe.cin.android.podcast

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.itemlista.view.*

class ItemFeedAdapter (private val itemFeeds: List<ItemFeed>, private val context : Context) : RecyclerView.Adapter<ItemFeedAdapter.ViewHolder>() {

    // Chamado quando uma nova view e atribuida a um ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItemFeed(itemFeeds[position])
    }

    // Cria uma View e retorna um ViewHolder para ela.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.itemlista, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = itemFeeds.size

    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        private var itemFeed: ItemFeed = ItemFeed("", "", "", "", "")

        fun setItemFeed(newItemFeed: ItemFeed) {
            itemFeed = newItemFeed

            // Atualiza a view com as informacoes do novo ItemFeed
            view.item_title.text = itemFeed.title
            view.item_date.text = itemFeed.pubDate

            // Abre nova Activity ao clickar na view
            view.setOnClickListener {
                val intent = Intent(view.context, EpisodeDetailActivity::class.java)
                intent.putExtra(Messages.ITEMFEED_EXTRA, itemFeed)
                startActivity(view.context, intent, null)
            }

            // Download ao clickar no botao
            view.item_action.setOnClickListener {
                val url = itemFeed.downloadLink
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(view.context, intent, null)
            }
        }
    }
}