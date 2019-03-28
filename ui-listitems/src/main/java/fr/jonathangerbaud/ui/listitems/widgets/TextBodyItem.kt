package fr.jonathangerbaud.ui.listitems.widgets

import fr.jonathangerbaud.ui.listitems.style.MaterialListBodyStyle

class TextBodyItem : TextItem()
{
    init
    {
        styler(MaterialListBodyStyle())
    }
}