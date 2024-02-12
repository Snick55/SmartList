package com.snick55.smartlist.members.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAllMembersUseCase {

    suspend fun execute(): Flow<List<MemberDomain>>


    class GetAllMembersUseCaseImpl @Inject constructor(
       private val repository: MembersRepository
    ): GetAllMembersUseCase{

        override suspend fun execute(): Flow<List<MemberDomain>>  {
          return repository.getAllMembersInList()
        }
    }

}