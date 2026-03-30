package com.example.financetracker.presentation.features.view_records_feature.components

import com.example.financetracker.presentation.features.view_records_feature.events.ViewSavedItemsEvents
import com.example.financetracker.presentation.features.view_records_feature.events.ViewTransactionsEvents
import com.example.financetracker.presentation.features.view_records_feature.states.ViewSavedItemsStates
import com.example.financetracker.presentation.features.view_records_feature.states.ViewTransactionsStates
import com.example.financetracker.utils.MenuItems


fun buildTransactionsMenu(
    onNavigateToTransactionInfo: () -> Unit,
    selectedTransactionsSize: Int,
    viewTransactionsEvents: (ViewTransactionsEvents) -> Unit
): List<MenuItems> {

    if (selectedTransactionsSize == 1) {
        return listOf(
            MenuItems(
                text = "Info",
                onClick = {
                    onNavigateToTransactionInfo()
                }
            ),

            MenuItems(
                text = "Delete",
                onClick = {
                    viewTransactionsEvents(
                        ViewTransactionsEvents.ChangeCustomDateAlertBox(true)
                    )
                }
            ),

            MenuItems(
                text = "Select All",
                onClick = {
                    viewTransactionsEvents(
                        ViewTransactionsEvents.SelectAllTransactions
                    )
                }
            )
        )
    }

    return listOf(
        MenuItems(
            text = "Delete All",
            onClick = {
                viewTransactionsEvents(
                    ViewTransactionsEvents.ChangeCustomDateAlertBox(true)
                )
            }
        ),

        MenuItems(
            text = "Select All",
            onClick = {
                viewTransactionsEvents(
                    ViewTransactionsEvents.SelectAllTransactions
                )
            }
        )
    )
}


fun buildSavedItemsMenu(
    onNavigateToSavedItemInfo: () -> Unit,
    selectedSavedItemsSize: Int,
    viewSavedItemsEvents: (ViewSavedItemsEvents) -> Unit,
): List<MenuItems> {

    if (selectedSavedItemsSize == 1) {

        return listOf(

            MenuItems(
                text = "Info",
                onClick = {
                    onNavigateToSavedItemInfo()
                }
            ),

            MenuItems(
                text = "Delete",
                onClick = {
                    viewSavedItemsEvents(
                        ViewSavedItemsEvents.ChangeCustomDateAlertBox(true)
                    )
                }
            ),

            MenuItems(
                text = "Edit",
                onClick = {
                    viewSavedItemsEvents(
                        ViewSavedItemsEvents.ChangeUpdateBottomSheetState(true)
                    )
                }
            ),

            MenuItems(
                text = "Select All",
                onClick = {
                    viewSavedItemsEvents(
                        ViewSavedItemsEvents.SelectAllItems
                    )
                }
            )
        )
    }

    return listOf(

        MenuItems(
            text = "Delete All",
            onClick = {
                viewSavedItemsEvents(
                    ViewSavedItemsEvents.ChangeCustomDateAlertBox(true)
                )
            }
        ),

        MenuItems(
            text = "Select All",
            onClick = {
                viewSavedItemsEvents(
                    ViewSavedItemsEvents.SelectAllItems
                )
            }
        )
    )
}