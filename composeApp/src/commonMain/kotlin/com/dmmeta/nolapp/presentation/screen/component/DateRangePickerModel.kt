package com.dmmeta.nolapp.presentation.screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun CustomDateRangePicker(
    allowPastDates: Boolean = false, // ✅ Proper parameter
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.sizeIn(maxWidth = 450.dp)) {
        Surface(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                Text(
                    text = "DateRangePicker with Selectable Dates",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (allowPastDates) {
                        "Allows all dates."
                    } else {
                        "Allows today and future dates."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                val selectableDates = if (!allowPastDates) {
                    object : androidx.compose.material3.SelectableDates {
                        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                            // Only allow today & future
                            val today = Clock.System.now().toEpochMilliseconds()
                            return utcTimeMillis >= today
                        }

                        override fun isSelectableYear(year: Int): Boolean {
                            val currentYear = 2025
                            return year >= currentYear
                        }
                    }
                } else {
                    DatePickerDefaults.AllDates
                }

                val dateRangePickerState = rememberDateRangePickerState(
                    selectableDates = selectableDates
                )

                DateRangePicker(
                    state = dateRangePickerState,
                    modifier = Modifier
                        .sizeIn(maxWidth = 450.dp)
                        .height(450.dp)
                )
            }
        }
    }
}
