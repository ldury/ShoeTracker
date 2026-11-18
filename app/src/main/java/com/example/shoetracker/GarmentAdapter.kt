package com.example.shoetracker



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class GarmentAdapter(
    private var items: List<GarmentItem> = emptyList(),
    private val onItemClick: (GarmentItem) -> Unit
) : RecyclerView.Adapter<GarmentAdapter.GarmentViewHolder>() {

    class GarmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GarmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_garment, parent, false)
        return GarmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: GarmentViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = item.name
        holder.tvCategory.text = "Категория: ${item.category}"

        val nextCleanDate = item.lastCleanedDate + TimeUnit.DAYS.toMillis(item.cleaningIntervalDays.toLong())
        val now = System.currentTimeMillis()

        if (now >= nextCleanDate) {
            holder.tvStatus.text = "🔴 ПОРА ЧИСТИТЬ!"
        } else {
            val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val nextDateString = formatter.format(Date(nextCleanDate))
            holder.tvStatus.text = "✅ Всё чисто. Следующая: $nextDateString"
        }

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount() = items.size

    fun updateItems(newItems: List<GarmentItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}