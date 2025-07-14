package com.apps.aivisioncmp.data.model

sealed class ImageGenerationStatus {
    data class Generated(val base64:String): ImageGenerationStatus()
    object Downloading: ImageGenerationStatus()
    object Completed: ImageGenerationStatus()
    data class GenerationError(val error:String): ImageGenerationStatus()
    data class DownloadError(val url:String): ImageGenerationStatus()
}