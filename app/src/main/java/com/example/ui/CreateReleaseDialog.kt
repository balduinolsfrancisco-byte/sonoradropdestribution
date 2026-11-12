package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.*
import com.example.theme.*
import java.util.*

@Composable
fun CreateReleaseDialog(
    onDismiss: () -> Unit,
    onSubmit: (Release) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var artist by remember { mutableStateOf("Sonora Sound Collective") }
    var selectedType by remember { mutableStateOf(ReleaseType.SINGLE) }
    var genre by remember { mutableStateOf("Electronic / Synthwave") }
    var recordLabel by remember { mutableStateOf("SonoraDrop Records") }
    var isCoverSong by remember { mutableStateOf(false) }
    var originalSongTitle by remember { mutableStateOf("") }
    var isExplicit by remember { mutableStateOf(false) }
    var trackName by remember { mutableStateOf("") }
    var autoISRC by remember { mutableStateOf(true) }

    val scrollState = rememberScrollState()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceElevated),
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.9f)
                .border(1.dp, SurfaceBorder, RoundedCornerShape(24.dp))
                .testTag("create_release_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudUpload,
                            contentDescription = null,
                            tint = ElectricCyan
                        )
                        Text(
                            text = "Distribute Music",
                            color = TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextSecondary
                        )
                    }
                }

                HorizontalDivider(color = SurfaceBorder, modifier = Modifier.padding(vertical = 12.dp))

                // Scrollable Form Body
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Release Title
                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            title = it
                            if (trackName.isEmpty() && selectedType == ReleaseType.SINGLE) {
                                trackName = it
                            }
                        },
                        label = { Text("Release Title *", color = TextSecondary) },
                        placeholder = { Text("e.g. Neon Horizons", color = TextTertiary) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceDark,
                            unfocusedContainerColor = SurfaceDark,
                            focusedBorderColor = ElectricCyan,
                            unfocusedBorderColor = SurfaceBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("release_title_input")
                    )

                    // Artist Name
                    OutlinedTextField(
                        value = artist,
                        onValueChange = { artist = it },
                        label = { Text("Primary Artist *", color = TextSecondary) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceDark,
                            unfocusedContainerColor = SurfaceDark,
                            focusedBorderColor = ElectricCyan,
                            unfocusedBorderColor = SurfaceBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Release Type Selector
                    Text("Release Format", color = TextSecondary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ReleaseType.values().forEach { type ->
                            val isSelected = selectedType == type
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) ElectricCyan else SurfaceDark)
                                    .border(1.dp, if (isSelected) ElectricCyan else SurfaceBorder, RoundedCornerShape(10.dp))
                                    .clickable { selectedType = type }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = type.name,
                                    color = if (isSelected) Color(0xFF001F29) else TextSecondary,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }

                    // Genre & Record Label
                    OutlinedTextField(
                        value = genre,
                        onValueChange = { genre = it },
                        label = { Text("Primary Genre", color = TextSecondary) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceDark,
                            unfocusedContainerColor = SurfaceDark,
                            focusedBorderColor = ElectricCyan,
                            unfocusedBorderColor = SurfaceBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = recordLabel,
                        onValueChange = { recordLabel = it },
                        label = { Text("Record Label Name", color = TextSecondary) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceDark,
                            unfocusedContainerColor = SurfaceDark,
                            focusedBorderColor = ElectricCyan,
                            unfocusedBorderColor = SurfaceBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Track Name (if single)
                    OutlinedTextField(
                        value = trackName,
                        onValueChange = { trackName = it },
                        label = { Text("Track 1 Title", color = TextSecondary) },
                        placeholder = { Text("Track title", color = TextTertiary) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = SurfaceDark,
                            unfocusedContainerColor = SurfaceDark,
                            focusedBorderColor = ElectricCyan,
                            unfocusedBorderColor = SurfaceBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Explicit & ISRC Toggles
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceDark)
                            .padding(horizontal = 14.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Explicit Lyrics", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Text("Contains profanity or adult content", color = TextSecondary, fontSize = 11.sp)
                        }
                        Switch(
                            checked = isExplicit,
                            onCheckedChange = { isExplicit = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = ElectricCyan, checkedTrackColor = DeepViolet)
                        )
                    }

                    // Cover Song Licensing Option (Soundrop signature feature!)
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, if (isCoverSong) NeonPurple else SurfaceBorder, RoundedCornerShape(14.dp))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Gavel,
                                            contentDescription = null,
                                            tint = NeonPurple,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = "Cover Song Mechanical License",
                                            color = TextPrimary,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Automated mechanical clearance for cover songs",
                                        color = TextSecondary,
                                        fontSize = 11.sp
                                    )
                                }
                                Switch(
                                    checked = isCoverSong,
                                    onCheckedChange = { isCoverSong = it },
                                    colors = SwitchDefaults.colors(checkedThumbColor = NeonPurple, checkedTrackColor = DeepViolet)
                                )
                            }

                            if (isCoverSong) {
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = originalSongTitle,
                                    onValueChange = { originalSongTitle = it },
                                    label = { Text("Original Song Title & Songwriter", color = TextSecondary) },
                                    placeholder = { Text("e.g. Dreams by Stevie Nicks", color = TextTertiary) },
                                    singleLine = true,
                                    shape = RoundedCornerShape(10.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = SurfaceElevated,
                                        unfocusedContainerColor = SurfaceElevated,
                                        focusedBorderColor = NeonPurple,
                                        unfocusedBorderColor = SurfaceBorder,
                                        focusedTextColor = TextPrimary,
                                        unfocusedTextColor = TextPrimary
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "✓ SonoraDrop handles mechanical royalty payouts automatically.",
                                    color = LiveGreen,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                // Submit Button
                Button(
                    onClick = {
                        if (title.isBlank()) return@Button
                        val randomUpc = "742893" + (100000 + Random().nextInt(900000)).toString()
                        val randomIsrc = "US-SD1-24-" + (10000 + Random().nextInt(90000)).toString()
                        val effectiveTrackName = if (trackName.isNotBlank()) trackName else title
                        val newRelease = Release(
                            id = "rel-${UUID.randomUUID().toString().take(6)}",
                            title = title,
                            artist = artist,
                            type = selectedType,
                            coverGradientStart = 0xFF7C4DFF,
                            coverGradientEnd = 0xFF00E5FF,
                            releaseDate = "2024-11-15",
                            genre = genre,
                            upc = randomUpc,
                            status = ReleaseStatus.IN_REVIEW,
                            tracks = listOf(
                                Track(
                                    id = "trk-${UUID.randomUUID().toString().take(6)}",
                                    trackNumber = 1,
                                    title = effectiveTrackName,
                                    duration = "3:25",
                                    isrc = randomIsrc,
                                    isExplicit = isExplicit,
                                    streamsCount = 0,
                                    writers = artist
                                )
                            ),
                            stores = com.example.data.SampleData.defaultStores,
                            isCoverSong = isCoverSong,
                            totalStreams = 0,
                            smartLinkSlug = title.lowercase().replace(" ", "-"),
                            recordLabel = recordLabel
                        )
                        onSubmit(newRelease)
                    },
                    enabled = title.isNotBlank(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ElectricCyan,
                        contentColor = Color(0xFF001F29),
                        disabledContainerColor = SurfaceDark,
                        disabledContentColor = TextTertiary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(top = 8.dp)
                        .testTag("submit_distribution_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Submit to 150+ Stores",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
