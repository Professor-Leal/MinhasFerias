package br.com.rafaelleal.minhasferias.presentation_common.screens.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun CloseIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    DefaultIcon(
        modifier = modifier,
        icon = Icons.Default.Close,
        contentDescription = "Close Icon",
        onClick = onClick
    )
}

@Preview
@Composable
fun CloseIconPreview() {
    CloseIcon(Modifier)
}