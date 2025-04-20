package com.example.financetracker.main_page_feature.view_records.use_cases

import com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.repository.TransactionLocalRepository

class DeleteSelectedTransactionsByIdsLocally(
    private val transactionLocalRepository: TransactionLocalRepository
){

    suspend operator fun invoke(transactionId: Int){
        return transactionLocalRepository.deleteSelectedTransactionsByIds(transactionId)
    }
}