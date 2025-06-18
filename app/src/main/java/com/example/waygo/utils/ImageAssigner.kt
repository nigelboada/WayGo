package com.example.waygo.utils

fun assignHotelImage(hotelName: String): String {
    return when (hotelName.lowercase()) {
        "hotel ramblas" -> "https://picsum.photos/id/1018/800/400"
        "hotel gaudí" -> "https://picsum.photos/id/212/2000/1394"
        "hotel sagrada familia" -> "https://picsum.photos/id/1037/800/400"

        "hotel louvre" -> "https://picsum.photos/id/164/1200/800"
        "hotel bastille" -> "https://picsum.photos/id/193/3578/2451"
        "hotel montmartre" -> "https://picsum.photos/id/188/2896/1936"

        "hotel westminster" -> "https://picsum.photos/id/11/2500/1667"
        "hotel soho" -> "https://picsum.photos/id/128/3823/2549"
        "hotel kensington" -> "https://picsum.photos/id/57/2448/3264"

        else -> "https://picsum.photos/800/400"
    }
}

fun assignRoomImage(roomType: String): String {
    return when (roomType.lowercase()) {
        "single" -> "https://picsum.photos/id/1011/600/400"
        "double" -> "https://picsum.photos/id/1012/600/400"
        "suite" -> "https://picsum.photos/id/1013/600/400"
        else -> "https://picsum.photos/600/400"
    }
}
