package com.mwaibanda.momentum.data.db

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {


    private val database = MomentumDatabase(
        driver = databaseDriverFactory.createDriver()
    ).momentumDatabaseQueries

    internal fun deleteAllTransactions() {
        database.deleteAll()
    }

    internal fun getAllTransactions() : List<MomentumTransaction> {
        return database.getAllTransactions().executeAsList()
    }

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
}