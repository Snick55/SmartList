package com.snick55.smartlist.members.domain

import com.snick55.smartlist.core.log
import com.snick55.smartlist.lists.domain.EmptyListsException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetAllUsersByPhoneUseCase {

    suspend fun execute(phoneNumber: String): Flow<List<MemberDomain>>


    class GetAllUsersByPhoneUseCaseImpl @Inject constructor(
        private val repository: MembersRepository
    ) : GetAllUsersByPhoneUseCase {

        override suspend fun execute(phoneNumber: String): Flow<List<MemberDomain>> = flow {
            val phoneNum = if (phoneNumber.startsWith("8"))
                "+7${phoneNumber.substring(1)}"
            else
                phoneNumber

            repository.getAllMembers().map { listDomain ->
                listDomain.filter {
                    it.phoneNumber.startsWith(phoneNum)
                }
            }
                .collect { listDomain ->
                    if (listDomain.isEmpty()) throw NoOneUserException()
                    emit(listDomain)
                }
        }

    }
}