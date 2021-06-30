package nl.joery.timerangepicker.benchmark.labelsRender

import android.graphics.*
import nl.joery.timerangepicker.TimeRangePicker

object DefaultClockRendererNew {
    private val LABELS_APPLE_24 = arrayOf("0", "2", "4", "6", "8", "10", "12", "14", "16", "18", "20", "22")
    private val LABELS_APPLE_12 =  arrayOf("12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")

    private val LABELS_SAMSUNG_24 = arrayOf("0", "6", "12", "18")
    private val LABELS_SAMSUNG_12 = arrayOf("12", "3", "6", "9")

    private val SIN_TABLE_LABELS_APPLE = floatArrayOf(
        -1f,
        -0.866025f,
        -0.5f,
        0f,
        0.5f,
        0.866025f,
        1f,
        0.866025f,
        0.5f,
        0f,
        -0.5f,
        -0.866025f
    )

    private val COS_TABLE_LABELS_APPLE = floatArrayOf(
        0f,
        0.5f,
        0.866025f,
        1f,
        0.866025f,
        0.5f,
        0f,
        -0.5f,
        -0.866025f,
        -1f,
        -0.866025f,
        -0.5f
    )

    private val SIN_TABLE_LABELS_SAMSUNG = floatArrayOf(
        -1f,
        0f,
        1f,
        0f
    )

    private val COS_TABLE_LABELS_SAMSUNG = floatArrayOf(
        0f,
        1f,
        0f,
        -1f
    )

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

    fun drawLabels(canvas: Canvas, picker: TimeRangePicker, radius: Float) {
        val labels: Array<String>
        val sinTable: FloatArray
        val cosTable: FloatArray

        when(picker.clockFace) {
            TimeRangePicker.ClockFace.APPLE -> {
                labels =  if (picker.hourFormat == TimeRangePicker.HourFormat.FORMAT_24) {
                    LABELS_APPLE_24
                } else {
                    LABELS_APPLE_12
                }
                sinTable = SIN_TABLE_LABELS_APPLE
                cosTable = COS_TABLE_LABELS_APPLE
            }
            TimeRangePicker.ClockFace.SAMSUNG -> {
                labels =  if (picker.hourFormat == TimeRangePicker.HourFormat.FORMAT_24) {
                    LABELS_SAMSUNG_24
                } else {
                    LABELS_SAMSUNG_12
                }

                sinTable = SIN_TABLE_LABELS_SAMSUNG
                cosTable = COS_TABLE_LABELS_SAMSUNG
            }
        }

        val bounds = _drawLabelsBounds
        val tickLength = picker._tickLength.toFloat()
        val anglePerLabel = 360f / labels.size

        for (i in labels.indices) {
            val label = labels[i]

            _labelPaint.getTextBounds(label, 0, label.length, bounds)
            val offset = when (picker.clockFace) {
                TimeRangePicker.ClockFace.APPLE -> tickLength * 2 + bounds.height()
                TimeRangePicker.ClockFace.SAMSUNG -> {
                    val angle = anglePerLabel * i - 90f
                    (if (angle == 0f || angle == 180f) bounds.width() else bounds.height()).toFloat() / 2
                }
            }

            val radiusNoOffset = radius - offset

            val posX = _middle.x + radiusNoOffset * cosTable[i]
            val posY = _middle.y + radiusNoOffset * sinTable[i]

            canvas.drawText(
                label,
                posX,
                posY + bounds.height() / 2f,
                _labelPaint
            )
        }
    }
}