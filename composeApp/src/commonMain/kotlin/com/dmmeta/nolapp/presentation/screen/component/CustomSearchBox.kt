package com.dmmeta.nolapp.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dmmeta.nolapp.presentation.theme.PrimaryColor
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import nolapp.composeapp.generated.resources.Res
import nolapp.composeapp.generated.resources.calender_date_picker
import nolapp.composeapp.generated.resources.close
import nolapp.composeapp.generated.resources.ic_add
import nolapp.composeapp.generated.resources.ic_remove
import nolapp.composeapp.generated.resources.ic_user
import nolapp.composeapp.generated.resources.search
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun CustomSearchBox(
    onSearch: () -> Unit,
    isSearch: Boolean = false,
    modifier: Modifier = Modifier,
    categoryName: String? = null
) {
    val now = Clock.System.now().toEpochMilliseconds()
    val onDaysMils = 1.days.inWholeMilliseconds
    var showDialog by remember { mutableStateOf(false) }
    var showUserDialogue by remember { mutableStateOf(false) }
    var roomNumb by rememberSaveable { mutableStateOf(1) }
    var userNumb by rememberSaveable { mutableStateOf(1) }
    var fMember by rememberSaveable { mutableStateOf(1) }

    var selectedRange by rememberSaveable {
        mutableStateOf<Pair<Long?, Long?>>(now to now.plus(onDaysMils))
    }

    val selectedDate = "${selectedRange.first?.toDayMonth()}-${selectedRange.second?.toDayMonth()}"
    val nights = getNight(
        startMils = selectedRange.first ?: 0,
        endMils = selectedRange.second ?: 0
    )
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
                TextButton(
                    onClick = {
                        selectedRange = dateRangePickerState.selectedStartDateMillis to
                                dateRangePickerState.selectedEndDateMillis
                        showDialog = false
                    },
                    enabled = dateRangePickerState.selectedStartDateMillis != null &&
                            dateRangePickerState.selectedEndDateMillis != null
                ) {
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
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )
                },
                state = dateRangePickerState,
                modifier = Modifier.height(500.dp)
            )
        }
    }

    if (showUserDialogue) {
        Dialog(
            onDismissRequest = { showUserDialogue = false },
            content = {
                DialogContent(
                    onDismiss = { showUserDialogue = false },
                    onClick = { roomNum, userN, fMemb ->
                        roomNumb = roomNum
                        userNumb = userN
                        fMember = fMemb
                        showUserDialogue = false
                    },
                    roomNumInit = roomNumb,
                    userNInit = userNumb,
                    fMembInit = fMember
                )
            }
        )
    }
//        if (maxWidth >= wideBreakPoint) {
//            Row(
//                modifier = Modifier.fillMaxWidth().padding(end = 10.dp)
//                    .clip(RoundedCornerShape(8.dp)),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                SearchBox(
//                    modifier = modifier,
//                    onSearch = {
//
//                    },
//                    isLandScape = true,
//                    isSearch = isSearch,
//                )
//                DateAndCountBox(
//                    modifier = Modifier,
//                    isLandScape = true,
//                    onDatePick = {
//                        showDialog = true
//                    },
//                    pickDate = "$selectedDate · ${nights}박",
//                    onPersonPick = {
//                        showUserDialogue = true
//                    },
//                    personValue = "객실 $roomNumb, 성인 $userNumb, 아동 $fMember",
//                )
//            }
//        } else {
    Column(
        modifier = modifier.fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp)),
    ) {
        SearchBox(
            modifier = modifier,
            onSearch = {
                onSearch()
            },
            isLandScape = false,
            isSearch = isSearch,
            categoryName = categoryName

        )
        HorizontalDivider()
        DateAndCountBox(
            modifier = Modifier,
            isLandScape = false,
            onDatePick = {
                showDialog = true
            },
            pickDate = "$selectedDate · ${nights}박",
            onPersonPick = {
                showUserDialogue = true
            },
            personValue = "객실 $roomNumb, 성인 $userNumb, 아동 $fMember"
        )
    }
//        }
}

@Composable
fun DialogContent(
    onDismiss: () -> Unit,
    onClick: (Int, Int, Int) -> Unit,
    roomNumInit: Int,
    userNInit: Int,
    fMembInit: Int,
) {
    var count성인 by rememberSaveable { mutableStateOf(userNInit) }
    var count아동 by rememberSaveable { mutableStateOf(fMembInit) }
    var roomNum by rememberSaveable { mutableStateOf(roomNumInit) }
    Column(
        modifier = Modifier.widthIn(min = 500.dp, max = 600.dp)
            .padding(16.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                onDismiss()
            }) {
                Icon(
                    painterResource(Res.drawable.close),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = "인원수 선택",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier.fillMaxWidth().border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(16.dp)
            ).clip(RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {

                DialogItem(
                    itemName = "객실",
                    onIncrease = {
                        if (roomNum < 10) {
                            roomNum++
                        }
                    },
                    onDecrease = {
                        if (roomNum <= 1) {
                            return@DialogItem
                        }
                        roomNum--
                    },
                    countValue = roomNum.toString()
                )
                HorizontalDivider()

                DialogItem(
                    itemName = "성인",
                    onIncrease = {
                        if (count성인 < 10) {
                            count성인++
                        }

                    },
                    onDecrease = {
                        if (count성인 <= 1) {
                            return@DialogItem
                        }
                        count성인--;
                    },
                    countValue = count성인.toString()
                )
                HorizontalDivider()

                DialogItem(
                    itemName = "아동",
                    onIncrease = {
                        if (count아동 < 10) {
                            count아동++
                        }
                    },
                    onDecrease = {
                        if (count아동 <= 1) {
                            return@DialogItem
                        }
                        count아동--;
                    },
                    countValue = count아동.toString()
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                onClick(roomNum, count성인, count아동)
                onDismiss()
            }, colors = ButtonDefaults
                .buttonColors(containerColor = PrimaryColor, contentColor = Color.White),
            modifier = Modifier.height(46.dp).fillMaxWidth()

        ) {
            Text("객실 $roomNum, 성인 $count성인, 아동 $count아동")
        }
    }
}

@Composable
private fun DialogItem(
    modifier: Modifier = Modifier,
    itemName: String,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    countValue: String
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = itemName, style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDecrease) {
                Icon(
                    painterResource(Res.drawable.ic_remove),
                    contentDescription = null
                )
            }
            Text(text = countValue, style = MaterialTheme.typography.titleLarge)
            IconButton(onClick = onIncrease) {
                Icon(
                    painterResource(Res.drawable.ic_add),
                    contentDescription = null
                )
            }
        }

    }
}

@Composable
private fun SearchBox(
    modifier: Modifier = Modifier,
    onSearch: () -> Unit,
    isLandScape: Boolean,
    isSearch: Boolean,
    categoryName: String? = null
) {
    var text by remember { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxWidth()
            .showBorder(isLandScape)
            .clip(RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            onValueChange = {
                text = it
            },
            singleLine = true,
            value = text,
            prefix = {
                Row(modifier = Modifier) {
                    Icon(
                        painterResource(Res.drawable.search),
                        contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )
                    if (isSearch && categoryName?.isNotEmpty() == true) {
                        Text(
                            text = categoryName ?: "",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.background(
                                MaterialTheme.colorScheme.outlineVariant
                            ).padding(8.dp)
                        )
                    }
                }

            },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            ),
            placeholder = {
                Text("도시, 명소, 숙소명으로 찾아보세요")
            },
            modifier = modifier.isSearch(
                isSearch,
                onClick = {
                    if (isSearch) {
                        onSearch()
                    }
                }
            ),
        )

    }

}

@Composable
fun DateAndCountBox(
    modifier: Modifier = Modifier,
    isLandScape: Boolean,
    onDatePick: () -> Unit,
    pickDate: String,
    onPersonPick: () -> Unit,
    personValue: String
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .showBorder(isLandScape),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        DateBoxItem(
            modifier = Modifier,
            icon = painterResource(Res.drawable.calender_date_picker),
            title = pickDate,
            onCLick = {
                onDatePick()
            }
        )
        DateBoxItem(
            modifier = Modifier,
            icon = painterResource(Res.drawable.ic_user),
            title = personValue,
            onCLick = {
                onPersonPick()
            }
        )
    }
}


@Composable
private fun Modifier.showBorder(isLandScape: Boolean): Modifier {
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
            .clickable(onClick = onCLick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Icon(
            icon, contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(5.dp))
        Text(text = title, style = MaterialTheme.typography.titleSmall)
    }
}

fun getNight(startMils: Long, endMils: Long): Int {
    val diff = (endMils - startMils).milliseconds
    return diff.toInt(DurationUnit.DAYS)

}

@OptIn(ExperimentalTime::class)
fun Long.toDayMonth(): String {

    val instant = Instant.fromEpochMilliseconds(this)
    val localDate: LocalDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val month =
        if (localDate.month.number < 10) "0${localDate.month.number}" else "${localDate.month.number}"
    val day =
        if (localDate.day < 10) "0${localDate.day}" else "${localDate.day}"

    return "$month/$day"
}