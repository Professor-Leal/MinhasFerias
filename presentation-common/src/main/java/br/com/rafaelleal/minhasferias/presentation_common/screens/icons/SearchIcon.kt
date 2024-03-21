package br.com.rafaelleal.minhasferias.presentation_common.screens.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SearchIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    DefaultIcon(
        modifier = modifier,
        icon = Icons.Default.Search,
        contentDescription = "Search Icon",
        onClick = onClick
    )
}

@Preview
@Composable
fun SearchIconPreview() {
    SearchIcon(Modifier)
}