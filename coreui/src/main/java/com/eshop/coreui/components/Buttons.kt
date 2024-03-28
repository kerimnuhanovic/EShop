package com.eshop.coreui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.R
import com.eshop.coreui.theme.EShopTheme

@Composable
fun EShopButton(
    content: @Composable () -> Unit,
    backgroundColor: Color,
    contentColor: Color,
    shape: Shape,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val dimensions = LocalDimensions.current
    Button(
        onClick = onButtonClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        modifier = modifier.height(dimensions.buttonHeight_56),
        shape = shape,
        enabled = enabled
    ) {
        Box(
            modifier = Modifier.padding(dimensions.spaceExtraSmall),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun EShopButtonPreview() {
    val dimensions = LocalDimensions.current
    EShopTheme {
        EShopButton(
            content = { Text(text = stringResource(id = R.string.login)) },
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            onButtonClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensions.spaceSmall),
            shape = RoundedCornerShape(CornerSize(dimensions.largeCornerRadius))
        )
    }
}