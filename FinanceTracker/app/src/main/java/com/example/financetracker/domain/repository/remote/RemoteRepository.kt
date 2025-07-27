package com.example.financetracker.domain.repository.remote

import com.example.financetracker.domain.model.UserProfile
import com.example.financetracker.domain.model.Transactions

interface RemoteRepository {
    suspend fun logoutUser()
    suspend fun getCurrentUserID(): String?
    suspend fun getCurrentUserEmail(): String?
    suspend fun saveUserProfile(userId: String,profile: UserProfile)
    suspend fun getUserProfile(userId: String): UserProfile?
    suspend fun cloudSyncSingleTransaction(userId: String, transactions: Transactions, updateCloudSync:suspend (Int, Boolean) -> Unit)
    suspend fun cloudSyncMultipleTransaction()
    fun isInternetConnected(): Boolean
    suspend fun deletedTransactionRemote(transactionId: Int,userId: String)
    suspend fun getRemoteTransactions(userId:String): List<Transactions>
    suspend fun insertRemoteTransactionToLocal()
}