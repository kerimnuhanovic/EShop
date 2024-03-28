package com.eshop.coreui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.R
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.theme.ErrorRed

@Composable
fun ErrorBox(
    errorMessageId: Int,
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    Surface(
        color = ErrorRed,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = dimensions.errorBoxHeight),
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensions.spaceSmall),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = errorMessageId),
                textAlign = TextAlign.Center,
                fontFamily = PoppinsFontFamily
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ErrorBoxPreview() {
    EShopTheme {
        ErrorBox(errorMessageId = R.string.api_error_4xx)
    }
}