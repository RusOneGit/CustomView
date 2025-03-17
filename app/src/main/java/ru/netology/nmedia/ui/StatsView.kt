package ru.netology.nmedia.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.RectF
import android.provider.SyncStateContract.Helpers.update
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.withStyledAttributes
import ru.netology.nmedia.R
import ru.netology.nmedia.util.AndroidUtils
import kotlin.math.min
import kotlin.random.Random

class StatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private var radius = 0F
    private var center = PointF(0F, 0F)
    private var oval = RectF(0F, 0F, 0F, 0F)

    private var lineWidth = AndroidUtils.dp(context, 5F).toFloat()
    private var fontSize = AndroidUtils.dp(context, 40F).toFloat()
    private var colors = emptyList<Int>()

    private var progress = 0F
    private var valueAnimator: ValueAnimator? = null

    // обработка атрибутов
    init {
        context.withStyledAttributes(attrs, R.styleable.StatsView) {
            lineWidth = getDimension(R.styleable.StatsView_lineWidth, lineWidth) // ширина строки
            fontSize = getDimension(R.styleable.StatsView_fontSize, fontSize)
            val resId = getResourceId(R.styleable.StatsView_colors, 0)
            colors = resources.getIntArray(resId).toList()
        }
    }

    // кисть
    private val paint = Paint(
        Paint.ANTI_ALIAS_FLAG // сглаживание
    ).apply {
        style = Paint.Style.STROKE // стиль отрисовки (отрисовываем строки)
        strokeWidth = lineWidth // ширина строки
        strokeCap =
            Paint.Cap.ROUND // округление линий при их пересечении (обработку начала и конца обводимых линий и контуров)
        strokeJoin = Paint.Join.ROUND // округление краев при отрисовке

    }

    // кисть для отрисовки текста
    private val textPaint = Paint(
        Paint.ANTI_ALIAS_FLAG // сглаживание
    ).apply {
        style = Paint.Style.FILL // стиль заливка
        textAlign = Paint.Align.CENTER // гравитация у текста (по центру)
        textSize = fontSize // размер текста
    }

    var data: List<Float> = emptyList()
        set(value) {
            field = value
            //invalidate()
            update()
        }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = min(w, h) / 2F - lineWidth / 2
        center = PointF(w / 2F, h / 2F)
        oval = RectF(
            // прямоугольник, область отрисовки
            center.x - radius, center.y - radius,
            center.x + radius, center.y + radius,
        )
    }

    override fun onDraw(canvas: Canvas) {
        var taskNomber = 3
        if (taskNomber == 1) {
            if (data.isEmpty()) {
                return
            }

            var startFrom = -90F // стартовый угол
            for ((index, datum) in data.withIndex()) {
                //val angle = 360F * datum // угол поворота
                val angle = ((360F * datum) / (4 * datum)) // угол поворота
                paint.color =
                    colors.getOrNull(index) ?: randomColor() // назначим каждому элементу свой цвет
                //canvas.drawArc(oval, startFrom, angle, false, paint) // отрисовка дуги
                canvas.drawArc(
                    oval,
                    startFrom + 360F * progress,
                    angle * progress,
                    false,
                    paint
                ) // отрисовка дуги
                startFrom += angle // отступ к стартовуму углу поворота
            }
            paint.color =
                colors.getOrNull(0) ?: randomColor()
            canvas.drawArc(oval, startFrom + 360 * progress, -1F, false, paint) // отрисовка дуги

            // отрисовка текста
            canvas.drawText(
                //"%.2f%%".format(data.sum() * 100),
                "%.2f%%".format(data.sum() / 20),
                center.x,                               // положение текста на экране
                center.y + textPaint.textSize / 4,   // положение текста на экране
                textPaint, // кисть
            )
        }
        if (taskNomber == 2) {
            if (data.isEmpty()) {
                return
            }

            // отрисовка текста
            canvas.drawText(
                //"%.2f%%".format(data.sum() * 100),
                "%.2f%%".format(data.sum() / 20),
                center.x,                               // положение текста на экране
                center.y + textPaint.textSize / 4,   // положение текста на экране
                textPaint, // кисть
            )

            var startFrom = -90F // стартовый угол
            val max = 360F
            val progressAngel = progress * 360F

            if (progressAngel > max) {
                for ((index, datum) in data.withIndex()) {
                    val angle = ((360F * datum) / (4 * datum)) // угол поворота
                    paint.color = colors.getOrNull(index)
                        ?: randomColor() // назначим каждому элементу свой цвет
                    canvas.drawArc(oval, startFrom, angle, false, paint) // отрисовка дуги
                    startFrom += angle // отступ к стартовуму углу поворота
                }
                return
            }

            var filled = 0F

            for ((index, datum) in data.withIndex()) {
                val angle = ((360F * datum) / (4 * datum)) // угол поворота
                paint.color =
                    colors.getOrNull(index) ?: randomColor() // назначим каждому элементу свой цвет
                canvas.drawArc(
                    oval,
                    startFrom,
                    progressAngel - filled,
                    false,
                    paint
                ) // отрисовка дуги
                startFrom += angle // отступ к стартовуму углу поворота
                filled += angle
                if (filled > progressAngel) return
            }
        }
        if (taskNomber == 3) {
            if (data.isEmpty()) {
                return
            }

            var startFrom = -45F // стартовый угол
            for ((index, datum) in data.withIndex()) {
                //val angle = 360F * datum // угол поворота
                val angle = ((360F * datum) / (4 * datum)) // угол поворота
                val sweepAngle = angle * progress
                paint.color =
                    colors.getOrNull(index) ?: randomColor() // назначим каждому элементу свой цвет
                //canvas.drawArc(oval, startFrom, angle, false, paint) // отрисовка дуги
                canvas.drawArc(
                    oval,
                    startFrom - sweepAngle / 2,
                    sweepAngle,
                    false,
                    paint
                ) // отрисовка дуги
                startFrom += angle // отступ к стартовуму углу поворота
            }

            // отрисовка текста
            canvas.drawText(
                //"%.2f%%".format(data.sum() * 100),
                "%.2f%%".format(data.sum() / 20),
                center.x,                               // положение текста на экране
                center.y + textPaint.textSize / 4,   // положение текста на экране
                textPaint, // кисть
            )
        }
    }


    private fun randomColor() =
        Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt()) //генерация случайного цвета


    private fun update() {
        // вычищаем предыдущую анимацию
        valueAnimator?.let {
            it.removeAllListeners()
            it.cancel()
        }
        progress = 0F

        valueAnimator = ValueAnimator.ofFloat(0F, 1F).apply {
            addUpdateListener { anim ->
                progress = anim.animatedValue as Float
                invalidate()
            }
            duration = 1900
            interpolator = LinearInterpolator()
        }.also {
            it.start()
        }
    }

}