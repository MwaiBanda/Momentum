package com.mwaibanda.momentum.data.db

import android.content.Context
import android.os.Parcelable
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.android.parcel.Parcelize

actual class DatabaseDriverFactory(private val context:  Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(MomentumDatabase.Schema, context, "MomentumDatabase.db")
    }
}

