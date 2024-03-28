package com.eshop.coreui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.theme.MediumGray
import com.eshop.coreui.R
import com.eshop.coreui.util.ItemData
import com.eshop.coreui.util.ShopAndProductCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EShopDropdownMenu(
    expanded: Boolean,
    placeholder: String,
    items: List<ItemData>,
    selectedItems: List<ItemData>,
    onExpandChange: () -> Unit,
    onSelectItem: (ItemData) -> Unit,
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandChange() },
        modifier = modifier
            .fillMaxWidth()
    ) {
        TextField(
            value = "", onValueChange = {}, modifier = Modifier
                .fillMaxWidth(), shape = RoundedCornerShape(CornerSize(50.dp)),
            textStyle = TextStyle(
                fontFamily = PoppinsFontFamily,
                letterSpacing = dimensions.smallLetterSpacing
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MediumGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = PoppinsFontFamily,
                    fontSize = dimensions.font_12,
                    letterSpacing = dimensions.smallLetterSpacing
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = if (!expanded) R.drawable.arrow_down_24 else R.drawable.arrow_up_24),
                    contentDescription = null
                )
            },
            singleLine = true,
            readOnly = true
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandChange() },
            modifier = Modifier
                .height(dimensions.dropdownHeight)
                .verticalScroll(
                    rememberScrollState()
                )
                .background(color = MediumGray)
        ) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    onSelectItem(item)
                },
                    text = {
                        Text(text = item.value)
                        Spacer(modifier = Modifier.weight(1f))
                        if (selectedItems.contains(item)) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null)
                        }
                    })
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun EShopDropdownMenuPreview() {
    val dimensions = LocalDimensions.current
    EShopTheme {
        EShopDropdownMenu(
            false,
            stringResource(id = R.string.select_your_shop_categories),
            ShopAndProductCategory.listAllCategories(),
            emptyList(),
            {},
            {},
            Modifier.padding(dimensions.spaceMedium)
        )
    }
}