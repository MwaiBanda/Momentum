package com.mwaibanda.momentum.data.db

import com.squareup.sqldelight.logs.LogSqliteDriver
import kotlin.math.log

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = MomentumDatabase(
        driver = databaseDriverFactory.createDriver()
    ).momentumDatabaseQueries

    /**
     * Transaction Queries
     */
    internal fun insertTransaction(
        description: String,
        date: String,
        amount: Double,
        isSeen: Boolean,
    ) {
        database.insertTransaction(
            description = description,
            date = date,
            amount = amount,
            seen = isSeen,
        )
    }

    internal fun insertTransactions(transactions: List<MomentumTransaction>) {
        database.transaction {
            afterCommit { println("${transactions.size} transactions were inserted.") }
            transactions.forEach {
                database.insertTransaction(
                    description = it.description,
                    date = it.date,
                    amount = it.amount,
                    seen = it.is_seen
                )
            }
        }
    }

    internal fun getAllTransactions() : List<MomentumTransaction> {
        return database.getAllTransactions().executeAsList()
    }

    internal fun deleteTransactionById(transactionId: Int) {
        database.deleteTransactionById(id = transactionId.toLong())
    }

    internal fun deleteAllTransactions() {
        database.deleteAllTransactions()
    }
    /**
     * User Queries
     */
    internal fun insertUser(
        fullname: String,
        phone: String,
        password: String,
        email: String,
        createdOn: String,
        userId: String
    ) {
        database.insertUser(
            fullname = fullname,
            phone = phone,
            password = password,
            email = email,
            createdOn = createdOn,
            userId = userId
        )
    }

    internal fun getUserByUserId(userId: String): MomentumUser? {
        return database.getUserByUserId(userId = userId).executeAsOneOrNull()
    }

    internal fun updateUserFullnameByUserId(userId: String, fullname: String){
        database.updateUserFullnameByUserId(
            fullname = fullname,
            userId = userId
        )
    }

    internal fun updateUserPhoneByUserId(userId: String, phone: String) {
        database.updateUserPhoneByUserId(
            phone = phone,
            userId = userId
        )
    }

    internal fun updateUserEmailByUserId(userId: String, email: String) {
        database.updateUserEmailByUserId(
            email = email,
            userId = userId
        )
    }

    internal fun updateUserPasswordUserId(userId: String, password: String) {
        database.updateUserPasswordUserId(
            password = password,
            userId = userId
        )
    }

    internal fun deleteUserByUserId(userId: String){
        database.deleteUserByUserId(userId = userId)
    }
    /**
     * Billing Address Queries
     */
    internal fun insertBillingAddress(
        streetAddress: String,
        apt: String?,
        city: String,
        zipCode: String,
        userId: String
    ) {
        database.insertBillingAddress(
            streetAddress = streetAddress,
            apt = apt,
            city = city,
            zipCode = zipCode,
            userId = userId
        )
    }

    internal fun getBillingAddressByUserId(userId: String): MomentumBillingAddress? {
        return database.getBillingAddressByUserId(userId = userId).executeAsOneOrNull()
    }

    internal fun updateBillingStreetByUserId(userId: String, streetAddress: String) {
        database.updateBillingStreetByUserId(
            streetAddress = streetAddress,
            userId = userId
        )
    }
    internal fun updateBillingAptByUserId(userId: String, apt: String) {
        database.updateBillingAptByUserId(
            apt = apt,
            userId = userId
        )
    }

    internal fun updateBillingCityByUserId(userId: String, city: String) {
        database.updateBillingCityByUserId(
            city = city,
            userId = userId
        )
    }

    internal fun updateBillingZipCodeByUserId(userId: String, zipCode: String) {
        database.updateBillingZipCodeByUserId(
            zipCode = zipCode,
            userId = userId
        )
    }

    internal fun deleteBillingByUserId(userId: String) {
        database.deleteBillingByUserId(userId = userId)
    }
    /**
     * Sermon Queries
     */
    internal fun addSermon(
        id: String,
        lastPlayedTime: Double,
        lastPlayedPercentage: Int,
    ) {
       database.insertSermon(
           id = id,
           lastPlayedTime = lastPlayedTime,
           lastPlayedPercentage = lastPlayedPercentage
       )
    }

    internal fun getWatchedSermons(): List<MomentumSermon> {
        return database.getAllWatchedSermons().executeAsList()
    }
}