package com.pelmenstar.benchmark

import android.graphics.PointF
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.cos
import kotlin.math.sin

@RunWith(AndroidJUnit4::class)
class ComputeLineCoordBenchmark {
    @get:Rule
    val benchmarkRule = BenchmarkRule()

    private var _middle = PointF(100f, 100f)
    private var _position = PointF()
    private var _radius = 100f
    private var _angle = 500f
    private var _tickLength = 6

    private var startX = 0f
    private var startY = 0f
    private var stopX = 0f
    private var stopY = 0f

    @Test
    fun old() {
        benchmarkRule.measureRepeated {
            getPositionByAngle(_radius, _angle, _position)
            startX = _position.x
            startY = _position.y

            getPositionByAngle(_radius - _tickLength, _angle, _position)

            stopX = _position.x
            stopY = _position.y
        }
    }

    @Test
    fun new() {
        benchmarkRule.measureRepeated {
            val radius = _radius
            val tickLength = _tickLength
            val angleRadians = Math.toRadians(_angle.toDouble())
            val endRadius = (radius - tickLength)

            val sinAngle = sin(angleRadians).toFloat()
            val cosAngle = cos(angleRadians).toFloat()

            startX = _middle.x + radius * cosAngle
            startY = _middle.y + radius * sinAngle

            stopX = _middle.x + endRadius * cosAngle
            stopY = _middle.y + endRadius * sinAngle
        }
    }

    private fun getPositionByAngle(radius: Float, angle: Float, outPoint: PointF) {
        val angleRadians = Math.toRadians(angle.toDouble())
        outPoint.x = _middle.x + radius * cos(angleRadians).toFloat()
        outPoint.y = _middle.y + radius * sin(angleRadians).toFloat()
    }
}