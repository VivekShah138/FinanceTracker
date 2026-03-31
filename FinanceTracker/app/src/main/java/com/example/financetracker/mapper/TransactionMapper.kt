package com.example.financetracker.mapper

import com.example.financetracker.data.data_source.local.room.modules.transactions.TransactionsEntity
import com.example.financetracker.domain.model.Transactions

object TransactionMapper {
    fun toEntity(transaction: Transactions): TransactionsEntity {
        return TransactionsEntity(
            amount = transaction.amount,
            currency = CountryMapper.fromCurrencies(transaction.currency),
            convertedAmount = transaction.convertedAmount,
            exchangeRate = transaction.exchangeRate,
            transactionType = transaction.transactionType,
            category = transaction.category,
            dateTime = transaction.dateTime,
            userUid = transaction.userUid,
            description = transaction.description,
            isRecurring = transaction.isRecurring,
            cloudSync = transaction.cloudSync,
            transactionName = transaction.transactionName,
            transactionId = transaction.transactionId ?: 0
        )
    }

    fun fromEntity(entity: TransactionsEntity): Transactions {
        return Transactions(
            transactionId = entity.transactionId,
            amount = entity.amount,
            currency = CountryMapper.toCurrencies(entity.currency),
            convertedAmount = entity.convertedAmount,
            exchangeRate = entity.exchangeRate,
            transactionType = entity.transactionType,
            category = entity.category,
            dateTime = entity.dateTime,
            userUid = entity.userUid,
            description = entity.description,
            isRecurring = entity.isRecurring,
            cloudSync = entity.cloudSync,
            transactionName = entity.transactionName
        )
    }
}