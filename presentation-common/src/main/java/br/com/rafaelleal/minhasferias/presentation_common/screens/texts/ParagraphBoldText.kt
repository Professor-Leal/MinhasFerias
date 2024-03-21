package br.com.rafaelleal.minhasferias.presentation_common.screens.texts

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy

@Composable
fun ParagraphBoldText(text: String = "Texto de parágrafo negrito", color: Color = Navy, fontSize: TextUnit = 18.sp) {
    Text(
        modifier = Modifier,
        text = text,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        color = color
    )
}

@Preview
@Composable
fun ParagraphBoldTextPreview() {
    ParagraphBoldText()
}