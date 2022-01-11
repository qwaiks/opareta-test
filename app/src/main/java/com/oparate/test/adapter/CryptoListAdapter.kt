package com.oparate.test.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oparate.test.R
import com.oparate.test.model.DataResponse
import java.math.RoundingMode
import java.text.DecimalFormat

class CryptoListAdapter(
) : RecyclerView.Adapter<CryptoListAdapter.ViewHolder>() {

    private var mList: List<DataResponse>? = listOf()
    private var amount: String? = null
    private var baseConvert: String? = null


    fun setValues(
        mList: List<DataResponse>,
        amount: String,
        baseConvert: String
    ) {
        this.mList = mList
        this.amount = amount
        this.baseConvert = baseConvert
        Log.e("QWEDD", "${mList.size} $amount $baseConvert")
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_crypto_result, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val cryptoResult = mList!![position]
        Log.e("QWEDD", cryptoResult.quote.toString())
        val currencyValue = cryptoResult.quote[baseConvert]?.let { getCurrencyValue(it.price) }

        holder.txtName.text = cryptoResult.name;
        holder.txtSymbol.text = cryptoResult.symbol;
        holder.txtLastUpdate.text = cryptoResult.last_updated
        holder.txtValue.text = currencyValue


    }

    fun getCurrencyValue(rate: Double): String {
        val df = DecimalFormat("#.#####")
        df.roundingMode = RoundingMode.CEILING
        return df.format((rate * amount!!.toDouble()))

    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtSymbol: TextView = itemView.findViewById(R.id.txtSymbol)
        val txtLastUpdate: TextView = itemView.findViewById(R.id.txtLastUpdate)
        val txtValue: TextView = itemView.findViewById(R.id.txtValue)
    }
}
