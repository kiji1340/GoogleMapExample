package com.dbao1608.googlemapexample.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dbao1608.googlemapexample.R
import com.dbao1608.googlemapexample.model.Place
import com.dbao1608.googlemapexample.view.adapter.OnItemViewClickListener
import com.dbao1608.googlemapexample.view.adapter.PlaceAdapter
import com.dbao1608.googlemapexample.viewmodel.PlaceViewModel
import kotlinx.android.synthetic.main.view_search_places.view.*

class SearchView : ConstraintLayout, OnItemViewClickListener<Place> {

    private var placeViewModel: PlaceViewModel? = null

    private var backPressedListener: SearchViewListener.BackButton? = null

    private val placeAdapter = PlaceAdapter()

    var isOrigin: Boolean = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    fun setOnBackPressedListener(backPressedListener: SearchViewListener.BackButton) {
        this.backPressedListener = backPressedListener
    }

    fun attachOnActivity(activity: FragmentActivity) {
        placeViewModel = ViewModelProviders.of(activity)[PlaceViewModel::class.java]
        placeViewModel!!.apply {
            attach(activity)
            addContext(activity)
            getHistory()
        }
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_search_places, this, true)

        backBtn.setOnClickListener {
            backPressedListener?.onBackPressedEvent(isOrigin, null)
        }

        placeRc.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = placeAdapter
        }
        placeAdapter.setOnItemViewClickListener(this)

        handleSearchEvent()
    }

    private fun handleSearchEvent() {
        searchEdit.apply {
            addTextChangedListener {
                if (text.isNullOrEmpty() || text.isBlank()) getHistory()
            }

            setOnKeyListener(object : OnKeyListener {
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                    if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                        placeViewModel?.isAttached()?.let {
                            val result = searchEdit.text?.trim().toString()
                            placeViewModel
                                ?.searchPlace(result)
                                ?.observe(it,
                                    Observer<List<Place>> { places ->
                                        placeAdapter.updateData(places)
                                        placeAdapter.notifyDataSetChanged()
                                    }
                                )
                        }
                        return true
                    }
                    return false
                }

            })
        }

        cancelBtn.setOnClickListener {
            searchEdit.setText("")
        }
    }

    private fun getHistory() {
        if (placeViewModel == null) return

        placeViewModel!!.isAttached()?.let {
            placeViewModel!!.getPlaceHistory().observe(it
                , Observer<List<Place>> { data ->
                    placeAdapter.updateData(data)
                    placeAdapter.notifyDataSetChanged()
                })
        }
    }

    override fun onItemClick(data: Place) {
        placeViewModel?.insertPlaceCache(data)
        backPressedListener?.onBackPressedEvent(isOrigin, data)
    }


}

interface SearchViewListener {
    interface BackButton {
        fun onBackPressedEvent(isOrigin: Boolean, data: Place?)
    }
}