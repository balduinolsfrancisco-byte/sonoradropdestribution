package com.example.model

enum class ReleaseType {
    SINGLE, EP, ALBUM
}

enum class ReleaseStatus {
    LIVE, IN_REVIEW, SCHEDULED, DRAFT
}

data class Track(
    val id: String,
    val trackNumber: Int,
    val title: String,
    val duration: String,
    val isrc: String,
    val isExplicit: Boolean = false,
    val streamsCount: Long = 0,
    val writers: String = ""
)

data class StoreDistribution(
    val name: String,
    val key: String,
    val isLive: Boolean = true
)

data class Release(
    val id: String,
    val title: String,
    val artist: String,
    val type: ReleaseType,
    val coverGradientStart: Long,
    val coverGradientEnd: Long,
    val releaseDate: String,
    val genre: String,
    val upc: String,
    val status: ReleaseStatus,
    val tracks: List<Track>,
    val stores: List<StoreDistribution>,
    val isCoverSong: Boolean = false,
    val totalStreams: Long = 0,
    val smartLinkSlug: String = "",
    val recordLabel: String = "SonoraDrop Records"
)

data class RoyaltySplit(
    val id: String,
    val collaboratorName: String,
    val email: String,
    val role: String,
    val percentage: Int,
    val payoutEmail: String
)

data class DailyStreamPoint(
    val dayLabel: String,
    val streamCount: Long
)

data class PlatformShare(
    val platformName: String,
    val percentage: Int,
    val streams: Long,
    val brandColorHex: Long
)

data class TerritoryStat(
    val country: String,
    val flagEmoji: String,
    val streams: Long,
    val percentage: Int
)

data class PayoutTransaction(
    val id: String,
    val date: String,
    val amount: Double,
    val method: String,
    val destination: String,
    val status: String,
    val reference: String
)

data class ArtistProfile(
    val name: String,
    val stageName: String,
    val email: String,
    val bio: String,
    val genre: String,
    val isVerified: Boolean = true,
    val monthlyListeners: Long = 245200,
    val followers: Long = 48900
)
