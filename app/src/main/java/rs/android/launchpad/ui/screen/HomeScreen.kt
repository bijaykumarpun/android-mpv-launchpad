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

@Composable
fun HomeScreen(
    message: String,
    onTappedMessage: () -> Unit,
    bottomCtaTitle: String,
    onBottomCtaTapped: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            message, modifier = Modifier
                .padding(12.dp)
                .clickable { onTappedMessage.invoke() })

        Text(
            bottomCtaTitle, modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
                .clickable { onBottomCtaTapped.invoke() })
    }
}