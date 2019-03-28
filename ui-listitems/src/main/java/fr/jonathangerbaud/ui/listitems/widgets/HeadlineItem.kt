package fr.jonathangerbaud.ui.listitems.widgets

import fr.jonathangerbaud.ui.listitems.style.MaterialListHeadlineStyle


class HeadlineItem : TextItem()
{
    init
    {
        styler(MaterialListHeadlineStyle())
    }
}