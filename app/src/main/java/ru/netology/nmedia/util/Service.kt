package ru.netology.nmedia.util

import android.annotation.SuppressLint
import android.os.Bundle
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object Service {

    @SuppressLint("SimpleDateFormat")
    fun getSimpleDateFormat(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date())
    }

    fun peopleCounter(quantity: Int): String {
        return when (quantity) {
            in 1..999 -> quantity.toString()
            in 1000..9999 -> {
                if (floor(quantity.toDouble() / 100) % 10 == 0.0) {
                    String.format("%.0f", quantity.toDouble() / 1000).plus("k")
                } else {
                    String.format("%.1f", quantity.toDouble() / 1000).plus("k")
                }
            }
            in 10000..999999 -> String.format("%.0f", floor(quantity.toDouble() / 1000)).plus("k")
            in 1000000..Int.MAX_VALUE -> {
                if (floor(quantity.toDouble() / 100000) % 10 == 0.0) {
                    String.format("%.0f", quantity.toDouble() / 1000000).plus("M")
                } else {
                    String.format("%.1f", quantity.toDouble() / 1000000).plus("M")
                }
            }
            else -> ""
        }
    }
}

object StringArg: ReadWriteProperty<Bundle, String?>{
    override fun getValue(thisRef: Bundle, property: KProperty<*>): String? {
        return thisRef.getString(property.name)
    }
    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: String?) {
        thisRef.putString(property.name, value)
    }

}


