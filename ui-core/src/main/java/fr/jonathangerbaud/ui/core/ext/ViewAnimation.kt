package fr.jonathangerbaud.ui.core.ext

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator


private val duration = 750L
private inline val interpolator: Interpolator
    get() = AccelerateDecelerateInterpolator()

fun View.animateWidth(toValue: Int, duration: Long = fr.jonathangerbaud.ui.core.ext.duration,
                      interpolator:
Interpolator =
                          fr.jonathangerbaud.ui.core.ext.interpolator
): AnimatePropsWrapper {
    if (toValue == width || layoutParams == null) {
        return AnimatePropsWrapper(null)
    }
    return AnimatePropsWrapper(ValueAnimator().apply {
        setIntValues(width, toValue)
        setDuration(duration)
        setInterpolator(interpolator)
        addUpdateListener {
            val lp = layoutParams
            lp.width = it.animatedValue as Int
            layoutParams = lp
        }
        start()
    })
}

fun View.animateWidthBy(byValue: Int, duration: Long = fr.jonathangerbaud.ui.core.ext.duration, interpolator: Interpolator = fr.jonathangerbaud.ui.core.ext.interpolator)
        = animateWidth(width + byValue, duration, interpolator)

fun View.animateHeight(toValue: Int, duration: Long = fr.jonathangerbaud.ui.core.ext.duration, interpolator: Interpolator = fr.jonathangerbaud.ui.core.ext.interpolator): AnimatePropsWrapper {
    if (toValue == height || layoutParams == null) {
        return AnimatePropsWrapper(null)
    }
    return AnimatePropsWrapper(ValueAnimator().apply {
        setIntValues(height, toValue)
        setDuration(duration)
        setInterpolator(interpolator)
        addUpdateListener {
            val lp = layoutParams
            lp.height = it.animatedValue as Int
            layoutParams = lp
        }
        start()
    })
}

fun View.animateHeightBy(byValue: Int, duration: Long = fr.jonathangerbaud.ui.core.ext.duration, interpolator: Interpolator = fr.jonathangerbaud.ui.core.ext.interpolator)
        = animateHeight(height + byValue, duration, interpolator)


fun View.animateX(toValue: Float, duration: Long = fr.jonathangerbaud.ui.core.ext.duration, interpolator: Interpolator = fr.jonathangerbaud.ui.core.ext.interpolator): AnimatePropsWrapper {
    if (toValue == translationX) {
        return AnimatePropsWrapper(null)
    }
    return AnimatePropsWrapper(ValueAnimator().apply {
        setFloatValues(translationX, toValue)
        setDuration(duration)
        setInterpolator(interpolator)
        addUpdateListener { this@animateX.translationX = it.animatedValue as Float }
        start()
    })
}

fun View.animateXBy(toValue: Float, duration: Long = fr.jonathangerbaud.ui.core.ext.duration, interpolator: Interpolator = fr.jonathangerbaud.ui.core.ext.interpolator)
        = animateX(translationX + toValue, duration, interpolator)

fun View.animateY(toValue: Float, duration: Long = fr.jonathangerbaud.ui.core.ext.duration, interpolator: Interpolator = fr.jonathangerbaud.ui.core.ext.interpolator): AnimatePropsWrapper {
    if (toValue == translationY) {
        return AnimatePropsWrapper(null)
    }
    return AnimatePropsWrapper(ValueAnimator().apply {
        setFloatValues(translationY, toValue)
        setDuration(duration)
        setInterpolator(interpolator)
        addUpdateListener { this@animateY.translationY = it.animatedValue as Float }
        start()
    })
}

fun View.animateYBy(toValue: Float, duration: Long = fr.jonathangerbaud.ui.core.ext.duration, interpolator: Interpolator = fr.jonathangerbaud.ui.core.ext.interpolator)
        = animateY(translationY + toValue, duration, interpolator)

class AnimatePropsWrapper(private val animator: Animator?) {

    fun onEnd(block: () -> Unit) {
        if (animator == null)
        {
            block()
        }
        else
        {
            animator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator?)
                {
                    block()
                }

                override fun onAnimationRepeat(animation: Animator?)
                {

                }

                override fun onAnimationCancel(animation: Animator?)
                {

                }

                override fun onAnimationStart(animation: Animator?)
                {

                }
            })
        }
    }
}

fun View.animateAlpha(toValue: Float, duration: Long = fr.jonathangerbaud.ui.core.ext.duration, interpolator: Interpolator = fr.jonathangerbaud.ui.core.ext.interpolator): AnimatePropsWrapper {
    if (toValue == alpha) {
        return AnimatePropsWrapper(null)
    }
    return AnimatePropsWrapper(ValueAnimator().apply {
        setFloatValues(alpha, toValue)
        setDuration(duration)
        setInterpolator(interpolator)
        addUpdateListener { this@animateAlpha.alpha = it.animatedValue as Float }
        start()
    })
}

fun View.showAlphaAnimated(display:Boolean = true, duration:Long = 300): Unit {
    if (display)
    {
        alpha = 0f
        show()
        animateAlpha(1f, duration)
    }
    else
    {
        animateAlpha(0f, duration).onEnd { hide() }
    }
}

fun View.hideAlphaAnimated(duration:Long = 300): Unit = showAlphaAnimated(false, duration)