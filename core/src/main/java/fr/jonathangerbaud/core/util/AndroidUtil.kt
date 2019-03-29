package fr.jonathangerbaud.core.util

import android.os.Build


class AndroidUtil
{
    companion object
    {
        const val PIE = Build.VERSION_CODES.P
        const val OREO_MR1 = Build.VERSION_CODES.O_MR1
        const val OREO = Build.VERSION_CODES.O
        const val NOUGAT_MR1 = Build.VERSION_CODES.N_MR1
        const val NOUGAT = Build.VERSION_CODES.N
        const val MARSHMALLOW_MR1 = Build.VERSION_CODES.N_MR1
        const val MARSHMALLOW = Build.VERSION_CODES.M
        const val LOLLIPOP_MR1 = Build.VERSION_CODES.LOLLIPOP_MR1
        const val LOLLIPOP = Build.VERSION_CODES.LOLLIPOP

        fun isMinApi(api:Int): Boolean
        {
            return Build.VERSION.SDK_INT >= api
        }

        fun isMinPie():Boolean
        {
            return isMinApi(PIE)
        }

        fun isMinOreoMR1():Boolean
        {
            return isMinApi(OREO_MR1)
        }

        fun isMinOreo():Boolean
        {
            return isMinApi(OREO)
        }

        fun isMinNougatMR1():Boolean
        {
            return isMinApi(NOUGAT_MR1)
        }

        fun isMinNougat():Boolean
        {
            return isMinApi(NOUGAT)
        }

        fun isMinMarshmallowMR1():Boolean
        {
            return isMinApi(MARSHMALLOW_MR1)
        }

        fun isMinMarshmallow():Boolean
        {
            return isMinApi(MARSHMALLOW)
        }

        fun isMinLollipopMR1():Boolean
        {
            return isMinApi(LOLLIPOP_MR1)
        }

        fun isMinLollipop():Boolean
        {
            return isMinApi(LOLLIPOP)
        }
    }
}