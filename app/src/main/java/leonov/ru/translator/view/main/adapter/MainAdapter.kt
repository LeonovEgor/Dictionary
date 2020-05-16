package leonov.ru.translator.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import leonov.ru.translator.R
import leonov.ru.translator.model.entity.TranslateResult
import leonov.ru.translator.utils.image.loadByUrl

class MainAdapter(private var onListItemClickListener: OnListItemClickListener) :
    RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    private var data: List<TranslateResult> = arrayListOf()

    fun setData(data: List<TranslateResult>) {
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

        fun bind(data: TranslateResult) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.tv_item_header.text = data.text
                val transcriptionWithBrackets = "$leftBracket${data.transcription}$rightBracket"
                itemView.tv_item_transcription.text = transcriptionWithBrackets
                itemView.tv_item_description.text = data.translation
                itemView.iv_picture.loadByUrl("$httpsPrefix${data.previewUrl}")

                itemView.setOnClickListener { openInNewWindow(data) }
            }
        }
    }

    private fun openInNewWindow(listItemData: TranslateResult) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: TranslateResult)
    }
}