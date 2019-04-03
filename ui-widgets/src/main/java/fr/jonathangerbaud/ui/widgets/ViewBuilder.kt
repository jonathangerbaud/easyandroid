package fr.jonathangerbaud.ui.widgets

import android.content.Context
import android.view.View


interface ViewBuilder
{
    fun build(context: Context): View
}