package com.google.mediapipe.examples.llminference.data.utilities

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun internetIsConnected(): Boolean {
    return try {
        val command = "ping -c 1 google.com"
        Runtime.getRuntime().exec(command).waitFor() == 0
    } catch (e: Exception) {
        false
    }
}

fun encodeUrl(url: String): String {
    return URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
}

fun decodeUrl(encodedUrl: String): String {
    return URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString())
}