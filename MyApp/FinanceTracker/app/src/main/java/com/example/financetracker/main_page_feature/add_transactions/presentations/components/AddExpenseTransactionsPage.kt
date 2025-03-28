package com.example.financetracker.main_page_feature.add_transactions.presentations.components



import BottomNavigationBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.financetracker.core.presentation.components.AppTopBar
import kotlinx.coroutines.launch

@Composable
fun AddExpenseTransactionsPage(
){
    MaterialTheme {
        val pagerState = rememberPagerState(pageCount = { 3 }) // 3 tabs
        val coroutineScope = rememberCoroutineScope()

        Text("Expense", modifier = Modifier.fillMaxSize())
    }
}