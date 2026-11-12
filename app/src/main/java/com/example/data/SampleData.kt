package com.example.data

import com.example.model.*

object SampleData {

    val defaultArtist = ArtistProfile(
        name = "Balduino Francisco",
        stageName = "Sonora Sound Collective",
        email = "balduinolsfrancisco@gmail.com",
        bio = "Independent producer and multi-instrumentalist crafting electronic soundscapes, rhythmic grooves, and immersive audio experiences.",
        genre = "Electronic / Synthwave / Global Beats",
        isVerified = true,
        monthlyListeners = 245200,
        followers = 48900
    )

    val defaultStores = listOf(
        StoreDistribution("Spotify", "spotify", true),
        StoreDistribution("Apple Music", "apple_music", true),
        StoreDistribution("TikTok & ByteDance", "tiktok", true),
        StoreDistribution("YouTube Music & Content ID", "youtube", true),
        StoreDistribution("Amazon Music", "amazon", true),
        StoreDistribution("Tidal", "tidal", true),
        StoreDistribution("Deezer", "deezer", true),
        StoreDistribution("Instagram & Meta", "meta", true),
        StoreDistribution("Pandora", "pandora", true),
        StoreDistribution("Shazam", "shazam", true)
    )

    val initialReleases = listOf(
        Release(
            id = "rel-001",
            title = "Neon Horizons",
            artist = "Sonora Sound Collective",
            type = ReleaseType.SINGLE,
            coverGradientStart = 0xFF7C4DFF,
            coverGradientEnd = 0xFF00E5FF,
            releaseDate = "2024-09-15",
            genre = "Electronic / Synthwave",
            upc = "742893120912",
            status = ReleaseStatus.LIVE,
            tracks = listOf(
                Track(
                    id = "trk-001",
                    trackNumber = 1,
                    title = "Neon Horizons (Original Mix)",
                    duration = "3:42",
                    isrc = "US-SD1-24-00101",
                    isExplicit = false,
                    streamsCount = 684200,
                    writers = "Balduino Francisco"
                )
            ),
            stores = defaultStores,
            isCoverSong = false,
            totalStreams = 684200,
            smartLinkSlug = "neon-horizons",
            recordLabel = "SonoraDrop Records"
        ),
        Release(
            id = "rel-002",
            title = "Midnight Sessions",
            artist = "Sonora Sound Collective ft. Luna Rose",
            type = ReleaseType.EP,
            coverGradientStart = 0xFFFF4081,
            coverGradientEnd = 0xFF7C4DFF,
            releaseDate = "2024-08-01",
            genre = "Lo-Fi Beats / R&B",
            upc = "742893120913",
            status = ReleaseStatus.LIVE,
            tracks = listOf(
                Track("trk-002", 1, "Midnight Rain", "2:34", "US-SD1-24-00201", false, 281400, "B. Francisco, L. Rose"),
                Track("trk-003", 2, "Coffee & Vinyl", "3:10", "US-SD1-24-00202", false, 142000, "B. Francisco"),
                Track("trk-004", 3, "City Lights at 3AM", "2:55", "US-SD1-24-00203", false, 89000, "B. Francisco, K. Vance")
            ),
            stores = defaultStores,
            isCoverSong = false,
            totalStreams = 512400,
            smartLinkSlug = "midnight-sessions",
            recordLabel = "SonoraDrop Records"
        ),
        Release(
            id = "rel-003",
            title = "Tropics & Rhythm",
            artist = "Sonora Sound Collective",
            type = ReleaseType.ALBUM,
            coverGradientStart = 0xFFFFAB00,
            coverGradientEnd = 0xFFFF1744,
            releaseDate = "2024-06-20",
            genre = "Afrobeat / Latin Grooves",
            upc = "742893120914",
            status = ReleaseStatus.LIVE,
            tracks = listOf(
                Track("trk-005", 1, "Bahia Sunrise", "3:18", "US-SD1-24-00301", false, 94200, "B. Francisco"),
                Track("trk-006", 2, "Djembe Fever", "3:45", "US-SD1-24-00302", false, 82500, "B. Francisco, M. Diallo"),
                Track("trk-007", 3, "Bossa Nova Chill", "2:48", "US-SD1-24-00303", false, 72200, "B. Francisco")
            ),
            stores = defaultStores,
            isCoverSong = false,
            totalStreams = 248900,
            smartLinkSlug = "tropics-rhythm",
            recordLabel = "SonoraDrop Records"
        ),
        Release(
            id = "rel-004",
            title = "Cyber Pulse",
            artist = "Sonora Sound Collective",
            type = ReleaseType.SINGLE,
            coverGradientStart = 0xFF00E5FF,
            coverGradientEnd = 0xFF18FFFF,
            releaseDate = "2024-11-20",
            genre = "Cyberpunk / Industrial Bass",
            upc = "742893120915",
            status = ReleaseStatus.IN_REVIEW,
            tracks = listOf(
                Track("trk-008", 1, "Cyber Pulse", "4:12", "US-SD1-24-00401", true, 0, "B. Francisco")
            ),
            stores = defaultStores,
            isCoverSong = false,
            totalStreams = 0,
            smartLinkSlug = "cyber-pulse",
            recordLabel = "SonoraDrop Records"
        ),
        Release(
            id = "rel-005",
            title = "Dreams (Acoustic Cover)",
            artist = "Sonora Sound Collective",
            type = ReleaseType.SINGLE,
            coverGradientStart = 0xFF69F0AE,
            coverGradientEnd = 0xFF00B0FF,
            releaseDate = "2024-12-01",
            genre = "Indie Folk / Acoustic",
            upc = "742893120916",
            status = ReleaseStatus.SCHEDULED,
            tracks = listOf(
                Track("trk-009", 1, "Dreams (Acoustic Version)", "3:30", "US-SD1-24-00501", false, 0, "Stevie Nicks (Cover)")
            ),
            stores = defaultStores,
            isCoverSong = true,
            totalStreams = 0,
            smartLinkSlug = "dreams-cover",
            recordLabel = "SonoraDrop Distribution"
        )
    )

    val initialSplits = listOf(
        RoyaltySplit("sp-01", "Balduino Francisco", "balduinolsfrancisco@gmail.com", "Primary Artist & Producer", 60, "balduinolsfrancisco@gmail.com"),
        RoyaltySplit("sp-02", "Luna Rose", "lunarose.music@gmail.com", "Featured Vocalist", 25, "lunarose.payout@gmail.com"),
        RoyaltySplit("sp-03", "Marcus Vance", "vancemixing@studio.com", "Mixing & Mastering Engineer", 15, "marcus.vance@studio.com")
    )

    val platformShares = listOf(
        PlatformShare("Spotify", 48, 693800, 0xFF1DB954),
        PlatformShare("Apple Music", 28, 404700, 0xFFFC3C44),
        PlatformShare("TikTok / Reels", 14, 202300, 0xFF25F4EE),
        PlatformShare("YouTube Music", 6, 86700, 0xFFFF0000),
        PlatformShare("Amazon / Tidal / Others", 4, 58000, 0xFF00A8E1)
    )

    val topTerritories = listOf(
        TerritoryStat("United States", "🇺🇸", 540200, 37),
        TerritoryStat("Brazil", "🇧🇷", 380400, 26),
        TerritoryStat("United Kingdom", "🇬🇧", 195300, 14),
        TerritoryStat("Germany", "🇩🇪", 142100, 10),
        TerritoryStat("Japan", "🇯🇵", 98500, 7),
        TerritoryStat("Others", "🌍", 89000, 6)
    )

    val dailyStreamsLast7Days = listOf(
        DailyStreamPoint("Mon", 18450),
        DailyStreamPoint("Tue", 22100),
        DailyStreamPoint("Wed", 21800),
        DailyStreamPoint("Thu", 25400),
        DailyStreamPoint("Fri", 34200),
        DailyStreamPoint("Sat", 41800),
        DailyStreamPoint("Sun", 38900)
    )

    val initialPayouts = listOf(
        PayoutTransaction("PO-9841", "2024-11-01", 620.50, "PayPal", "balduinolsfrancisco@gmail.com", "Completed", "REF-849102-SD"),
        PayoutTransaction("PO-9214", "2024-10-01", 545.80, "Bank Wire / PIX", "BR-Bank-****9412", "Completed", "REF-742918-SD"),
        PayoutTransaction("PO-8812", "2024-09-01", 710.25, "PayPal", "balduinolsfrancisco@gmail.com", "Completed", "REF-653912-SD"),
        PayoutTransaction("PO-8104", "2024-08-01", 489.10, "PayPal", "balduinolsfrancisco@gmail.com", "Completed", "REF-584021-SD")
    )
}
