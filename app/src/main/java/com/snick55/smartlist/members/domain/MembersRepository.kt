package com.snick55.smartlist.members.domain

import kotlinx.coroutines.flow.Flow

interface MembersRepository {

    suspend fun getAllMembers(): Flow<List<MemberDomain>>

}