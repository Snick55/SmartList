package com.snick55.smartlist.profile.domain

import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun logout()

    suspend fun getMainInfoFlow(): Flow<Account>

    suspend fun changeName(name: String)

}