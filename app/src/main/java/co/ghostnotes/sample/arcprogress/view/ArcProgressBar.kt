package co.ghostnotes.sample.arcprogress.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Paint.Cap.ROUND
import android.graphics.Paint.Style.STROKE
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import timber.log.Timber

class ArcProgressBar @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val startAngle: Float = 90F + ARC_GAP_ANGLE
    private val sweepAngleFull: Float = 360F - (ARC_GAP_ANGLE * 2F)

    private var progress: Float = 0F

    private val painBackground: Paint = Paint().apply {
        isAntiAlias = true
        color = DEFAULT_ARC_BACKGROUND_COLOR
        style = STROKE
        strokeWidth = ARC_STROKE_WIDTH
        strokeCap = ROUND
    }

    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        style = STROKE
        strokeWidth = ARC_STROKE_WIDTH
        strokeCap = ROUND
    }

    private var arcShader: Shader? = null
        get() {
            if (width == 0 || height == 0) return null

            if (field == null) {
                field = createShader()
            }

            return field
        }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {
            // TODO 必要なときのみコメントアウトを外してください
            //drawDebug()

            drawArc(createOval(), startAngle, sweepAngleFull, false, painBackground)
            drawArc(createOval(), startAngle, progress, false, paint.apply { shader = arcShader })
        }
    }

    fun setPercent(percent: Int) {
        require(percent in 0..100) { "percent must be from 0 to 100. percent=[$percent]" }

        Timber.d("### percent=$percent")
        progress = sweepAngleFull * (percent / 100F)
        invalidate()
    }

    private fun createOval(): RectF = RectF(PADDING, PADDING, width - PADDING, height - PADDING)

    private fun createShader(): Shader {
        return SweepGradient(width / 2F, height / 2F, Color.YELLOW, Color.RED).apply {
            setLocalMatrix(
                Matrix().apply {
                    setRotate(90F, width / 2F, height / 2F)
                }
            )
        }
    }

    private fun drawDebug(canvas: Canvas) {
        canvas.drawRect(
            RectF(10F, 10F, width - 10F, height - 10F),
            Paint().apply { shader = createShader() }
        )
    }

    companion object {
        private const val PADDING = 24F
        private const val ARC_STROKE_WIDTH = 32F
        private const val ARC_GAP_ANGLE = 30F

        private val DEFAULT_ARC_BACKGROUND_COLOR = Color.parseColor("#3F0A00")
    }

}

fun Number.dpToPx(context: Context) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics).toInt()

fun Number.pxToDp(context: Context) =
    this.toFloat() / context.resources.displayMetrics.density

fun Number.spToPx(context: Context) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), context.resources.displayMetrics).toInt()

