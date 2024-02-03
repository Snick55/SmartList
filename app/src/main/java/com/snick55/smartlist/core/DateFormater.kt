package com.snick55.smartlist.core

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

interface DateFormater {

    fun format(time: Long): String

    class DateFormaterImpl @Inject constructor() : DateFormater {

        @SuppressLint("SimpleDateFormat")
        override fun format(time: Long): String {
            return SimpleDateFormat("dd.MM.yyyy").format(
                Date(
                    time
                )
            )
        }
    }

}