package com.erictriawan.githubuser.di

import com.erictriawan.githubuser.service.GitHubApi
import com.erictriawan.githubuser.service.GitHubService
import com.erictriawan.githubuser.service.GitHubServiceImpl
import com.erictriawan.githubuser.ui.UserViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single {
        val timeoutSeconds = 15L

        val okhttp =
            OkHttpClient.Builder()
                .connectTimeout(timeoutSeconds, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(timeoutSeconds, java.util.concurrent.TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Accept", "application/vnd.github.v3+json").build()
                    chain.proceed(request)
                }


        okhttp.build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(GitHubApi.API_ENDPOINT)
            .build()
    }

    single<GitHubApi> { get<Retrofit>().create(GitHubApi::class.java) }

    single<GitHubService> { GitHubServiceImpl() }

    viewModel { UserViewModel() }
}