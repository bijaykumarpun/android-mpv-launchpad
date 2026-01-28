package rs.android.launchpad

import android.os.Bundle
import android.widget.Toast
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
import rs.android.launchpad.models.ClickAction
import rs.android.launchpad.models.RemoteFeature
import rs.android.launchpad.ui.routes.AppRoutes
import rs.android.launchpad.ui.screen.HomeScreen
import rs.android.launchpad.ui.screen.PaywallScreen
import rs.android.launchpad.ui.screen.SettingsScreen
import rs.android.launchpad.ui.theme.MVPTheme
import rs.android.launchpad.ui.vm.HomeViewModel
import rs.android.launchpad.ui.vm.PaywallViewModel
import rs.android.launchpad.ui.vm.SettingsViewModel
import rs.android.launchpad.util.vendor.RatingUtils

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
                                    if (homeViewModel.isFeatureEnabled.value) {
                                        safeNavigateTo(navController, AppRoutes.SETTINGS_SCREEN)
                                    } else {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Feature not enabled",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                                bottomCtaTitle = homeViewModel.bottomRemoteFeature.collectAsState().value?.title
                                    ?: "",
                                onBottomCtaTapped = {
                                    homeViewModel.bottomRemoteFeature.value?.let {
                                        handleRemoteFeature(
                                            navController,
                                            it
                                        )
                                    }

                                },
                                onPaywallTapped = {
                                    safeNavigateTo(navController, AppRoutes.PAYWALL_SCREEN)
                                },
                                onRateAppTapped = {
                                    RatingUtils.requestInAppRating(
                                        this@MainActivity,
                                        fallbackToPlayStore = true
                                    )
                                })

                            composable(route = AppRoutes.SETTINGS_SCREEN) {
                                val settingsViewModel: SettingsViewModel = hiltViewModel()
                                SettingsScreen(
                                    message = settingsViewModel.message.collectAsState().value,
                                    onTappedMessage = {
                                        popBacksStackSafely(navController)
                                    })
                            }

                            composable(route = AppRoutes.PAYWALL_SCREEN) {
                                val paywallViewModel: PaywallViewModel = hiltViewModel()
                                PaywallScreen(
                                    isUserPro = paywallViewModel.isUserPro.collectAsState().value,
                                    message = paywallViewModel.message.collectAsState().value,
                                    onPurchaseTapped = { paywallViewModel.onPurchaseTapped(this@MainActivity) },
                                    onTappedMessage = {
                                        popBacksStackSafely(navController)
                                    }
                                )
                            }
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


    fun handleRemoteFeature(navController: NavController, feature: RemoteFeature) {
        when (feature.clickAction) {
            ClickAction.None -> {}
            ClickAction.NavigateToLocalScreen
                -> {
                try {
                    safeNavigateTo(navController = navController, route = feature.data)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            ClickAction.NavigateToPlayStore -> {
                try {
                    RatingUtils.openAppInPlayStore(this@MainActivity, feature.data)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }
}






