package com.shrutiapp.data

import java.io.Serializable

data class Song(
    var id: String,
    var title: String,
    var displayName: String,
    var size: String,
    var duration: String,
    var path: String,
    var dateAdded: String,

    ) : Serializable {
    var bgImage: Int = 0
}
