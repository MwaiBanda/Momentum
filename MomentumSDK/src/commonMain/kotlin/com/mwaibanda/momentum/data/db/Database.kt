package com.mwaibanda.momentum.data.db

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = MomentumDatabase(driver = databaseDriverFactory.createDriver())
    private val queries = database.momentumDatabaseQueries

    internal fun deleteTransactions() {
        queries.deleteAll()
    }

    internal fun getAllTransactions() : List<UserTransaction> {
        return queries.getAllTransactions().executeAsList()
    }

    internal fun insertTransaction(name: String, amount: Double) {
        queries.insertTransaction(name, amount)
    }
}