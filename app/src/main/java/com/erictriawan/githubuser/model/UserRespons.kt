package com.erictriawan.githubuser.model

import com.google.gson.annotations.SerializedName

data class UserRespons<T> (
    @SerializedName("total_count")
    val TotalCount: Int,
    @SerializedName("items")
    val item: List<T>
)