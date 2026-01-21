package rs.android.launchpad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import rs.android.launchpad.ui.routes.AppRoutes
import rs.android.launchpad.ui.screen.HomeScreen
import rs.android.launchpad.ui.screen.SettingsScreen
import rs.android.launchpad.ui.theme.MVPTheme
import rs.android.launchpad.vm.HomeViewModel
import rs.android.launchpad.vm.SettingsViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVPTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val startDestination = AppRoutes.HOME_SCREEN
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = innerPadding.calculateTopPadding())
                    ) {
                        composable(route = AppRoutes.HOME_SCREEN) {
                            val homeViewModel: HomeViewModel = hiltViewModel()
                            HomeScreen(
                                message = homeViewModel.message.collectAsState().value,
                                onTappedMessage = {
                                    safeNavigateTo(navController, AppRoutes.SETTINGS_SCREEN)
                                })
                        }

                        composable(route = AppRoutes.SETTINGS_SCREEN) {
                            val settingsViewModel: SettingsViewModel = hiltViewModel()
                            SettingsScreen(
                                message = settingsViewModel.message.collectAsState().value,
                                onTappedMessage = {
                                    popBacksStackSafely(navController)
                                })
                        }
                    }
                }
            }
        }
    }

    /**
     * This prevents same destination from being navigated multiple times
     */
    fun safeNavigateTo(
        navController: NavController,
        route: String,
        popUpTo: String? = null,
        isInclusive: Boolean? = null,
        isSingleTop: Boolean? = null
    ) {
        try {
            val currentRoute = navController.currentBackStackEntry?.destination?.route
            if (currentRoute != route) {
                navController.navigate(route) {
                    launchSingleTop = true
                    if (popUpTo != null) {
                        popUpTo(popUpTo) {
                            if (isInclusive != null) {
                                inclusive = isInclusive
                            }
                        }

                    }
                    if (isSingleTop != null) {
                        launchSingleTop = isSingleTop
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun popBacksStackSafely(navController: NavController) {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack()
        }
    }
}



