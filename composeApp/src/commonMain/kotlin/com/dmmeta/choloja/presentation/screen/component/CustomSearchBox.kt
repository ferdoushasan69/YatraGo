package com.dmmeta.choloja.presentation.screen.component

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dmmeta.choloja.presentation.theme.PrimaryColor
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun CustomSearchBox(
    onSearch: (String) -> Unit,
    isSearch: Boolean,
    modifier: Modifier = Modifier,
    categoryName: String? = null,
    onValueChange: (String) -> Unit = {},
    value: String? = null
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

        DateRangePickerDialog(
            onDismiss = { showDialog = false },
            onConfirm = { startMillis, endMillis ->
                selectedRange = startMillis to endMillis
                showDialog = false
            }
        )
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
                onSearch(it)
            },
            isSearch = isSearch,
            categoryName = categoryName,
            onValueChange = onValueChange,
            value = value ?: "",
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
                    imageVector = Icons.Default.Close,
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
                    imageVector = Icons.Default.Remove,
                    contentDescription = null
                )
            }
            Text(text = countValue, style = MaterialTheme.typography.titleLarge)
            IconButton(onClick = onIncrease) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }

    }
}

@Composable
private fun SearchBox(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    isSearch: Boolean,
    categoryName: String? = null,
    onValueChange: (String) -> Unit,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            onValueChange = onValueChange,
            singleLine = true,
            value = value,
            prefix = {
                Row(modifier = Modifier) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )
                    if (!isSearch) {
                        Text(
                            text = categoryName ?: "",
                            style = MaterialTheme.typography.titleMedium,
                            color = PrimaryColor,
                            modifier = Modifier.background(
                                MaterialTheme.colorScheme.surfaceDim.copy(.5f),
                                shape = RoundedCornerShape(8.dp)
                            ).clip(RoundedCornerShape(8.dp))
                        )
                    }
                }

            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch(value) }
            ),
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            ),

            placeholder = {
                Text("도시, 명소, 숙소명으로 찾아보세요", overflow = TextOverflow.Ellipsis)
            },
            modifier = modifier
                .height(56.dp).isSearch(
                    isSearch,
                    onClick = {
                        if (isSearch) {
                            onSearch(value)
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
            icon = Icons.Default.EditCalendar,
            title = pickDate,
            onCLick = {
                onDatePick()
            },
        )
        DateBoxItem(
            modifier = Modifier,
            icon = Icons.Default.Person,
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
    icon: ImageVector, title: String,
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