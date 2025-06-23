package com.example.financetracker.main_page_feature.view_records.use_cases

import android.util.Log
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model.Transactions
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionLocalRepository
import com.example.financetracker.main_page_feature.view_records.transactions.utils.DurationFilter
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionFilter
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionOrder
import com.example.financetracker.main_page_feature.view_records.transactions.utils.TransactionTypeFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class GetAllTransactionsFilters(
    private val transactionLocalRepository: TransactionLocalRepository
) {
    suspend operator fun invoke(uid: String,filters: List<TransactionFilter>): Flow<List<Transactions>>{
        return transactionLocalRepository.getAllTransactions(uid).map { transactions ->
            var filteredTransactions = transactions

            // Apply each filter
            filters.forEach { filter ->
                when (filter) {
                    is TransactionFilter.TransactionType -> {
                        filteredTransactions = when (filter.type) {
                            is TransactionTypeFilter.Income -> filteredTransactions.filter { it.transactionType.equals("Income",ignoreCase = true) }
                            is TransactionTypeFilter.Expense -> filteredTransactions.filter { it.transactionType.equals("Expense",ignoreCase = true) }
                            is TransactionTypeFilter.Both -> filteredTransactions
                        }
                    }

                    is TransactionFilter.Category -> {
                        if (filter.selectedCategories.isNotEmpty()) {
                            filteredTransactions =
                                filteredTransactions.filter {
                                    it.category in filter.selectedCategories.map { category ->
                                        category.name
                                    }
                                }
                        }
                    }

                    is TransactionFilter.Duration -> {
                        filteredTransactions = when (filter.durationFilter) {
                            is DurationFilter.Today -> {
                                // Filter for today (you can implement exact logic)
                                val startOfToday = getStartOfTodayInMillis()

                                val endOfToday = getEndOfTodayInMillis()

                                filteredTransactions.filter {
                                    it.dateTime in startOfToday..endOfToday
                                }
                            }

                            is DurationFilter.ThisMonth -> {
                                val startOfMonthMillis = getStartOfMonthInMillis()
                                val endOfMonthMillis = getEndOfTodayInMillis()

                                filteredTransactions.filter {
                                    it.dateTime in startOfMonthMillis..endOfMonthMillis
                                }
                            }


                            is DurationFilter.LastMonth -> {
                                val startOfLastMonthMillis = getStartOfLastMonthInMillis()
                                val endOfLastMonthMillis = getEndOfLastMonthInMillis()

                                filteredTransactions.filter {
                                    it.dateTime in startOfLastMonthMillis..endOfLastMonthMillis
                                }
                            }

                            is DurationFilter.Last3Months -> {
                                val startOfLast3MonthMillis = getStartOfLast3MonthsInMillis()
                                val endOfLast3MonthMillis = System.currentTimeMillis()

                                filteredTransactions.filter {
                                    it.dateTime in startOfLast3MonthMillis..endOfLast3MonthMillis
                                }
                            }

                            is DurationFilter.CustomRange -> {
                                val startOfFromDate = getStartOfDayInMillis(filter.durationFilter.from)
                                val endOfToDate = getEndOfDayInMillis(filter.durationFilter.to)

                                filteredTransactions.filter { it.dateTime in startOfFromDate..endOfToDate }
                            }
                        }
                    }

                    is TransactionFilter.Order -> {
                        filteredTransactions = when (filter.order) {
                            is TransactionOrder.Ascending -> filteredTransactions.sortedBy { it.dateTime }
                            is TransactionOrder.Descending -> filteredTransactions.sortedByDescending { it.dateTime }
                        }
                    }
                }
            }

            filteredTransactions
        }
    }


    private fun getStartOfTodayInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun getEndOfTodayInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }

    private fun getStartOfMonthInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1) // Set to first day of current month
        Log.d("ViewTransactionsViewModel","This Month First Day ${Calendar.DAY_OF_MONTH}")
        calendar.set(Calendar.HOUR_OF_DAY, 0)  // Set to 00:00:00
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val dateThisMonth = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.timeInMillis)
        Log.d("ViewTransactionsViewModel","This Month long${calendar.timeInMillis}")
        Log.d("ViewTransactionsViewModel","This Month date format $dateThisMonth")
        return calendar.timeInMillis
    }

    private fun getStartOfLastMonthInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)  // Move to the previous month
        calendar.set(Calendar.DAY_OF_MONTH, 1) // Set to the first day of the last month
        calendar.set(Calendar.HOUR_OF_DAY, 0)  // Set to 00:00:00
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun getEndOfLastMonthInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)  // Move to the previous month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) // Set to the last day of the last month
        calendar.set(Calendar.HOUR_OF_DAY, 23)  // Set to 23:59:59
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }

    private fun getStartOfLast3MonthsInMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -3)  // Move back by 3 months
        calendar.set(Calendar.DAY_OF_MONTH, 1) // Set to the first day of that month
        calendar.set(Calendar.HOUR_OF_DAY, 0)  // Set to 00:00:00
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun getStartOfDayInMillis(timeInMillis: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun getEndOfDayInMillis(timeInMillis: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }



}