package nl.joery.timerangepicker.benchmark

import android.graphics.Color
import android.graphics.Paint
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PaintAlphaBenchmark {
    @get:Rule
    val benchmarkRule = BenchmarkRule()

    private val paint = Paint().apply {
        color = Color.BLACK
    }

    @Test
    fun set_through_setAlpha() {
        benchmarkRule.measureRepeated {
            paint.alpha = 144
        }
    }

    @Test
    fun set_through_setColor() {
        val paintColor = paint.color

        benchmarkRule.measureRepeated {
            paint.color = (paintColor and 0x00ffffff) or 144
        }
    }
}