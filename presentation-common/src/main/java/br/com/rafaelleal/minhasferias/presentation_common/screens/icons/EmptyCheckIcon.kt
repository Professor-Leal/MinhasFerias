package br.com.rafaelleal.minhasferias.presentation_common.screens.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import br.com.rafaelleal.minhasferias.presentation_common.R

@Composable
fun EmptyCheckIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    IconFromResource(
        modifier = modifier.testTag("EmptyCheckIcon"),
        drawable = R.drawable.crop_square,
        contentDescription = "Empty Check Icon",
        onClick = onClick
    )
}

@Preview
@Composable
fun EmptyCheckIconPreview() {
    EmptyCheckIcon(Modifier)
}