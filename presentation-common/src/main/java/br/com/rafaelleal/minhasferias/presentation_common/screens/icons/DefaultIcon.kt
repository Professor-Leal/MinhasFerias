package br.com.rafaelleal.minhasferias.presentation_common.screens.icons

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.White

@Composable
fun DefaultIcon(
    modifier : Modifier,
    icon: ImageVector,
    contentDescription: String = "Default Icon",
    onClick: () -> Unit = {}
) {
    IconButton(modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Navy
        )
    }
}

@Preview
@Composable
fun DefaultIconPreview() {
    DefaultIcon(Modifier, Icons.Default.Person)
}