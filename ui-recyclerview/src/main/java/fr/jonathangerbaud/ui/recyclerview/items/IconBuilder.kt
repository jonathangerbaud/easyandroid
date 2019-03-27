package fr.jonathangerbaud.ui.recyclerview.items

class IconBuilder : ImageBuilder()
{
    init {
        measurements(IconMeasurements())
    }

    private class IconMeasurements : DefaultMeasurements()
    {
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