package br.com.rafaelleal.minhasferias.presentation_common.screens.icons

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.rafaelleal.minhasferias.presentation_common.R
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy

@Composable
fun IconFromResource(
    modifier: Modifier = Modifier,
    drawable: Int,
    contentDescription: String = "Default Icon",
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            contentDescription = contentDescription,
            tint = Navy,
            painter = painterResource(drawable)
        )
    }
}

@Preview
@Composable
fun IconFromResourcePreview() {
    IconFromResource(Modifier, R.drawable.crop_square)
}
