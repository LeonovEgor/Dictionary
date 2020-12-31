package ru.leonov.history.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import leonov.ru.model.entity.TranslateResult
import ru.leonov.history.R
import ru.leonov.history.databinding.ActivityHistoryRecyclerviewItemBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var data: List<TranslateResult> = arrayListOf()

    fun setData(data: List<TranslateResult>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_history_recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ActivityHistoryRecyclerviewItemBinding.bind(itemView)

        fun bind(data: TranslateResult) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                binding.tvHistoryHeaderItem.text = data.text
                itemView.setOnClickListener {
                    Toast.makeText(itemView.context, "on click: ${data.text}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
