package com.snick55.smartlist.members.domain

import javax.inject.Inject

interface AddMemberToListUseCase {

    suspend fun execute(UUID: String)

    class AddMemberToListUseCaseImpl @Inject constructor(
        private val repository: MembersRepository
    ): AddMemberToListUseCase{

        override suspend fun execute(UUID: String) {
            repository.addMemberByUUID(UUID)
        }
    }

}