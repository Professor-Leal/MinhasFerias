package br.com.rafaelleal.minhasferias.presentation_common.screens.texts

import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy

@Composable
fun PlaceHolderText(modifier: Modifier, text: String) {
    Text(
        modifier = modifier.alpha(ContentAlpha.medium),
        text = text,
        color = Navy
    )
}