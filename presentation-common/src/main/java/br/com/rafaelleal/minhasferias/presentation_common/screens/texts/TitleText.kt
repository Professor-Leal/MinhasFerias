package br.com.rafaelleal.minhasferias.presentation_common.screens.texts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy

@Composable
fun TitleText(title: String = "Título") {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .testTag("TitleText")

    ) {
        Text(
            text = title, fontSize = 24.sp, textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold, color = Navy
        )
    }
}

@Preview
@Composable
fun TitleTextPreview() {
    TitleText()
}