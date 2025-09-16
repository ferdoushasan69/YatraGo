package com.dmmeta.nolapp.presentation.screen.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import nolapp.composeapp.generated.resources.Res
import nolapp.composeapp.generated.resources.ic_add
import nolapp.composeapp.generated.resources.ic_pause
import nolapp.composeapp.generated.resources.ic_play
import org.jetbrains.compose.resources.painterResource
import kotlin.math.roundToInt

@Composable
fun PlayerBox(
    current: Int,
    count: Int,
    playing: Boolean,
    onTogglePlay: () -> Unit,
    onSeek: (Int) -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit,
    currentPage: Int,
    pageSize: Int
) {
    val density = LocalDensity.current

    var trackWidthPx by remember { mutableStateOf(0) }
    val knobWidthDp = 12.dp
    val knobWidthPx = with(density) { knobWidthDp.toPx() }

    //current -> fraction (0..1)
    val fraction = if (count <= 1) 0f else current.toFloat() / (count - 1).toFloat()

    val animX = remember { Animatable(0f) }

    LaunchedEffect(fraction, trackWidthPx) {
        if (trackWidthPx > 0) {
            animX.animateTo(
                fraction * trackWidthPx,
                animationSpec = tween(350)
            )
        }
    }

    //Drag / tap handling
    var isDragging by remember { mutableStateOf(false) }
    var dragX by remember { mutableStateOf(0f) }

    fun xToIndex(x: Float): Int {
        if (trackWidthPx <= 0 || count <= 1) return 0
        val clamped = x.coerceIn(0f, trackWidthPx.toFloat())
        val idx = ((clamped / trackWidthPx) * (count - 1)).roundToInt()
        return idx.coerceIn(0, count - 1)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onTogglePlay) {
            Icon(
                painter = painterResource(if (playing) Res.drawable.ic_play else Res.drawable.ic_pause),
                contentDescription = null
            )
        }

        //CustomSlider
        Box(
            modifier = Modifier.weight(1f)
                .height(16.dp)

                .pointerInput(count, trackWidthPx) {
                    detectTapGestures { offset ->
                        onSeek(xToIndex(offset.x))
                    }
                }
                .pointerInput(count, trackWidthPx) {
                    detectDragGestures(
                        onDragStart = {
                            isDragging = true
                            dragX = animX.value
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            dragX = (dragX + dragAmount.x)
                                .coerceIn(0f, trackWidthPx.toFloat())
                        },
                        onDragEnd = {
                            isDragging = false
                            onSeek(xToIndex(dragX))
                        },
                        onDragCancel = { isDragging = false }
                    )
                },
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().height(2.dp).background(Color(0xFFE6E6E6))
                    .onGloballyPositioned {
                        trackWidthPx = it.size.width
                    }
            )
            val x = if (isDragging) dragX else animX.value

            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(x = (x - knobWidthPx / 2f).roundToInt(), 0)
                    }
                    .width(knobWidthDp)
                    .height(4.dp)
                    .background(Color.Black, RoundedCornerShape(2.dp))
            )
        }

        Text(
            text = "$currentPage / $pageSize",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        IconButton(onClick = onNext) {
            Icon(painterResource(Res.drawable.ic_add), contentDescription = null)
        }

    }
}