package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.model

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.DeletedTransactionsEntity
import com.example.financetracker.main_page_feature.finance_entry.add_transactions.data.local.data_source.TransactionsEntity
import com.example.financetracker.setup_account.data.local.data_source.country.CountryMapper
import com.example.financetracker.setup_account.domain.model.Currency


data class DeletedTransactions(
    val transactionId: Int,
    val userUid: String
)

fun DeletedTransactions.toEntity(): DeletedTransactionsEntity {
    return DeletedTransactionsEntity(
        userUid = this.userUid,
        transactionId = transactionId
    )
}

fun DeletedTransactionsEntity.toDomain(): DeletedTransactions {
    return DeletedTransactions(
        transactionId = this.transactionId,
        userUid = this.userUid,
    )
}


