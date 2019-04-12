package fr.jonathangerbaud.ui.recyclerview

import android.view.ViewGroup
import fr.jonathangerbaud.ui.listitems.Row

abstract class RowRenderer<T>(parent: ViewGroup) : ViewRenderer<T, Row>(Row(parent.context))