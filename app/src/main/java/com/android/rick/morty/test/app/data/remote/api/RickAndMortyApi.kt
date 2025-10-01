package com.android.rick.morty.test.app.data.remote.api

import com.android.rick.morty.test.app.data.remote.model.CharactersApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): CharactersApiResponse
}
