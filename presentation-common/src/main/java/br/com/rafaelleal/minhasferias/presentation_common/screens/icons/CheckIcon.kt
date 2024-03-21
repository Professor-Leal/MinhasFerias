package br.com.rafaelleal.minhasferias.presentation_common.screens.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CheckIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    DefaultIcon(
        modifier = modifier.testTag("CheckIcon"),
        icon = Icons.Default.Check,
        contentDescription = "Check Icon",
        onClick = onClick
    )
}

@Preview
@Composable
fun CheckIconPreview() {
    CheckIcon(Modifier)
}