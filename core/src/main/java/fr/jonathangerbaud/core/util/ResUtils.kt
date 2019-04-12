package fr.jonathangerbaud.core.util

import android.content.Context
import android.content.res.AssetManager
import android.content.res.ColorStateList
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

    fun getDimension(context:Context, @DimenRes resId: Int): Float {
        return context.resources.getDimension(resId)
    }

    fun getDimensionPxSize(@DimenRes resId: Int): Int {
        return res.getDimensionPixelSize(resId)
    }

    fun getDimensionPxSize(context:Context, @DimenRes resId: Int): Int {
        return context.resources.getDimensionPixelSize(resId)
    }

    fun getBoolean(@BoolRes resId: Int): Boolean {
        return res.getBoolean(resId)
    }

    fun getBoolean(context:Context, @BoolRes resId: Int): Boolean {
        return context.resources.getBoolean(resId)
    }

    fun getString(@StringRes resId: Int): String {
        return res.getString(resId)
    }

    fun getString(context:Context, @StringRes resId: Int): String {
        return context.resources.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg args: Any): String {
        return res.getString(resId, *args)
    }

    fun getString(context:Context, @StringRes resId: Int, vararg args: Any): String {
        return context.resources.getString(resId, *args)
    }

    fun getStringArray(@ArrayRes resId: Int): Array<String> {
        return res.getStringArray(resId)
    }

    fun getStringArray(context:Context, @ArrayRes resId: Int): Array<String> {
        return context.resources.getStringArray(resId)
    }

    fun getIntArray(@ArrayRes resId: Int): IntArray {
        return res.getIntArray(resId)
    }

    fun getIntArray(context:Context, @ArrayRes resId: Int): IntArray {
        return context.resources.getIntArray(resId)
    }

    fun getColor(@ColorRes resId: Int, context:Context? = null): Int {
        return ContextCompat.getColor(context ?: ResUtils.context, resId)
    }

    fun getColorStateList(@ColorRes resId: Int): ColorStateList? {
        return ContextCompat.getColorStateList(context, resId)
    }

    fun getColorStateList(context: Context, @ColorRes resId: Int): ColorStateList? {
        return ContextCompat.getColorStateList(context, resId)
    }

    fun getDrawable(@DrawableRes resId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }

    fun getDrawable(context:Context, @DrawableRes resId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }

    fun getAssetManager(): AssetManager {
        return res.assets
    }

    fun getDrawableByName(name: String): Int {
        return res.getIdentifier(name, "drawable", context.packageName)
    }

    fun getDrawableByName(context:Context, name: String): Int {
        return context.resources.getIdentifier(name, "drawable", context.packageName)
    }

    fun getStringByName(name: String): String {
        return getString(res.getIdentifier(name, "string", context.packageName))
    }

    fun getStringByName(context:Context, name: String): String {
        return getString(context.resources.getIdentifier(name, "string", context.packageName))
    }

    fun getDpInPx(dpSize: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpSize.toFloat(), res.displayMetrics).toInt()
    }

    fun getSpInPx(spSize: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spSize.toFloat(), res.displayMetrics).toInt()
    }
}
