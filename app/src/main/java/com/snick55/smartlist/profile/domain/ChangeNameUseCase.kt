package com.snick55.smartlist.profile.domain

import javax.inject.Inject

interface ChangeNameUseCase {

    suspend fun execute(name:String)


    class ChangeNameUseCaseImpl @Inject constructor(
        private val repository: ProfileRepository
    ): ChangeNameUseCase{

        override suspend fun execute(name: String) {
            if (name.isBlank()) return
            repository.changeName(name)
        }
    }

}