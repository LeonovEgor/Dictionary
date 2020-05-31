package leonov.ru.translator.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item.view.*
//import kotlinx.android.synthetic.main.recyclerview_item.view.*
import leonov.ru.translator.R
import leonov.ru.model.entity.TranslateResult
import leonov.ru.utils.addHttpsPrefix
import leonov.ru.utils.image.loadByUrl
import leonov.ru.utils.surroundBrackets
import leonov.ru.utils.ui.viewById

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

        private val itemHeader by viewById<TextView>(R.id.tv_item_header)
        private val itemTranscription by viewById<TextView>(R.id.tv_item_transcription)
        private val itemDescription by viewById<TextView>(R.id.tv_item_description)
        private val itemPicture by viewById<ImageView>(R.id.iv_picture)

        fun bind(data: TranslateResult) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemHeader.text = data.text
                itemTranscription.text = data.transcription.surroundBrackets()
                itemDescription.text = data.translation
                itemPicture.loadByUrl(data.previewUrl.addHttpsPrefix())

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