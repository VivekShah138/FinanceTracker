package com.example.financetracker.main_page_feature.finance_entry.add_transactions.domain.usecases

import com.example.financetracker.domain.repository.remote.RemoteRepository

class InsertRemoteTransactionsToLocal(
    private val remoteRepository: RemoteRepository
) {

    suspend operator fun invoke(){
        return remoteRepository.insertRemoteTransactionToLocal()
    }
}