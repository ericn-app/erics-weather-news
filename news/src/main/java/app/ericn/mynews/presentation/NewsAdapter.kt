package app.ericn.mynews.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.ericn.android_common.ImageLoader
import app.ericn.mynews.R
import app.ericn.mynews.core.Article
import app.ericn.mynews.databinding.ItemNewsBinding

class NewsAdapter(private val imageLoader: ImageLoader) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private var items: List<Article> = ArrayList()

    class ViewHolder(
        private val binding: ItemNewsBinding,
        private val imageLoader: ImageLoader
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.title.text = article.title
            binding.subtitle.text = itemView.context.getString(R.string.source_by, article.source)
            imageLoader.load(binding.thumb, article.thumb)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding, imageLoader)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun update(articles: List<Article>) {
        items = articles
        notifyDataSetChanged()
    }
}