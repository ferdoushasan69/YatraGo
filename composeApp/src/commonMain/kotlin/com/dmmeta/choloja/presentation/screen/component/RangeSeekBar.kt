package com.dmmeta.choloja.presentation.screen.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * A custom dual-thumb range seek bar implemented using Canvas, designed to work in commonMain.
 */
@Composable
fun RangeSeekBar(
    value: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    trackHeight: Dp = 4.dp,
    thumbRadius: Dp = 10.dp,
    gapBetweenThumbs: Float = 0f,
    colors: RangeSeekBarColors = RangeSeekBarDefaults.colors(),
    showValueLabels: Boolean = false,
    valueFormatter: (Float) -> String = { it.roundToInt().toString() }
) {
    val coercedStart = value.start.coerceIn(valueRange.start, valueRange.endInclusive)
    val coercedEnd = value.endInclusive.coerceIn(valueRange.start, valueRange.endInclusive)

    var activeThumb by remember { mutableStateOf(ActiveThumb.None) }

    // Keep an internal state while dragging to prevent recomposition jank from parent state updates
    var currentStart by remember(valueRange.start, valueRange.endInclusive) { mutableStateOf(coercedStart) }
    var currentEnd by remember(valueRange.start, valueRange.endInclusive) { mutableStateOf(coercedEnd) }

    // Sync internal state from external value when not dragging
    LaunchedEffect(coercedStart, coercedEnd) {
        if (activeThumb == ActiveThumb.None) {
            currentStart = coercedStart
            currentEnd = coercedEnd
        }
    }

    val onValueChangeState = rememberUpdatedState(onValueChange)

    val trackTotalHeight = max(thumbRadius * 2, trackHeight) + if (showValueLabels) 22.dp else 0.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(trackTotalHeight)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        val startPxState: MutableState<Float> = remember { mutableStateOf(0f) }
        val endPxState: MutableState<Float> = remember { mutableStateOf(0f) }
        val widthPxState: MutableState<Float> = remember { mutableStateOf(0f) }

        val rangeSpan = valueRange.endInclusive - valueRange.start
        val stepSize = remember(steps, rangeSpan) {
            if (steps <= 0) 0f else rangeSpan / (steps + 1)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(trackHeight)
                .background(colors.inactiveTrackColor, RoundedCornerShape(percent = 50))
        )

        Canvas(
            modifier = Modifier
                .matchParentSize()
                // Avoid recreating gesture detector on every value change
                .pointerInput(valueRange.start, valueRange.endInclusive, steps, gapBetweenThumbs) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            val startX = startPxState.value
                            val endX = endPxState.value
                            activeThumb = if (abs(offset.x - startX) <= abs(offset.x - endX)) ActiveThumb.Start else ActiveThumb.End
                        },
                        onDragEnd = {
                            // Snap to step only at the end to reduce during-drag jitter
                            if (steps > 0) {
                                val rangeSpanLocal = valueRange.endInclusive - valueRange.start
                                val stepSizeLocal = rangeSpanLocal / (steps + 1)
                                val snappedStart = snapToStep(currentStart, valueRange.start, stepSizeLocal)
                                val snappedEnd = snapToStep(currentEnd, valueRange.start, stepSizeLocal)
                                currentStart = snappedStart
                                currentEnd = snappedEnd
                                onValueChangeState.value(snappedStart..snappedEnd)
                            }
                            activeThumb = ActiveThumb.None
                        },
                        onDragCancel = {
                            activeThumb = ActiveThumb.None
                        }
                    ) { change, dragAmount ->
                        change.consume()

                        val width = widthPxState.value
                        if (width <= 0f) return@detectDragGestures

                        val valuePerPixel = rangeSpan / width
                        val deltaValue = dragAmount.x * valuePerPixel

                        var newStart = currentStart
                        var newEnd = currentEnd

                        when (activeThumb) {
                            ActiveThumb.Start -> {
                                newStart = (newStart + deltaValue).coerceIn(valueRange.start, newEnd - gapBetweenThumbs)
                            }
                            ActiveThumb.End -> {
                                newEnd = (newEnd + deltaValue).coerceIn(newStart + gapBetweenThumbs, valueRange.endInclusive)
                            }
                            ActiveThumb.None -> Unit
                        }

                        if (newStart != currentStart || newEnd != currentEnd) {
                            currentStart = newStart
                            currentEnd = newEnd
                            onValueChangeState.value(newStart..newEnd)
                        }
                    }
                }
        ) {
            val width = size.width
            widthPxState.value = width

            val startX = ((currentStart - valueRange.start) / rangeSpan) * width
            val endX = ((currentEnd - valueRange.start) / rangeSpan) * width
            startPxState.value = startX
            endPxState.value = endX

            // Active range track
            drawRoundRect(
                color = colors.activeTrackColor,
                topLeft = Offset(x = startX, y = center.y - trackHeight.toPx() / 2f),
                size = androidx.compose.ui.geometry.Size(
                    max(0f, endX - startX),
                    trackHeight.toPx()
                ),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(trackHeight.toPx() / 2f)
            )

            // Thumbs
            drawCircle(
                color = colors.thumbBorderColor,
                radius = thumbRadius.toPx() + 2.dp.toPx(),
                center = Offset(startX, center.y)
            )
            drawCircle(
                color = colors.thumbColor,
                radius = thumbRadius.toPx(),
                center = Offset(startX, center.y)
            )

            drawCircle(
                color = colors.thumbBorderColor,
                radius = thumbRadius.toPx() + 2.dp.toPx(),
                center = Offset(endX, center.y)
            )
            drawCircle(
                color = colors.thumbColor,
                radius = thumbRadius.toPx(),
                center = Offset(endX, center.y)
            )
        }

    }
}

private enum class ActiveThumb { Start, End, None }

private fun snapToStep(value: Float, min: Float, stepSize: Float): Float {
    val stepsFromMin = ((value - min) / stepSize).roundToInt()
    return min + stepsFromMin * stepSize
}

class RangeSeekBarColors(
    val activeTrackColor: Color,
    val inactiveTrackColor: Color,
    val thumbColor: Color,
    val thumbBorderColor: Color,
    val labelColor: Color
)

object RangeSeekBarDefaults {
    @Composable
    fun colors(
        activeTrackColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.8f),
        inactiveTrackColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f),
        thumbColor: Color = MaterialTheme.colorScheme.onBackground,
        thumbBorderColor: Color = MaterialTheme.colorScheme.outlineVariant,
        labelColor: Color = MaterialTheme.colorScheme.onSurface
    ): RangeSeekBarColors = RangeSeekBarColors(
        activeTrackColor = activeTrackColor,
        inactiveTrackColor = inactiveTrackColor,
        thumbColor = thumbColor,
        thumbBorderColor = thumbBorderColor,
        labelColor = labelColor
    )
}