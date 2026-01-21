package rs.android.launchpad.ui.routes

sealed interface AppRoutes {
    companion object {
        const val HOME_SCREEN = "home"
        const val SETTINGS_SCREEN = "settings"
    }
}