package com.mymusic.datasource


import com.mymusic.other.nework.ApiClient
import com.topchart.topchart.TopSongs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TopSongsRemoteDataSource(apiClient: ApiClient) : TopSongsDataSource {

    private var call: Call<TopSongs>? = null
    private val service = apiClient.build()


     override fun retrieveFeed(callback: OnResultCallback<TopSongs>) {

        call = service?.getFeed()
        call?.enqueue(object : Callback<TopSongs> {
            override fun onFailure(call: Call<TopSongs>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<TopSongs>,
                response: Response<TopSongs>
            ) {
                response.body()?.let {
                    if (response.isSuccessful) {
                        callback.onSuccess(it)
                    } else {
                        callback.onError("Opps, Try Again")
                    }
                }
            }
        })
    }

     override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}