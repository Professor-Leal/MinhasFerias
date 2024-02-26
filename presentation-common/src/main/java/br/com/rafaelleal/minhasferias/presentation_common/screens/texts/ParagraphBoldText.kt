package br.com.rafaelleal.minhasferias.presentation_common.screens.texts

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy

@Composable
fun ParagraphBoldText(text: String) {
    Text(
        modifier = Modifier,
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Navy
    )
}

@Preview
@Composable
fun ParagraphBoldTextPreview() {
    ParagraphBoldText("Texto de parágrafo negrito")
}