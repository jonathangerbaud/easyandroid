package fr.jonathangerbaud.core.util

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.*
import androidx.core.content.ContextCompat
import fr.jonathangerbaud.core.BaseApp

object ResUtils {
    private val context: Context
        get() = BaseApp.instance

    private val res: Resources
        get() = context.resources

    fun getDimension(@DimenRes resId: Int): Float {
        return res.getDimension(resId)
    }

    fun getDimensionPxSize(@DimenRes resId: Int): Int {
        return res.getDimensionPixelSize(resId)
    }

    fun getBooleanString(@BoolRes resId: Int): Boolean {
        return res.getBoolean(resId)
    }

    fun getString(@StringRes resId: Int): String {
        return res.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg args: Any): String {
        return res.getString(resId, *args)
    }

    fun getStringArray(@ArrayRes resId: Int): Array<String> {
        return res.getStringArray(resId)
    }

    fun getIntArray(@ArrayRes resId: Int): IntArray {
        return res.getIntArray(resId)
    }

    fun getColor(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }

    fun getDrawable(@DrawableRes resId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }

    fun getAssetManager(@ArrayRes resId: Int): AssetManager {
        return res.assets
    }

    fun getDrawableByName(name: String): Int {
        return res.getIdentifier(name, "drawable", context.packageName)
    }

    fun getStringByName(name: String): String {
        return getString(res.getIdentifier(name, "string", context.packageName))
    }

    fun getDpInPx(dpSize: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpSize.toFloat(), res.displayMetrics).toInt()
    }

    fun getSpInPx(spSize: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spSize.toFloat(), res.displayMetrics).toInt()
    }
}
