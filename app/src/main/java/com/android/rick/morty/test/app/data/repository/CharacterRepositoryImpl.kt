package com.android.rick.morty.test.app.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.android.rick.morty.test.app.core.PAGE_COUNT
import com.android.rick.morty.test.app.data.local.CharactersDatabase
import com.android.rick.morty.test.app.data.local.mapper.toCharacter
import com.android.rick.morty.test.app.data.remote.CharactersRemoteMediator
import com.android.rick.morty.test.app.data.remote.api.RickAndMortyApi
import com.android.rick.morty.test.app.domain.model.Character
import com.android.rick.morty.test.app.domain.model.SortOrder
import com.android.rick.morty.test.app.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val database: CharactersDatabase,
    private val api: RickAndMortyApi
) : CharacterRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharacters(sortOrder: SortOrder?): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_COUNT),
            remoteMediator = CharactersRemoteMediator(database, api)
        ) {
            when (sortOrder) {
                SortOrder.DESC -> database.charactersDao.pagingSourceSortedDesc()
                SortOrder.ASC -> database.charactersDao.pagingSourceSortedAsc()
                null -> database.charactersDao.pagingSource()
            }
        }.flow.map { pagingData ->
            pagingData.map { it.toCharacter() }
        }
    }

    override fun getCharacterById(characterId: Int): Flow<Character?> {
        return database.charactersDao.getCharacterById(characterId).map { it?.toCharacter() }
    }
}