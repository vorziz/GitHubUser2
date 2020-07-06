package com.erictriawan.githubuser.service

import com.erictriawan.githubuser.model.User
import com.erictriawan.githubuser.model.UserRespons
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject


interface GitHubService {
    fun getSearchUserList(term: String, pageNumber: Int): Single<UserRespons<User>>
}

class GitHubServiceImpl : GitHubService, KoinComponent {

    private val api: GitHubApi by inject()

    // preparing local cache here if we need

    override fun getSearchUserList(term: String, pageNumber: Int): Single<UserRespons<User>> =
        api.searchUsers(term, pageNumber)
}