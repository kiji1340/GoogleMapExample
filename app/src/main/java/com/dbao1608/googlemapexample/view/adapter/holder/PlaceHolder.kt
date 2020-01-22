package com.dbao1608.googlemapexample.view.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dbao1608.googlemapexample.R
import com.dbao1608.googlemapexample.model.DataType
import com.dbao1608.googlemapexample.model.Place
import kotlinx.android.synthetic.main.item_place.view.*

class PlaceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    fun bind(place: Place){
        if(place.typeData == DataType.REMOTE){
            itemView.iconImg.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_place))
        }else{
            itemView.iconImg.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_clock))
        }
        itemView.placeNameTxt.text = place.name
        itemView.placeDescriptionTxt.text = place.address
    }
}