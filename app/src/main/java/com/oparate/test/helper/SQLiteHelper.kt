package com.oparate.test.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.oparate.test.model.DataResponse

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "cryto.db"
        private const val DATABASE_VERSION = 1
        private const val TBL_CURRENCY = ""
        private const val TBL_QUOTE = ""
        private const val ID = ""
        private const val CURRENCY_ID = ""
        private const val SYMBOL = ""
        private const val SLUG = ""
        private const val NAME = ""
        private const val PRICE = ""
        private const val LAST_UPDATED = ""
        private const val CURRENCY_KEY =""
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createCurrencyTBL =
            "CREATE TABLE " + TBL_CURRENCY + " (" + ID + " INTEGER, " + SYMBOL + " TEXT, " + SLUG + " TEXT ," + NAME + " TEXT );"
        val createQuoteTBL =
            "CREATE TABLE " + TBL_QUOTE + " (" + ID + " INTEGER PRIMARY KEY, " + PRICE + "TEXT , " + LAST_UPDATED + "TEXT , "+ CURRENCY_KEY + "TEXT , " + CURRENCY_ID + " INTEGER FOREIGN KEY (" + CURRENCY_ID + ") REFERENCES " + TBL_CURRENCY + " (" + ID + ") );"
        db?.execSQL(createCurrencyTBL)
        db?.execSQL(createQuoteTBL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TBL_CURRENCY)
        db!!.execSQL("DROP TABLE IF EXISTS " + TBL_QUOTE)
        onCreate(db)
    }

    fun insertCurrency(cur: DataResponse):Long{
        val db = this.writableDatabase

        val currencyContentValues = ContentValues()
        currencyContentValues.put(ID, cur.id)
        currencyContentValues.put(SYMBOL, cur.symbol)
        currencyContentValues.put(SLUG, cur.slug)
        currencyContentValues.put(LAST_UPDATED, cur.last_updated)
        currencyContentValues.put(NAME, cur.name)


        val insertCurrency = db.insert(TBL_CURRENCY, null, currencyContentValues)

        for( key in cur.quote.keys){
            val quoteContentValues = ContentValues()
            quoteContentValues.put(CURRENCY_ID, cur.id )
            quoteContentValues.put(CURRENCY_KEY, key )
            quoteContentValues.put(PRICE, cur.quote[key]?.price)
            quoteContentValues.put(LAST_UPDATED, cur.quote[key]?.last_updated)
            db.insert(TBL_QUOTE, null , quoteContentValues)
        }

        return  insertCurrency
    }



}