package com.eshop.coreui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.theme.EShopTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EShopCheckbox(
    isChecked: Boolean,
    onChange: (isChecked: Boolean) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    colors: CheckboxColors = CheckboxDefaults.colors()
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    onChange(it)
                },
                modifier = modifier,
                colors = colors
            )
        }
        label()
    }
}

@Composable
@Preview(showBackground = true)
fun EShopCheckboxPreview() {
    val dimensions = LocalDimensions.current
    EShopTheme {
        EShopCheckbox(isChecked = true, onChange = {}, label = {
            Text(
                text = "Checkbox",
                fontFamily = PoppinsFontFamily,
                fontSize = dimensions.font_16,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = dimensions.spaceExtraSmall)
            )
        },
        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary, checkmarkColor = Color.White)
        )
    }
}