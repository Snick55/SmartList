package com.snick55.smartlist.lists.domain

import com.snick55.smartlist.core.Container
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

interface GetAllListsUseCase {

    fun execute(): Flow<Container<List<ListItemDomain>>>

    class GetAllListsUseCaseImpl @Inject constructor(
        private val repository: ListsRepository
    ) : GetAllListsUseCase {

        override fun execute(): Flow<Container<List<ListItemDomain>>> = flow {
            repository.getAllLists().catch {
                emit(Container.Error(it))
            }.collect {
                if (it.unwrap().isEmpty()) {
                    emit(Container.Error(EmptyListsException()))
                } else
                    emit(it.map { list ->
                        list.sortedBy { itemDomain ->
                            itemDomain.date
                        }
                            .reversed()
                            .map { itemDomain ->
                            itemDomain.copy(
                                date = SimpleDateFormat("dd.MM.yyyy").format(
                                    Date(
                                        itemDomain.date.toLong()
                                    )
                                )
                            )
                        }
                    })
            }
        }
    }
}