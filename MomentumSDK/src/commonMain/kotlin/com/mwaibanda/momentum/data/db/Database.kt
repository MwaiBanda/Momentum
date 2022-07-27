package com.mwaibanda.momentum.data.db

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

    internal fun getUserByUserId(userId: String): MomentumUser {
        return database.getUserById(userId = userId).executeAsOne()
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
}