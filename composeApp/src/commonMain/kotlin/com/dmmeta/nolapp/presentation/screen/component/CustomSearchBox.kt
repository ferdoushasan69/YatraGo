package com.dmmeta.nolapp.presentation.screen.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dmmeta.nolapp.utils.wideBreakPoint
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import nolapp.composeapp.generated.resources.Res
import nolapp.composeapp.generated.resources.calender_date_picker
import nolapp.composeapp.generated.resources.search
import nolapp.composeapp.generated.resources.user
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun CustomSearchBox(
    onSearch: () -> Unit,
) {
    val instant = Clock.System.now()
    val localDate: LocalDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val dates = "${localDate.dayOfMonth}${localDate.monthNumber}"
    var showDialog by remember { mutableStateOf(false) }

    // Store the result
    var selectedRange by remember { mutableStateOf<Pair<Long?, Long?>>(dates.toLong() to dates.toLong()) }

    val selectedDate = "${selectedRange.first?.toDayMonth()}-${selectedRange.second?.toDayMonth()}"
    if (showDialog) {
        val dateRangePickerState = rememberDateRangePickerState(
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    val today = Clock.System.now().toEpochMilliseconds()
                    return utcTimeMillis >= today
                }
            }
        )

        DatePickerDialog(

            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    selectedRange = dateRangePickerState.selectedStartDateMillis to
                            dateRangePickerState.selectedEndDateMillis
                    showDialog = false
                }) {
                    Text("OK")
                }
            },

            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DateRangePicker(
                title = {
                    Text(
                        "날짜 선택",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                state = dateRangePickerState,
                modifier = Modifier.height(500.dp)
            )
        }
    }
    BoxWithConstraints {
        if (maxWidth >= wideBreakPoint) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(end = 10.dp)
                    .clip(RoundedCornerShape(8.dp)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBox(
                    modifier = Modifier.weight(1f),
                    onSearch = {

                    },
                    isLandScape = true
                )
                DateAndCountBox(
                    modifier = Modifier.weight(1f),
                    isLandScape = true,
                    onDateCLick = {
                        showDialog = true
                    },
                    pickDate = selectedDate
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
            ) {
                SearchBox(
                    modifier = Modifier,
                    onSearch = {

                    },
                    isLandScape = false
                )
                HorizontalDivider()
                DateAndCountBox(
                    modifier = Modifier.fillMaxWidth(),
                    isLandScape = false,
                    onDateCLick = {
                        showDialog = true
                    },
                    pickDate = selectedDate

                )
            }
        }
    }
}

@Composable
private fun SearchBox(
    modifier: Modifier = Modifier,
    onSearch: () -> Unit,
    isLandScape: Boolean
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(12.dp)
            .showBorder(isLandScape)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onSearch()
            }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(Res.drawable.search),
            contentDescription = null,
            modifier = Modifier.size(26.dp)
        )
        Text("도시, 명소, 숙소명으로 찾아보세요")

    }

}

@Composable
fun DateAndCountBox(
    modifier: Modifier = Modifier,
    isLandScape: Boolean,
    onDateCLick: () -> Unit,
    pickDate: String
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .showBorder(isLandScape),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        DateBoxItem(
            modifier = Modifier.weight(1f),
            icon = painterResource(Res.drawable.calender_date_picker),
            title = "$pickDate · 1박",
            onCLick = {
                onDateCLick()
            }
        )
        VerticalDivider()
        DateBoxItem(
            modifier = Modifier.weight(1f),
            icon = painterResource(Res.drawable.user),
            title = "객실 1, 성인 2, 아동 0",
            onCLick = {

            }
        )
    }
}


@Composable
fun Modifier.showBorder(isLandScape: Boolean): Modifier {
    return if (isLandScape) {
        border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
            shape = RoundedCornerShape(8.dp)
        )
    } else {
        return Modifier
    }

}

@Composable
fun DateBoxItem(
    icon: Painter, title: String,
    onCLick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxHeight()
            .clickable(onClick = onCLick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            icon, contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(text = title, style = MaterialTheme.typography.titleMedium)
    }
}

@OptIn(ExperimentalTime::class)
fun Long.toDayMonth(): String {

    val instant = Instant.fromEpochMilliseconds(this)
    val localDate: LocalDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val month =
        if (localDate.monthNumber < 10) "0${localDate.monthNumber}" else "${localDate.monthNumber}"
    val day =
        if (localDate.dayOfMonth < 10) "0${localDate.dayOfMonth}" else "${localDate.dayOfMonth}"

    return "$month/$day"
}