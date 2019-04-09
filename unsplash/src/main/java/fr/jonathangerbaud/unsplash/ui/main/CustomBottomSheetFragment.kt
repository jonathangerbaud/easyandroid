package fr.jonathangerbaud.unsplash.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.warkiz.tickseekbar.OnSeekChangeListener
import com.warkiz.tickseekbar.SeekParams
import com.warkiz.tickseekbar.TickSeekBar
import fr.jonathangerbaud.ui.core.AspectRatio
import fr.jonathangerbaud.unsplash.R


@SuppressLint("ValidFragment")
class CustomBottomSheetFragment(
    private val columns: Int,
    private val padding: Int,
    private val spacing: Int,
    private val aspectRatio: Float
) : BottomSheetDialogFragment()
{
    var callback: ((columns: Int, padding: Int, spacing: Int, aspectRatio: Float) -> Unit)? = null

    private lateinit var columnSeekbar: TickSeekBar
    private lateinit var paddingSeekbar: TickSeekBar
    private lateinit var spacingSeekbar: TickSeekBar
    private lateinit var aspectGroup: RadioGroup

    private val listener = object : SimpleListener()
    {
        override fun onSeeking(seekParams: SeekParams?)
        {
            notifyCallback()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        columnSeekbar = view.findViewById(R.id.column_seekbar)
        paddingSeekbar = view.findViewById(R.id.padding_seekbar)
        spacingSeekbar = view.findViewById(R.id.spacing_seekbar)
        aspectGroup = view.findViewById(R.id.aspect_group)

        columnSeekbar.onSeekChangeListener = listener
        paddingSeekbar.onSeekChangeListener = listener
        spacingSeekbar.onSeekChangeListener = listener

        aspectGroup.setOnCheckedChangeListener { group, checkedId -> notifyCallback() }

        when (aspectRatio)
        {
            AspectRatio.SQUARE -> aspectGroup.check(R.id.aspect_square)
            AspectRatio.x16_9 -> aspectGroup.check(R.id.aspect_16_9)
            AspectRatio.x9_16 -> aspectGroup.check(R.id.aspect_9_16)
            AspectRatio.x4_3 -> aspectGroup.check(R.id.aspect_4_3)
            AspectRatio.x3_2 -> aspectGroup.check(R.id.aspect_3_2)
        }


        columnSeekbar.setProgress(columns.toFloat())
        paddingSeekbar.setProgress(padding.toFloat())
        spacingSeekbar.setProgress(spacing.toFloat())
    }

    private fun notifyCallback()
    {
        val aspectRatio: Float = when (aspectGroup.checkedRadioButtonId)
        {
            R.id.aspect_square -> AspectRatio.SQUARE
            R.id.aspect_16_9 -> AspectRatio.x16_9
            R.id.aspect_9_16 -> AspectRatio.x9_16
            R.id.aspect_4_3 -> AspectRatio.x4_3
            R.id.aspect_3_2 -> AspectRatio.x3_2
            else -> AspectRatio.SQUARE
        }

        callback?.invoke(columnSeekbar.progress, paddingSeekbar.progress, spacingSeekbar.progress, aspectRatio)
    }
}

open class SimpleListener : OnSeekChangeListener
{
    override fun onSeeking(seekParams: SeekParams?)
    {
    }

    override fun onStartTrackingTouch(seekBar: TickSeekBar?)
    {
    }

    override fun onStopTrackingTouch(seekBar: TickSeekBar?)
    {
    }

}