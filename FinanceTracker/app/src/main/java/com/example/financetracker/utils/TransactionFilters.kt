package com.example.financetracker.utils


sealed class TransactionFilter {

    data class TransactionType(val type: TransactionTypeFilter) : TransactionFilter()
    data class Order(val order: TransactionOrder) : TransactionFilter()
    data class Category(val selectedCategories: List<com.example.financetracker.domain.model.Category>) : TransactionFilter()
    data class Duration(val durationFilter: DurationFilter) : TransactionFilter()

    fun copy(
        transactionType: TransactionTypeFilter? = null,
        transactionOrder: TransactionOrder? = null,
        selectedCategories: List<com.example.financetracker.domain.model.Category>? = null,
        durationFilter: DurationFilter? = null,
    ): TransactionFilter {
        return when (this) {
            is TransactionType -> TransactionType(transactionType ?: this.type)
            is Order -> Order(transactionOrder ?: this.order)
            is Category -> Category(selectedCategories ?: this.selectedCategories)
            is Duration -> Duration(durationFilter ?: this.durationFilter)
        }
    }
}
