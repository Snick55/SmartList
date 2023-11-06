package com.snick55.smartlist.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.runBlocking


typealias MutableLiveContainer<T> = MutableLiveData<Container<T>>
typealias LiveContainer<T> = LiveData<Container<T>>

sealed class Container<out T> {


    fun <R> map(mapper: ((T) -> R)? = null): Container<R> {
        return runBlocking {
            val suspendMapper: (suspend (T) -> R)? = if (mapper == null) {
                null
            } else {
                {
                    mapper(it)
                }
            }
            suspendMap(suspendMapper)
        }
    }


    abstract suspend fun <R> suspendMap(mapper: (suspend (T) -> R)? = null): Container<R>


    abstract fun unwrap(): T


    abstract fun getOrNull(): T?


    object Pending : Container<Nothing>() {

        override suspend fun <R> suspendMap(mapper: (suspend (Nothing) -> R)?): Container<R> {
            return this
        }

        override fun unwrap(): Nothing {
            throw IllegalStateException("Container is Pending")
        }

        override fun getOrNull(): Nothing? {
            return null
        }
    }


    data class Error (
        val exception: java.lang.Exception
    ) : Container<Nothing>() {

        override suspend fun <R> suspendMap(mapper: (suspend (Nothing) -> R)?): Container<R> {
            return this
        }

        override fun unwrap(): Nothing {
            throw exception
        }

        override fun getOrNull(): Nothing? {
            return null
        }
    }


    data class Success<T> (
        private val value: T
    ) : Container<T>() {

        override suspend fun <R> suspendMap(mapper: (suspend (T) -> R)?): Container<R> {
            if (mapper == null) throw IllegalStateException("Can't map Container.Success without mapper")
            return try {
                Success(mapper(value))
            } catch (e: Exception) {
                Error(e)
            }
        }

        override fun unwrap(): T {
            return value
        }

        override fun getOrNull(): T? {
            return value
        }
    }

}