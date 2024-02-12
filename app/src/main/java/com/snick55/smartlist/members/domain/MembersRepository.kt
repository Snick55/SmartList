package com.snick55.smartlist.members.domain

import kotlinx.coroutines.flow.Flow

interface MembersRepository {

     fun getAllMembersInList(): Flow<List<MemberDomain>>

     fun getAllMembers(): Flow<List<MemberDomain>>

}