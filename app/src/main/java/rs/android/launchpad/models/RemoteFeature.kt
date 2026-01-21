package rs.android.launchpad.models

sealed interface ClickAction {
    object NavigateToPlayStore : ClickAction
    object NavigateToLocalScreen : ClickAction
    object None : ClickAction
    companion object {
        fun fromString(action: String): ClickAction {
            return when (action) {
                "NavigateToPlayStore" -> NavigateToPlayStore
                "NavigateToLocalScreen" -> NavigateToLocalScreen
                else -> None
            }
        }
    }
}

data class RemoteFeature(
    val id: String,
    val title: String,
    val clickAction: ClickAction,
    val data: String,
    val skipForPro: Boolean
) {
    object Ids {
        const val ID_HOME_SCREEN_CLICK_BUTTON = "home-screen-id"
    }
}
