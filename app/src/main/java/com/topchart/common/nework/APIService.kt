package com.topchart.common.nework

import com.topchart.topchart.TopSongs
import retrofit2.Call
import retrofit2.http.GET


interface APIService {
    @GET("us/music/most-played/60/songs.json")
    fun getFeed(): Call<TopSongs>
}