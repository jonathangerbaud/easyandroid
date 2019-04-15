package fr.jonathangerbaud.ui.core.ext

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.SwitchCompat
import fr.jonathangerbaud.core.util.ResUtils


fun SwitchCompat.textOnRes(@StringRes stringRes:Int) { textOn = ResUtils.getString(stringRes) }
fun SwitchCompat.textOffRes(@StringRes stringRes:Int) { textOff = ResUtils.getString(stringRes) }
fun SwitchCompat.textColorRes(@ColorRes colorRes:Int) { setTextColor(ResUtils.getColor(colorRes, context)) }