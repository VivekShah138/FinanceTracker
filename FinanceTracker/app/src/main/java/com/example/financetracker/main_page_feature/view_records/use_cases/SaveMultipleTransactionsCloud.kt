package com.example.financetracker.main_page_feature.view_records.use_cases

import com.example.financetracker.domain.repository.remote.RemoteRepository

class SaveMultipleTransactionsCloud(
    private val remoteRepository: RemoteRepository
) {

    suspend operator fun invoke(){
        return remoteRepository.cloudSyncMultipleTransaction()
    }
}