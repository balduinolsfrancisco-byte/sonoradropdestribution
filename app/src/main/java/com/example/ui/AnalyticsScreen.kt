package com.example.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleData
import com.example.theme.*
import com.example.viewmodel.SonoraDropViewModel

@Composable
fun AnalyticsScreen(
    viewModel: SonoraDropViewModel
) {
    val timeframe by viewModel.analyticsTimeframe.collectAsState()
    val releases by viewModel.releases.collectAsState()
    val artist by viewModel.artistProfile.collectAsState()

    val totalStreams = releases.sumOf { it.totalStreams }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBackground)
            .testTag("analytics_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Screen Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Streaming Analytics",
                        color = TextPrimary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Real-time stream telemetry & listener demographics",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Timeframe selector
        item {
            val timeframes = listOf("7D", "30D", "90D", "1Y", "All")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceDark)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                timeframes.forEach { tf ->
                    val isSelected = timeframe == tf
                    Button(
                        onClick = { viewModel.setTimeframe(tf) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) ElectricCyan else Color.Transparent,
                            contentColor = if (isSelected) Color(0xFF001F29) else TextSecondary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = tf,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }

        // KPI Summary Cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricCard(
                    title = "Total Plays",
                    value = "${String.format("%,d", totalStreams)}",
                    subtitle = "↗ +22.4% vs last period",
                    icon = Icons.Default.PlayArrow,
                    iconTint = ElectricCyan,
                    modifier = Modifier.weight(1f)
                )
                MetricCard(
                    title = "Unique Listeners",
                    value = "${String.format("%,d", artist.monthlyListeners)}",
                    subtitle = "182 countries",
                    icon = Icons.Default.Public,
                    iconTint = NeonPurple,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Streams Trend Canvas Chart
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceElevated),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, SurfaceBorder, RoundedCornerShape(18.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Daily Streams Velocity",
                                color = TextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Peak: 41,800 streams / day",
                                color = LiveGreen,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ShowChart,
                            contentDescription = null,
                            tint = ElectricCyan
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Custom Compose Canvas Line & Area Chart
                    val points = SampleData.dailyStreamsLast7Days
                    val maxVal = (points.maxOfOrNull { it.streamCount } ?: 50000L).toFloat()

                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                    ) {
                        val width = size.width
                        val height = size.height
                        val stepX = width / (points.size - 1)

                        val path = Path()
                        val fillPath = Path()

                        points.forEachIndexed { i, p ->
                            val x = i * stepX
                            val y = height - (p.streamCount.toFloat() / maxVal * (height * 0.85f))
                            if (i == 0) {
                                path.moveTo(x, y)
                                fillPath.moveTo(x, height)
                                fillPath.lineTo(x, y)
                            } else {
                                val prevX = (i - 1) * stepX
                                val prevY = height - (points[i - 1].streamCount.toFloat() / maxVal * (height * 0.85f))
                                val controlX1 = prevX + (x - prevX) / 2
                                val controlX2 = prevX + (x - prevX) / 2
                                path.cubicTo(controlX1, prevY, controlX2, y, x, y)
                                fillPath.cubicTo(controlX1, prevY, controlX2, y, x, y)
                            }
                        }

                        fillPath.lineTo(width, height)
                        fillPath.close()

                        // Draw Gradient Area under curve
                        drawPath(
                            path = fillPath,
                            brush = Brush.verticalGradient(
                                colors = listOf(ElectricCyan.copy(alpha = 0.35f), Color.Transparent),
                                startY = 0f,
                                endY = height
                            )
                        )

                        // Draw Curve Stroke
                        drawPath(
                            path = path,
                            color = ElectricCyan,
                            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                        )

                        // Draw data points
                        points.forEachIndexed { i, p ->
                            val x = i * stepX
                            val y = height - (p.streamCount.toFloat() / maxVal * (height * 0.85f))
                            drawCircle(
                                color = DeepBackground,
                                radius = 4.dp.toPx(),
                                center = Offset(x, y)
                            )
                            drawCircle(
                                color = ElectricCyan,
                                radius = 2.5.dp.toPx(),
                                center = Offset(x, y)
                            )
                        }
                    }

                    // Days labels
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        points.forEach { p ->
                            Text(
                                text = p.dayLabel,
                                color = TextTertiary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // Platform Breakdown
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceElevated),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, SurfaceBorder, RoundedCornerShape(18.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Distribution Platform Share",
                        color = TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    SampleData.platformShares.forEach { share ->
                        Column(modifier = Modifier.padding(vertical = 4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = share.platformName,
                                    color = TextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "${share.percentage}% (${String.format("%,d", share.streams)})",
                                    color = TextSecondary,
                                    fontSize = 12.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { share.percentage / 100f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = Color(share.brandColorHex),
                                trackColor = SurfaceDark,
                            )
                        }
                    }
                }
            }
        }

        // Top Territories
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceElevated),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, SurfaceBorder, RoundedCornerShape(18.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Top Listener Territories",
                        color = TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    SampleData.topTerritories.forEach { territory ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(territory.flagEmoji, fontSize = 16.sp)
                                Text(territory.country, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "${String.format("%,d", territory.streams)}",
                                    color = TextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "${territory.percentage}%",
                                    color = ElectricCyan,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
