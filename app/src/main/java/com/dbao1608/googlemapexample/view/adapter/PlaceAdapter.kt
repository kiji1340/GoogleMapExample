package com.dbao1608.googlemapexample.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dbao1608.domain.entity.Place
import com.dbao1608.googlemapexample.R
import com.dbao1608.googlemapexample.view.adapter.holder.PlaceHolder

class PlaceAdapter : RecyclerView.Adapter<PlaceHolder>() {

    private var data = ArrayList<Place>()
    private var listener: OnItemViewClickListener<Place>? = null

    fun updateData(dataUpdate: List<Place>) {
        data.clear()
        data.addAll(dataUpdate)
    }

    fun setOnItemViewClickListener(listener: OnItemViewClickListener<Place>){
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return PlaceHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        holder.bind(data[position])

        holder.itemView.setOnClickListener{
            listener?.onItemClick(data[position])
        }
    }

}