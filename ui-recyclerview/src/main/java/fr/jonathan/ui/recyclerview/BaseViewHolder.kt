package fr.jonathan.ui.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.jonathangerbaud.ui.core.ext.inflate


open class BaseViewHolder(parent: ViewGroup, layout: Int) :
        RecyclerView.ViewHolder(parent.inflate(layout))