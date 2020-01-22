package com.dbao1608.googlemapexample.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.dbao1608.googlemapexample.R
import kotlinx.android.synthetic.main.view_map_bottom_controller.view.*

class MapBottomControllerView : ConstraintLayout, View.OnClickListener,
    RadioGroup.OnCheckedChangeListener {
    constructor(context: Context) : super(context)

    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    private var textViewListener: MapBottomListener.TextView? = null
    private var modeMovingListener: MapBottomListener.ModeMoving? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_map_bottom_controller, this, true)
        startTxt.setOnClickListener(this)
        endTxt.setOnClickListener(this)
        radioGroup.setOnCheckedChangeListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            startTxt -> textViewListener?.onTextViewEvent(true)
            endTxt -> textViewListener?.onTextViewEvent(false)
        }
    }

    fun setOnTextViewEvent(textViewListener: MapBottomListener.TextView) {
        this.textViewListener = textViewListener
    }

    fun setOnModeMovingEvent(modeMovingListener: MapBottomListener.ModeMoving){
        this.modeMovingListener = modeMovingListener
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.driving -> {
                modeMovingListener?.onModeEvent("driving")
            }
            R.id.transit -> {
                modeMovingListener?.onModeEvent("transit")
            }
            R.id.bicycling -> {
                modeMovingListener?.onModeEvent("bicycling")
            }
            R.id.walking -> {
                modeMovingListener?.onModeEvent("walking")
            }
        }
    }
}

interface MapBottomListener {
    interface TextView {
        fun onTextViewEvent(isOrigin: Boolean)
    }

    interface ModeMoving {
        fun onModeEvent(mode: String)
    }
}