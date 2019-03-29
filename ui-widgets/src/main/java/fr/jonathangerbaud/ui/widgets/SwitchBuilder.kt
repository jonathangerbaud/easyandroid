package fr.jonathangerbaud.ui.widgets

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.*
import androidx.appcompat.widget.SwitchCompat
import fr.jonathangerbaud.core.util.ResUtils

open class SwitchBuilder<T : SwitchBuilder<T>> : CompoundButtonBuilder<T>()
{
    private var textOn:String? = null
    private var textOnRes:Int? = null
    private var textOff:String? = null
    private var textOffRes:Int? = null
    private var textColor:Int? = null
    private var textColorRes:Int? = null
    private var textColorStateList:ColorStateList? = null
    private var textSize:Float? = null
    private var textSizeRes:Int? = null
    private var font: Typeface? = null
    private var thumbDrawable: Drawable? = null
    private var thumbDrawableRes: Int? = null
    private var trackDrawable: Drawable? = null
    private var trackDrawableRes: Int? = null
    private var splitTrack: Boolean? = null

    override fun createView(context: Context): View
    {
        return SwitchCompat(context)
    }

    override fun applyViewAttributes(view: View)
    {
        super.applyViewAttributes(view)
        val view = view as SwitchCompat

        textOn?.let { view.textOn = it}
        textOnRes?.let { view.textOn = ResUtils.getString(it)}
        textOff?.let { view.textOff = it}
        textOffRes?.let { view.textOff = ResUtils.getString(it)}
        textColor?.let { view.setTextColor(it)}
        textColorRes?.let { view.setTextColor(ResUtils.getColor(it))}
        textColorStateList?.let { view.setTextColor(it)}
        textSize?.let { view.textSize = it}
        textSizeRes?.let { view.textSize = ResUtils.getDimension(it)}
        font?.let { view.typeface = it }
        thumbDrawable?.let {view.thumbDrawable = it }
        thumbDrawableRes?.let {view.setThumbResource(it) }
        trackDrawable?.let {view.trackDrawable = it }
        trackDrawableRes?.let {view.setTrackResource(it) }
        splitTrack?.let {view.splitTrack = it }
    }

    fun textOn(text:String) = applySelf { textOn = text }
    fun textOn(@StringRes text:Int) = applySelf { textOnRes = text }
    fun textOff(text:String) = applySelf { textOff = text }
    fun textOff(@StringRes text:Int) = applySelf { textOffRes = text }

    fun thumbDrawable(@DrawableRes drawableRes:Int) = applySelf { this.thumbDrawableRes = drawableRes }
    fun thumbDrawable(drawable: Drawable) = applySelf { this.thumbDrawable = drawable }
    fun trackDrawable(@DrawableRes drawableRes:Int) = applySelf { this.trackDrawableRes = drawableRes }
    fun trackDrawable(drawable: Drawable) = applySelf { this.trackDrawable = drawable }
    fun splitTrack(split:Boolean) = applySelf { this.splitTrack = split }
}