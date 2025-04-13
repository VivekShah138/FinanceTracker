//package com.example.financetracker.main_page_feature.view_records.transactions.presentation.components
//
//import android.app.DatePickerDialog
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.platform.LocalContext
//import com.example.financetracker.main_page_feature.view_records.transactions.presentation.ViewTransactionsStates
//import java.time.LocalDate
//
//@Composable
//fun CustomDateRangeDialog(
//    onDismiss: () -> Unit,
//    onSubmit: (Long, Long) -> Unit,  // Callback with the selected dates in milliseconds
//    isDialogOpen: Boolean,
//    state: ViewTransactionsStates
//) {
//    if (isDialogOpen) {
//
//        // Function to open the date picker
//        fun openDatePicker(isFromDate: Boolean) {
//            val currentDate = LocalDate.now()
//            val datePickerDialog = DatePickerDialog(
//                LocalContext.current,
//                { _, year, month, dayOfMonth ->
//                    val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)  // Month is 0-based
//                    if (isFromDate) {
//                        fromDate = selectedDate
//                    } else {
//                        toDate = selectedDate
//                    }
//                },
//                currentDate.year, currentDate.monthValue - 1, currentDate.dayOfMonth
//            )
//            datePickerDialog.show()
//        }
//
//        // Dialog UI
//        AlertDialog(
//            onDismissRequest = onDismiss,
//            title = { Text("Select Custom Date Range") },
//            text = {
//                Column {
//                    Text("From Date: ${fromDate?.format(DateTimeFormatter.ISO_LOCAL_DATE)}")
//                    Button(onClick = { openDatePicker(true) }) {
//                        Text("Select From Date")
//                    }
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Text("To Date: ${toDate?.format(DateTimeFormatter.ISO_LOCAL_DATE)}")
//                    Button(onClick = { openDatePicker(false) }) {
//                        Text("Select To Date")
//                    }
//                }
//            },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        // Make sure both dates are selected before submitting
//                        if (fromDate != null && toDate != null) {
//                            onSubmit(fromDate!!.toEpochDay() * 24 * 60 * 60 * 1000, toDate!!.toEpochDay() * 24 * 60 * 60 * 1000)
//                        }
//                        onDismiss()
//                    }
//                ) {
//                    Text("Submit")
//                }
//            },
//            dismissButton = {
//                Button(onClick = onDismiss) {
//                    Text("Cancel")
//                }
//            }
//        )
//    }
//}
