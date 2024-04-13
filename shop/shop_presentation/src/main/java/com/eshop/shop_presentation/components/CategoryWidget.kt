package com.eshop.shop_presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.theme.MediumGray

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