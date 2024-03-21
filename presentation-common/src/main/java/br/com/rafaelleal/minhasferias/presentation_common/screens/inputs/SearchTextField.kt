package br.com.rafaelleal.minhasferias.presentation_common.screens.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.rafaelleal.minhasferias.presentation_common.screens.icons.CloseIcon
import br.com.rafaelleal.minhasferias.presentation_common.screens.icons.SearchIcon
import br.com.rafaelleal.minhasferias.presentation_common.screens.texts.PlaceHolderText
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue90
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy

// Referência: https://www.youtube.com/watch?v=jYJKX_7l9H4
// Programador de Elite - [COMPOSE SEARCH] COMO CRIAR CAMPO DE BUSCA NA TOPBAR EM JETPACK COMPOSE NO ANDROID

@Composable
fun SearchTextField(
    currentSearchText: String,
    onSearchTextChanged: (String) -> Unit = {},
    onSearchDeactivated: () -> Unit = {},
    onSearchDispatched: (String) -> Unit = {},
    placeHolder: String = ""
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .heightIn(0.dp, 60.dp)
            .testTag("SearchTextField"),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = Blue90,
        shape = RoundedCornerShape(50)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth().testTag("SearchTextFieldInput"),
            value = currentSearchText,
            onValueChange = { onSearchTextChanged(it) },
            placeholder = {
                PlaceHolderText(modifier = Modifier, placeHolder)
            },
            singleLine = true,
            leadingIcon = {
                SearchIcon(modifier = Modifier) {
                    onSearchDispatched(currentSearchText)
                }
            },
            trailingIcon = {
                if (currentSearchText.isNotEmpty()) {
                    CloseIcon(modifier = Modifier) {
                        onSearchTextChanged("")
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions =
            KeyboardActions(onSearch = { onSearchDispatched(currentSearchText) }),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Navy
            ),
            textStyle = TextStyle.Default.copy(fontSize = 18.sp)
        )
    }
}

@Preview
@Composable
fun SearchTextFieldPreview() {
    SearchTextField(currentSearchText = "", placeHolder = "Search something")
}

@Preview
@Composable
fun SearchTextFieldFilledPreview() {
    SearchTextField(currentSearchText = "some input", placeHolder = "Search something")
}