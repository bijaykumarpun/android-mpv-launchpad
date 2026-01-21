package rs.android.launchpad.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun PageScaffold(
    content: @Composable () -> Unit,
    topAppBar: @Composable () -> Unit = { EmptyTopBar() }
) {
    Scaffold(topBar = topAppBar) {
        Surface(modifier = Modifier.padding(it)) {
            content()
        }
    }
}

/**
 * A simple top bar with optional title and end icon.
 * In case this is not needed, use EmptyTopBar to reserve the space.
 * */
@Composable
fun PageTopBar(
    title: String? = null,
    @DrawableRes endIcon: Int? = null,
    endIconComponent: (@Composable () -> Unit)? = null,
    onEndIconTapped: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 10.dp)
    ) {
        title?.let {
            Box(modifier = Modifier.align(Alignment.CenterStart)) {
                Text(title, style = MaterialTheme.typography.titleLarge)
            }
        }

        endIcon?.let {
            Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                IconButton(it, onEndIconTapped)
            }
        } ?: endIconComponent?.let {
            Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                endIconComponent()
            }
        }

    }
}

/**
 * An empty top bar to reserve the space for a top bar when not needed.
 */
@Composable
fun EmptyTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 10.dp)
    )
}

@Composable
private fun IconButton(
    @DrawableRes icon: Int,
    onTapped: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(46.dp)
            .padding(4.dp)
            .clip(CircleShape)
            .clickable { onTapped() }
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.padding(12.dp),
            colorFilter = ColorFilter.tint(
                MaterialTheme.colorScheme.onBackground
            )
        )
    }
}