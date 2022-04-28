package com.topchart.topchart

import com.google.gson.annotations.SerializedName


data class TopSongs (

    @SerializedName("feed" ) var feed : Feed? = Feed()

)

data class Feed (

    @SerializedName("title"     ) var title     : String?            = null,
    @SerializedName("id"        ) var id        : String?            = null,
    @SerializedName("author"    ) var author    : Author?            = Author(),
    @SerializedName("links"     ) var links     : ArrayList<Links>   = arrayListOf(),
    @SerializedName("copyright" ) var copyright : String?            = null,
    @SerializedName("country"   ) var country   : String?            = null,
    @SerializedName("icon"      ) var icon      : String?            = null,
    @SerializedName("updated"   ) var updated   : String?            = null,
    @SerializedName("results"   ) var songs   : ArrayList<Results> = arrayListOf()

)


data class Author (

    @SerializedName("name" ) var name : String? = null,
    @SerializedName("url"  ) var url  : String? = null

)

data class Genres (

    @SerializedName("genreId" ) var genreId : String? = null,
    @SerializedName("name"    ) var name    : String? = null,
    @SerializedName("url"     ) var url     : String? = null

)

data class Links (

    @SerializedName("self" ) var self : String? = null

)

data class Results (

    @SerializedName("artistName"            ) var artistName            : String?           = null,
    @SerializedName("id"                    ) var id                    : String?           = null,
    @SerializedName("name"                  ) var name                  : String?           = null,
    @SerializedName("releaseDate"           ) var releaseDate           : String?           = null,
    @SerializedName("kind"                  ) var kind                  : String?           = null,
    @SerializedName("artistId"              ) var artistId              : String?           = null,
    @SerializedName("artistUrl"             ) var artistUrl             : String?           = null,
    @SerializedName("contentAdvisoryRating" ) var contentAdvisoryRating : String?           = null,
    @SerializedName("artworkUrl100"         ) var artworkUrl100         : String?           = null,
    @SerializedName("genres"                ) var genres                : ArrayList<Genres> = arrayListOf(),
    @SerializedName("url"                   ) var url                   : String?           = null

)