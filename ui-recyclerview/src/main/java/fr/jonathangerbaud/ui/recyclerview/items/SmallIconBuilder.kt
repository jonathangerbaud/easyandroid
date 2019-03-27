package fr.jonathangerbaud.ui.recyclerview.items

class SmallIconBuilder : ImageBuilder()
{
    init {
        measurements(IconMeasurements())
    }

    private class IconMeasurements : DefaultMeasurements()
    {
        override fun getMinListItemHeight():Int
        {
            return SIZE_48
        }

        override fun getTopPadding(minHeight: Int): Int {
            if (minHeight >= SIZE_72)
                return SIZE_24
            else if (minHeight >= SIZE_56)
                return SIZE_16
            else
                return SIZE_12
        }

        override fun getEndMargin(): Int {
            return SIZE_32
        }

        override fun getWidth(): Int
        {
            return SIZE_24
        }

        override fun getHeight(): Int
        {
            return SIZE_24
        }
    }
}