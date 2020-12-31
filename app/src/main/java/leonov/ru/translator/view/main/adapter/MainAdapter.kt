package leonov.ru.translator.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import leonov.ru.model.entity.TranslateResult
import leonov.ru.translator.R
import leonov.ru.translator.databinding.RecyclerviewItemBinding
import leonov.ru.utils.addHttpsPrefix
import leonov.ru.utils.image.loadByUrl
import leonov.ru.utils.surroundBrackets

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
        private val binding = RecyclerviewItemBinding.bind(itemView)

        fun bind(data: TranslateResult) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                with(binding) {
                    tvItemHeader.text = data.text
                    tvItemTranscription.text = data.transcription.surroundBrackets()
                    tvItemDescription.text = data.translation
                    ivPicture.loadByUrl(data.previewUrl.addHttpsPrefix())
                }
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