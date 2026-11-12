package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Release
import com.example.theme.DeepBackground
import com.example.theme.ElectricCyan
import com.example.theme.SonoraDropTheme
import com.example.theme.SurfaceDark
import com.example.theme.TextSecondary
import com.example.ui.*
import com.example.viewmodel.SonoraDropViewModel

enum class Screen(val title: String, val icon: ImageVector) {
    DASHBOARD("Dashboard", Icons.Default.Dashboard),
    RELEASES("Catalog", Icons.Default.LibraryMusic),
    ANALYTICS("Analytics", Icons.Default.Insights),
    ROYALTIES("Royalties", Icons.Default.Payments),
    PROFILE("Artist", Icons.Default.Person)
}

class MainActivity : ComponentActivity() {

    private val viewModel: SonoraDropViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SonoraDropTheme {
                MainAppContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppContent(viewModel: SonoraDropViewModel) {
    val context = LocalContext.current
    var currentScreen by remember { mutableStateOf(Screen.DASHBOARD) }
    val selectedRelease by viewModel.selectedRelease.collectAsState()
    var showCreateReleaseDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DeepBackground,
        bottomBar = {
            if (selectedRelease == null) {
                NavigationBar(
                    containerColor = SurfaceDark,
                    tonalElevation = 8.dp,
                    windowInsets = WindowInsets.navigationBars,
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                    Screen.values().forEach { screen ->
                        val isSelected = currentScreen == screen
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { currentScreen = screen },
                            icon = {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = screen.title
                                )
                            },
                            label = {
                                Text(
                                    text = screen.title,
                                    fontSize = 11.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF001F29),
                                selectedTextColor = ElectricCyan,
                                indicatorColor = ElectricCyan,
                                unselectedIconColor = TextSecondary,
                                unselectedTextColor = TextSecondary
                            ),
                            modifier = Modifier.testTag("nav_item_${screen.name.lowercase()}")
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            if (selectedRelease == null && (currentScreen == Screen.DASHBOARD || currentScreen == Screen.RELEASES)) {
                FloatingActionButton(
                    onClick = { showCreateReleaseDialog = true },
                    containerColor = ElectricCyan,
                    contentColor = Color(0xFF001F29),
                    modifier = Modifier.testTag("fab_distribute_music")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "New Release"
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (selectedRelease != null) {
                ReleaseDetailScreen(
                    release = selectedRelease!!,
                    onBackClick = { viewModel.selectRelease(null) }
                )
            } else {
                when (currentScreen) {
                    Screen.DASHBOARD -> DashboardScreen(
                        viewModel = viewModel,
                        onNavigateToReleases = { currentScreen = Screen.RELEASES },
                        onNavigateToAnalytics = { currentScreen = Screen.ANALYTICS },
                        onNavigateToRoyalties = { currentScreen = Screen.ROYALTIES },
                        onNewReleaseClick = { showCreateReleaseDialog = true },
                        onReleaseClick = { viewModel.selectRelease(it) }
                    )
                    Screen.RELEASES -> ReleasesScreen(
                        viewModel = viewModel,
                        onReleaseClick = { viewModel.selectRelease(it) },
                        onNewReleaseClick = { showCreateReleaseDialog = true }
                    )
                    Screen.ANALYTICS -> AnalyticsScreen(
                        viewModel = viewModel
                    )
                    Screen.ROYALTIES -> RoyaltiesScreen(
                        viewModel = viewModel
                    )
                    Screen.PROFILE -> ArtistProfileScreen(
                        viewModel = viewModel
                    )
                }
            }
        }
    }

    if (showCreateReleaseDialog) {
        CreateReleaseDialog(
            onDismiss = { showCreateReleaseDialog = false },
            onSubmit = { newRelease ->
                viewModel.addRelease(newRelease)
                Toast.makeText(context, "Submitted '${newRelease.title}' to 150+ stores!", Toast.LENGTH_LONG).show()
                showCreateReleaseDialog = false
            }
        )
    }
}
