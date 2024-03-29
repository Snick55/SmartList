package com.snick55.smartlist.di.lists

import com.snick55.smartlist.lists.domain.CreateNewListUseCase
import com.snick55.smartlist.lists.domain.DeleteAndLeaveUseCase
import com.snick55.smartlist.lists.domain.DeleteItemUseCase
import com.snick55.smartlist.lists.domain.GetAllItemsUseCase
import com.snick55.smartlist.lists.domain.GetAllListsUseCase
import com.snick55.smartlist.members.domain.AddMemberToListUseCase
import com.snick55.smartlist.members.domain.GetAllMembersUseCase
import com.snick55.smartlist.members.domain.GetAllUsersByPhoneUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindGetAllListsUseCase(useCase: GetAllListsUseCase.GetAllListsUseCaseImpl): GetAllListsUseCase

    @Binds
    abstract fun bindCreateNewListUseCase(useCase: CreateNewListUseCase.CreateNewListUseCaseImpl): CreateNewListUseCase

    @Binds
    abstract fun bindGetAllItemsUseCase(useCase: GetAllItemsUseCase.GetAllItemsUseCaseImpl): GetAllItemsUseCase

    @Binds
    abstract fun bindGetAllMembersUseCase(useCase: GetAllMembersUseCase.GetAllMembersUseCaseImpl): GetAllMembersUseCase

    @Binds
    abstract fun bindGetAllUsersByPhoneUseCase(useCase: GetAllUsersByPhoneUseCase.GetAllUsersByPhoneUseCaseImpl): GetAllUsersByPhoneUseCase

    @Binds
    abstract fun bindDeleteAndLeaveUseCase(useCase: DeleteAndLeaveUseCase.DeleteAndLeaveUseCaseImpl): DeleteAndLeaveUseCase
    @Binds
    abstract fun bindAddMemberToListUseCase(useCase: AddMemberToListUseCase.AddMemberToListUseCaseImpl): AddMemberToListUseCase

    @Binds
    abstract fun bindDeleteItemUseCse(useCase: DeleteItemUseCase.DeleteItemUseCaseImpl): DeleteItemUseCase


}