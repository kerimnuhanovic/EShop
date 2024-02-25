package com.eshop.shop_presentation.components

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.theme.MediumGray
import androidx.compose.ui.unit.sp

@Composable
fun CategoryWidget(
    text: String,
    fontSize: TextUnit = 12.sp,
    modifier: Modifier = Modifier,
) {
    val dimensions = LocalDimensions.current
    Surface(
        shape = RoundedCornerShape(CornerSize(dimensions.largeCornerRadius)),
        color = MediumGray,
        modifier = modifier.padding(
            end = dimensions.spaceSmall,
            bottom = dimensions.spaceExtraSmall
        )
    ) {
        Text(
            text = text,
            fontFamily = PoppinsFontFamily,
            fontSize = fontSize,
            modifier = Modifier.padding(vertical = dimensions.spaceExtraSmall, horizontal = dimensions.spaceSmall)
        )
    }
}