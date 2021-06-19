package nl.joery.timerangepicker.benchmark

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

const val R2D = 180f / PI.toFloat()

@RunWith(AndroidJUnit4::class)
class DiffBetweenAnglesBenchmark {
    @get:Rule
    val benchmarkRule = BenchmarkRule()

    private var a1 = 100f
    private var a2 = 200f
    private var result = 0f

    @Test
    fun old() {
        benchmarkRule.measureRepeated {
            val angle1 = Math.toRadians(a1.toDouble())
            val angle2 = Math.toRadians(a2.toDouble())

            result = atan2(
                    cos(angle1).toFloat() * sin(angle2).toFloat() - sin(angle1).toFloat() * cos(angle2).toFloat(),
                    cos(angle1).toFloat() * cos(angle2).toFloat() + sin(angle1).toFloat() * sin(angle2).toFloat()
            ) * R2D
        }
    }

    @Test
    fun new() {
        benchmarkRule.measureRepeated {
            val angle1 = Math.toRadians(a1.toDouble())
            val angle2 = Math.toRadians(a2.toDouble())

            val sinAngle1 = sin(angle1).toFloat()
            val cosAngle1 = cos(angle1).toFloat()

            val sinAngle2 = sin(angle2).toFloat()
            val cosAngle2 = cos(angle2).toFloat()

            result = atan2(
                cosAngle1 * sinAngle2 - sinAngle1 * cosAngle2,
                cosAngle1 * cosAngle2 + sinAngle1 * sinAngle2
            ) * R2D
        }
    }
}