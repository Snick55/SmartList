package com.snick55.smartlist.members.data

import com.snick55.smartlist.core.log
import com.snick55.smartlist.lists.data.DetailsDataSource
import com.snick55.smartlist.members.domain.MemberDomain
import com.snick55.smartlist.members.domain.MembersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MembersRepositoryImpl @Inject constructor(
    private val detailsDataSource: DetailsDataSource
): MembersRepository {


    override  fun getAllMembersInList(): Flow<List<MemberDomain>> {

        return detailsDataSource.getAllMembersInList().map { listMembersDomain->
            listMembersDomain.map {
                MemberDomain(it.name,it.phoneNumber,it.UUID)
            }
        }
    }

    override  fun getAllMembers(): Flow<List<MemberDomain>> {
      return  detailsDataSource.getAllMembers().map {listMembersDomain->
          listMembersDomain.map {
              MemberDomain(it.name,it.phoneNumber,it.UUID)
          }
      }
    }

    override suspend fun addMemberByUUID(UUID: String) {
        detailsDataSource.addMemberByUUID(UUID)
    }
}