package fr.jonathangerbaud.ui.recyclerview.items

class IconBuilder : ImageBuilder()
{
    init {
        measurements(IconMeasurements())
    }

    private class IconMeasurements : DefaultMeasurements()
    {
        override fun getMinListItemHeight():Int
        {
            return SIZE_56
        }

        override fun getTopPadding(minHeight:Int):Int
        {
            return if (minHeight < SIZE_72) SIZE_8 else SIZE_16
        }

        override fun getWidth(): Int
        {
            return SIZE_40
        }

        override fun getHeight(): Int
        {
            return SIZE_40
        }
    }
}