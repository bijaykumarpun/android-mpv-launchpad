package rs.android.launchpad.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rs.android.launchpad.ui.components.PageScaffold
import rs.android.launchpad.ui.components.RateAppButton

@Composable
fun HomeScreen(
    message: String,
    onTappedMessage: () -> Unit,
    bottomCtaTitle: String,
    onBottomCtaTapped: () -> Unit,
    onPaywallTapped: () -> Unit,
    onRateAppTapped: () -> Unit
) {
    PageScaffold(content = {
        HomeScreenContent(
            message = message,
            onTappedMessage = onTappedMessage,
            bottomCtaTitle = bottomCtaTitle,
            onBottomCtaTapped = onBottomCtaTapped,
            onPaywallTapped = onPaywallTapped,
            onRateAppTapped = onRateAppTapped

        )
    })

}

@Composable
private fun HomeScreenContent(
    message: String,
    onTappedMessage: () -> Unit,
    bottomCtaTitle: String,
    onBottomCtaTapped: () -> Unit,
    onPaywallTapped: () -> Unit,
    onRateAppTapped: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                message, modifier = Modifier
                    .padding(12.dp)
                    .clickable { onTappedMessage.invoke() })
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Paywall", modifier = Modifier
                    .padding(12.dp)
                    .clickable { onPaywallTapped.invoke() })
            Spacer(modifier = Modifier.height(12.dp))
            RateAppButton { onRateAppTapped.invoke() }

        }

        Text(
            bottomCtaTitle, modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
                .clickable { onBottomCtaTapped.invoke() })

    }

}