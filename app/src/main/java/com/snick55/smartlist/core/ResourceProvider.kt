package com.snick55.smartlist.core

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ResourceProvider {

    fun getString(@StringRes id: Int): String

    class ResourceProviderImpl @Inject constructor(
        @ApplicationContext private val context: Context
    ) : ResourceProvider {

        override fun getString(id: Int): String {
            return context.getString(id)
        }
    }

}