package com.erictriawan.githubuser.service

import com.erictriawan.githubuser.model.User
import com.erictriawan.githubuser.model.UserRespons
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface GitHubApi {

    companion object {
        const val API_ENDPOINT = "https://api.github.com"
        private const val PAGE_SIZE = 15
    }

    @GET("/search/users")
    fun searchUsers(@Query("q") term: String,
                    @Query("page") pageNumber: Int = 1,
                    @Query("per_page") pageSize: Int = PAGE_SIZE): Single<UserRespons<User>>
} 