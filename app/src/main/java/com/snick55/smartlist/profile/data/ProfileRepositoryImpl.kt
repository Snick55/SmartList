package com.snick55.smartlist.profile.data


import com.snick55.smartlist.core.log
import com.snick55.smartlist.profile.domain.Account
import com.snick55.smartlist.profile.domain.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
  private val  profileDataSource: ProfileDataSource
): ProfileRepository {

    override suspend fun logout() {
        profileDataSource.logout()
    }


    override suspend fun getMainInfoFlow(): Flow<Account> = profileDataSource.getProfileInfo()

    override suspend fun changeName(name: String) {
        profileDataSource.changeName(name)
    }
}