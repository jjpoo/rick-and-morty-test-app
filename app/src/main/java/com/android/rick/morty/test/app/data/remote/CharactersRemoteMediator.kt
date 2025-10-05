package com.android.rick.morty.test.app.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.android.rick.morty.test.app.data.local.CharacterEntity
import com.android.rick.morty.test.app.data.local.CharactersDatabase
import com.android.rick.morty.test.app.data.local.mapper.toEntity
import com.android.rick.morty.test.app.data.remote.api.RickAndMortyApi
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator(
    private val charactersDatabase: CharactersDatabase,
    private val api: RickAndMortyApi
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }
            val characters = api.getCharacters(page = loadKey)
            charactersDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    charactersDatabase.charactersDao.clearAll()
                }
                val characterEntities = characters.results.map { it.toEntity() }
                charactersDatabase.charactersDao.upsertAll(characterEntities)
            }
            MediatorResult.Success(
                endOfPaginationReached = characters.results.isEmpty()
            )
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}