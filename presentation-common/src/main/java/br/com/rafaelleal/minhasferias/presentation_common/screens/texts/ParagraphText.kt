package br.com.rafaelleal.minhasferias.presentation_common.screens.texts

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Black

@Composable
fun ParagraphText( text: String , modifier: Modifier) {
    Text(
        modifier = modifier.padding(horizontal = 12.dp), text = text,
        fontSize = 18.sp, color = Black
    )
}

@Preview
@Composable
fun ParagraphTextPreview() {
    ParagraphText("Texto simples de parágrafo fonte 18, cor preta", modifier = Modifier)
}