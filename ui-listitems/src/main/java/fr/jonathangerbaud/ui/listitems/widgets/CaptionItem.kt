package fr.jonathangerbaud.ui.listitems.widgets

import fr.jonathangerbaud.ui.listitems.style.MaterialListCaptionStyle


class CaptionItem : TextItem()
{
    init
    {
        styler(MaterialListCaptionStyle())
    }
}