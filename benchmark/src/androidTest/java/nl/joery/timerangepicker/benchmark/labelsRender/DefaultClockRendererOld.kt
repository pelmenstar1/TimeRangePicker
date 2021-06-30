package nl.joery.timerangepicker.benchmark.labelsRender

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Rect
import nl.joery.timerangepicker.TimeRangePicker
import kotlin.math.cos
import kotlin.math.sin

object DefaultClockRendererOld {
    private val LABELS_APPLE_24 = arrayOf("0", "2", "4", "6", "8", "10", "12", "14", "16", "18", "20", "22")
    private val LABELS_APPLE_12 =  arrayOf("12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")

    private val LABELS_SAMSUNG_24 = arrayOf("0", "6", "12", "18")
    private val LABELS_SAMSUNG_12 = arrayOf("12", "3", "6", "9")

    private val _minuteTickWidth = 1
    private val _hourTickWidth = 2
    private val _middle = PointF(0f, 0f)

    private val TimeRangePicker._tickLength
        get() = when (clockFace) {
            TimeRangePicker.ClockFace.APPLE -> 6
            TimeRangePicker.ClockFace.SAMSUNG -> 4
        }

    private val TimeRangePicker._tickCount: Int
        get() = when (clockFace) {
            TimeRangePicker.ClockFace.APPLE -> 48
            TimeRangePicker.ClockFace.SAMSUNG -> 120
        }

    private val _labelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
    }

    private val _drawLabelsBounds = Rect()
    private val _position = PointF()

    fun drawLabels(canvas: Canvas, picker: TimeRangePicker, radius: Float) {
        val labels: Array<String>

        when(picker.clockFace) {
            TimeRangePicker.ClockFace.APPLE -> {
                labels =  if (picker.hourFormat == TimeRangePicker.HourFormat.FORMAT_24) {
                    LABELS_APPLE_24
                } else {
                    LABELS_APPLE_12
                }
            }
            TimeRangePicker.ClockFace.SAMSUNG -> {
                labels =  if (picker.hourFormat == TimeRangePicker.HourFormat.FORMAT_24) {
                    LABELS_SAMSUNG_24
                } else {
                    LABELS_SAMSUNG_12
                }
            }
        }

        val bounds = _drawLabelsBounds
        val tickLength = picker._tickLength.toFloat()
        val anglePerLabel = 360f / labels.size
        val position = _position

        for (i in labels.indices) {
            val label = labels[i]
            val angle = anglePerLabel * i - 90f

            _labelPaint.getTextBounds(label, 0, label.length, bounds)
            val offset = when (picker.clockFace) {
                TimeRangePicker.ClockFace.APPLE -> tickLength * 2 + bounds.height()
                TimeRangePicker.ClockFace.SAMSUNG -> {
                    (if (angle == 0f || angle == 180f) bounds.width() else bounds.height()).toFloat() / 2
                }
            }

            val radiusNoOffset = radius - offset

            getPositionByAngle(radiusNoOffset, angle, position)

            canvas.drawText(
                label,
                position.x,
                position.y + bounds.height() / 2f,
                _labelPaint
            )
        }
    }

    private fun getPositionByAngle(radius: Float, angle: Float, outPoint: PointF) {
        val angleRadians = Math.toRadians(angle.toDouble())
        outPoint.x = _middle.x + radius * cos(angleRadians).toFloat()
        outPoint.y = _middle.y + radius * sin(angleRadians).toFloat()
    }
}