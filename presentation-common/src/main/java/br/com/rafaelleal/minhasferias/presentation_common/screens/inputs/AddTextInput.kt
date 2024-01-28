package br.com.rafaelleal.minhasferias.presentation_common.screens.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.White


@Composable
fun AddTextInput(label: String, text: String, onValueChange: (inputText: String) -> Unit = {}) {
    OutlinedTextField(
        value = text,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                label,
                style = TextStyle(
                    color = MaterialTheme.colors.primaryVariant,
                ),
                fontSize = 18.sp
            )
        },
        textStyle = TextStyle(fontSize = 18.sp, fontFamily = FontFamily.Default),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = White,
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.primary,
            focusedLabelColor = MaterialTheme.colors.primary,
            cursorColor = MaterialTheme.colors.primaryVariant
        ),
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize()
            .fillMaxWidth(1f)
            .testTag(label)
    )
}

@Preview(showBackground = true)
@Composable
fun AddTextInputPreview() {
    AddTextInput("Nome do Evento", "Evento Bacana") {

    }
}