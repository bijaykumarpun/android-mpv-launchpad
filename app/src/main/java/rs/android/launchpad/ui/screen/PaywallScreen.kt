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

@Composable
fun PaywallScreen(
    isUserPro: Boolean,
    message: String,
    onTappedMessage: () -> Unit,
    onPurchaseTapped: () -> Unit
) {
    PageScaffold(content = {
        PaywallScreenContent(
            isUserPro = isUserPro,
            message = message,
            onTappedMessage = onTappedMessage,
            onPurchaseTapped = onPurchaseTapped
        )
    })
}


@Composable
private fun PaywallScreenContent(
    isUserPro: Boolean,
    message: String,
    onTappedMessage: () -> Unit,
    onPurchaseTapped: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            Text(
                message, modifier = Modifier
                    .padding(12.dp)
                    .clickable { onTappedMessage.invoke() })
            Spacer(modifier = Modifier.height(12.dp))
            if (isUserPro.not()) {
                Text(
                    "Purchase", modifier = Modifier
                        .padding(12.dp)
                        .clickable { onPurchaseTapped.invoke() })
            }

        }

    }
}