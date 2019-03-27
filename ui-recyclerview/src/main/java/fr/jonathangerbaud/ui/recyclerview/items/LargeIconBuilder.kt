package fr.jonathangerbaud.ui.recyclerview.items

class LargeIconBuilder : ImageBuilder()
{
    init {
        measurements(IconMeasurements())
    }

    private class IconMeasurements : DefaultMeasurements()
    {
        override fun getMinListItemHeight():Int
        {
            return SIZE_72
        }

        override fun getTopPadding(minHeight: Int): Int {
            if (minHeight <= SIZE_88)
                return SIZE_8
            else
                return SIZE_16
        }

        override fun getWidth(): Int
        {
            return SIZE_56
        }

        override fun getHeight(): Int
        {
            return SIZE_56
        }
    }
}