package nl.joery.timerangepicker.benchmark.labelsRender

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import nl.joery.timerangepicker.TimeRangePicker
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LabelsClockRendererBenchmark {
    @get:Rule
    val benchmarkRule = BenchmarkRule()

    private val bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)
    private val canvas = Canvas(bitmap)
    private val context = InstrumentationRegistry.getInstrumentation().context
    private val picker = TimeRangePicker(context).apply {
        clockFace = TimeRangePicker.ClockFace.APPLE
    }

    @Test
    fun old() {
        benchmarkRenderMethod(DefaultClockRendererOld::drawLabels)
    }

    @Test
    fun new() {
        benchmarkRenderMethod(DefaultClockRendererNew::drawLabels)
    }

    private inline fun benchmarkRenderMethod(crossinline method: (Canvas, TimeRangePicker, Float) -> Unit) {
        benchmarkRule.measureRepeated {
            method(canvas, picker, 200f)
        }
    }
}