package fr.jonathangerbaud.ui.listitems

import fr.jonathangerbaud.ui.widgets.ViewBuilder


interface RowItem : ViewBuilder
{
    fun getRowItemSpecs(): RowItemSpec
    {
        return DefaultRowItemSpec()
    }
}