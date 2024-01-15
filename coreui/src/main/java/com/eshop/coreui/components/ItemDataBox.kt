package com.eshop.coreui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.theme.MediumGray
import com.eshop.coreui.util.ItemData
import com.eshop.coreui.util.ShopAndProductCategory

@Composable
fun ItemDataBox(
    itemData: ItemData,
    onExitClick: (ItemData) -> Unit,
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    Surface(
        shape = RoundedCornerShape(CornerSize(dimensions.largeCornerRadius)),
        color = MediumGray,
        modifier = modifier.padding(
            horizontal = dimensions.spaceSmall,
            vertical = dimensions.spaceSmall
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(
                start = dimensions.spaceExtraSmall,
                end = dimensions.spaceSmall,
                top = dimensions.spaceExtraSmall,
                bottom = dimensions.spaceExtraSmall
            )
        ) {
            IconButton(
                onClick = { onExitClick(itemData) },
                modifier = Modifier.size(dimensions.size_20)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear, contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(dimensions.spaceSmall))
            Text(
                text = itemData.value, fontFamily = PoppinsFontFamily,
                fontSize = dimensions.font_12,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SellerCategoryBoxPreview() {
    EShopTheme {
        ItemDataBox(itemData = ShopAndProductCategory.ArtAndCraft, onExitClick = {})
    }
}