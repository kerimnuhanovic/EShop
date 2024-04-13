package com.eshop.coreui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.theme.EShopTheme

@Composable
fun DrawerItem(
    label: @Composable () -> Unit,
    isDrawerItemExpanded: Boolean,
    onIconClick: () -> Unit,
    containerModifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    iconColor: Color,
    content: @Composable () -> Unit = {},
) {
    Column(
        modifier = containerModifier
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = rowModifier) {
            label()
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (isDrawerItemExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = iconColor,
                modifier = iconModifier.clickable { onIconClick() }
            )
        }
        AnimatedVisibility(visible = isDrawerItemExpanded) {
            content()
        }

    }
}

@Composable
@Preview(showBackground = true)
private fun DrawerItemPreview() {
    val dimensions = LocalDimensions.current
    EShopTheme {
        DrawerItem(
            isDrawerItemExpanded = true,
            containerModifier = Modifier.padding(dimensions.spaceSmall),
            label = {
                Text(
                    text = "Filter", fontSize = dimensions.font_16,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.SemiBold, maxLines = 1,
                    color = Color.Black
                )
            },
            iconColor = Color.Black,
            onIconClick = {}
        ) {
            Column {
                EShopCheckbox(isChecked = true, onChange = {}, label = {
                    Text(
                        text = "Category 1",
                        fontFamily = PoppinsFontFamily,
                        fontSize = dimensions.font_16,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = dimensions.spaceExtraSmall)
                    )
                },
                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
                )
                EShopCheckbox(isChecked = false, onChange = {}, label = {
                    Text(
                        text = "Category 2",
                        fontFamily = PoppinsFontFamily,
                        fontSize = dimensions.font_16,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = dimensions.spaceExtraSmall)
                    )
                },
                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
                )
                EShopCheckbox(isChecked = true, onChange = {}, label = {
                    Text(
                        text = "Category 3",
                        fontFamily = PoppinsFontFamily,
                        fontSize = dimensions.font_16,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = dimensions.spaceExtraSmall)
                    )
                },
                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
                )
            }
        }
    }
}