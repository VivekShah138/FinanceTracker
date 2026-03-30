package com.example.financetracker.presentation.features.view_records_feature.components
import androidx.compose.runtime.Composable
import com.example.financetracker.presentation.core_components.AppTopBar
import com.example.financetracker.presentation.features.view_records_feature.events.ViewTransactionsEvents
import com.example.financetracker.presentation.features.view_records_feature.events.ViewSavedItemsEvents
import com.example.financetracker.presentation.features.view_records_feature.states.ViewSavedItemsStates
import com.example.financetracker.presentation.features.view_records_feature.states.ViewTransactionsStates

@Composable
fun RecordsTopBar(
    onNavigateToTransactionInfo: () -> Unit,
    onNavigateToSavedItemInfo: () -> Unit,
    transactionsState: ViewTransactionsStates,
    savedItemsState: ViewSavedItemsStates,
    viewTransactionsEvents: (ViewTransactionsEvents) -> Unit,
    viewSavedItemsEvents: (ViewSavedItemsEvents) -> Unit,
) {

    when {
         transactionsState.isSelectionMode -> {
            AppTopBar(
                title = transactionsState.selectedTransactions.size.toString(),
                showMenu = true,
                showBackButton = true,
                onBackClick = {
                    viewTransactionsEvents(
                        ViewTransactionsEvents.ExitSelectionMode
                    )
                },
                menuItems = buildTransactionsMenu(
                    onNavigateToTransactionInfo = onNavigateToTransactionInfo,
                    viewTransactionsEvents = viewTransactionsEvents,
                    selectedTransactionsSize = transactionsState.selectedTransactions.size,
                )
            )
        }

        savedItemsState.isSelectionMode -> {
            AppTopBar(
                title = savedItemsState.selectedSavedItems.size.toString(),
                showMenu = true,
                showBackButton = true,
                onBackClick = {
                    viewSavedItemsEvents(
                        ViewSavedItemsEvents.ExitSelectionMode
                    )
                },
                menuItems = buildSavedItemsMenu(
                    onNavigateToSavedItemInfo = onNavigateToSavedItemInfo,
                    viewSavedItemsEvents = viewSavedItemsEvents,
                    selectedSavedItemsSize = savedItemsState.selectedSavedItems.size,
                )
            )
        }

        else -> {
            AppTopBar(
                title = "Records",
                showMenu = false,
                showBackButton = false,
                onBackClick = {},
                menuItems = emptyList()
            )
        }
    }
}