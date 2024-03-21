package br.com.rafaelleal.minhasferias.presentation_common.screens.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.rafaelleal.minhasferias.presentation_common.R
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Blue70
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.Navy
import br.com.rafaelleal.minhasferias.presentation_common.ui.theme.White


@Composable
fun AnimatedUpdateButton(visible: Boolean, testTag: String = "AnimatedUpdateButtonTag",  onClick: () -> Unit) {

    AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
        Button(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .testTag(testTag),
            colors = ButtonDefaults.buttonColors(backgroundColor = Navy, contentColor = White, disabledBackgroundColor = Blue70 ),
            onClick = { onClick() }) {
            Text(stringResource(R.string.update))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AnimatedUpdateButtonPreview() {
    AnimatedUpdateButton(true) { }
}