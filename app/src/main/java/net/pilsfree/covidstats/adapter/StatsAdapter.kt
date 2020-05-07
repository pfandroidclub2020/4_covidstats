package net.pilsfree.covidstats.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.pilsfree.covidstats.R
import net.pilsfree.covidstats.model.Stat
import java.text.SimpleDateFormat

class StatsAdapter(val list: List<Stat>) : RecyclerView.Adapter<StatsAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stat,parent,false)
        return StatsAdapter.ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val view = holder.itemView

        val date = view.findViewById<TextView>(R.id.date)
        val confirmed = view.findViewById<TextView>(R.id.confirmed)
        val recovered = view.findViewById<TextView>(R.id.recovered)
        val deaths = view.findViewById<TextView>(R.id.deaths)

        val format = SimpleDateFormat("d. M.")

        date.text = format.format(list[position].Date)

        confirmed.text = "\uD83E\uDD12" + list[position].Active.toString()
        recovered.text = "\uD83D\uDE01" + list[position].Recovered.toString()
        deaths.text = "\uD83D\uDC80" + list[position].Deaths.toString()

    }

}