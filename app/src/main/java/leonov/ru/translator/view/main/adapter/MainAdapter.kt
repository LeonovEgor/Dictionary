package leonov.ru.translator.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import leonov.ru.translator.R
import leonov.ru.translator.model.data.SearchResult
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import leonov.ru.translator.utils.convertMeaningsToString
import leonov.ru.translator.utils.image.GlideImageLoader

class MainAdapter(private var onListItemClickListener: OnListItemClickListener) :
    RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    private var data: List<SearchResult> = arrayListOf()

    fun setData(data: List<SearchResult>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val leftBracket = "("
        private val rightBracket = ")"
        private val httpsPrefix = "https:"

        fun bind(data: SearchResult) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.tv_item_header.text = data.text

                data.meanings?.let {
                    itemView.tv_item_description.text = convertMeaningsToString(it)
                }

                data.meanings?.get(0)?.transcription?.let {
                    val transcription = leftBracket + it + rightBracket
                    itemView.tv_item_transcription.text = transcription
                }

                data.meanings?.get(0)?.previewUrl?.let {url->
                    GlideImageLoader().loadInto("$httpsPrefix$url", itemView.iv_picture)
                }

                itemView.setOnClickListener { openInNewWindow(data) }
            }
        }
    }

    private fun openInNewWindow(listItemData: SearchResult) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: SearchResult)
    }
}