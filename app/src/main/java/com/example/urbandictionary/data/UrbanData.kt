package com.example.urbandictionary.data

import com.google.gson.annotations.SerializedName

data class UrbanResponseData(
    @SerializedName("list") val list: List<ListData>
)

data class ListData(
    @SerializedName("definition") val definition: String,
    @SerializedName("permalink") val permalink: String,
    @SerializedName("thumbs_up") val thumbs_up: Int,
    @SerializedName("sound_urls") val sound_urls: List<String>,
    @SerializedName("author") val author: String,
    @SerializedName("word") val word: String,
    @SerializedName("defid") val defid: Int,
    @SerializedName("current_vote") val current_vote: String,
    @SerializedName("written_on") val written_on: String,
    @SerializedName("example") val example: String,
    @SerializedName("thumbs_down") val thumbs_down: Int
)