package com.oparate.test.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.oparate.test.model.CryptoResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.Exception

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "cryto.db"
        private const val DATABASE_VERSION = 1
        private const val TBL_CURRENCY = "tbl_crypto"
        private const val ID = "id"
        private const val RESPONSE = "responsez"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createCurrencyTBL =
            "CREATE TABLE " + TBL_CURRENCY + " (" + ID + " INTEGER PRIMARY KEY ,"  + RESPONSE + " TEXT );"
        db?.execSQL(createCurrencyTBL)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TBL_CURRENCY)
        onCreate(db)
    }

    fun insertCurrency(response: CryptoResponse): Long {
        val db = this.writableDatabase

        val currencyContentValues = ContentValues()
        currencyContentValues.put(RESPONSE, Json.encodeToString(response))

        val insertCurrency = db.insert(TBL_CURRENCY, null, currencyContentValues)

        return insertCurrency
    }

    @SuppressLint("Range")
    fun getLatestCurrency(): CryptoResponse? {
        var cryptoResponse: CryptoResponse? = null
        val selectQuery =
            "SELECT " + RESPONSE + " from " + TBL_CURRENCY

        val db = this.readableDatabase
        var cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            db.execSQL(selectQuery)
            return cryptoResponse
        }

        if (cursor.moveToLast()) {
            var str = cursor.getString(cursor.getColumnIndex(RESPONSE))
            Log.e("QWEDD", str)
            cryptoResponse = Json.decodeFromString<CryptoResponse>(str)
        }
        return cryptoResponse
    }

}