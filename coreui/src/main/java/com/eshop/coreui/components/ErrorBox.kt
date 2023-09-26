package com.eshop.coreui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        shape = RoundedCornerShape(CornerSize(0.dp)),
        color = ErrorRed,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = dimensions.errorBoxHeight),
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(dimensions.spaceSmall), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(id = errorMessageId), textAlign = TextAlign.Center, fontFamily = PoppinsFontFamily)
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