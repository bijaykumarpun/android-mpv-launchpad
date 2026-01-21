package rs.android.launchpad.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rs.android.launchpad.ui.components.PageScaffold


@Composable
fun SettingsScreen(message: String, onTappedMessage: () -> Unit) {
    PageScaffold(content = {
        SettingsScreenContent(message = message, onTappedMessage = onTappedMessage)
    })
}

@Composable
fun SettingsScreenContent(message: String, onTappedMessage: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            message, modifier = Modifier
                .padding(12.dp)
                .clickable { onTappedMessage.invoke() })
    }
}