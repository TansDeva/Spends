package me.tansdeva.spends.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson

class TransactionBean(private val id: String, val date: String, val description: String, val amount: String, val currency: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(date)
        parcel.writeString(description)
        parcel.writeString(amount)
        parcel.writeString(currency)
    }

    fun getId(): String {
        return id
    }

    override fun toString(): String {
        return Gson().toJson(this)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransactionBean> {
        override fun createFromParcel(parcel: Parcel): TransactionBean {
            return TransactionBean(parcel)
        }

        override fun newArray(size: Int): Array<TransactionBean?> {
            return arrayOfNulls(size)
        }
    }
}