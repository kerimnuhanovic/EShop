package com.eshop.coreui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.R
import com.eshop.coreui.theme.EShopTheme

@Composable
fun TopBanner(
    iconId: Int,
    titleId: Int,
    subtitleId: Int,
    onSearchIconClick: () -> Unit,
    onFilterIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensions.size_56)
            .background(color = MaterialTheme.colors.onPrimary)
            .fillMaxWidth(),
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensions.spaceExtraSmall)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    modifier = Modifier.size(dimensions.size_60)
                )
                Column(
                    modifier = Modifier.padding(horizontal = dimensions.spaceSmall)
                ) {
                    Text(
                        text = stringResource(id = titleId),
                        fontSize = dimensions.font_14,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colors.onSurface,
                    )
                    Text(
                        text = stringResource(id = subtitleId),
                        fontSize = dimensions.font_12,
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colors.surface
                    )
                }
            }
            Row(
                modifier = Modifier.padding(horizontal = dimensions.spaceSmall)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.clickable { onSearchIconClick() }
                )
                Spacer(modifier = Modifier.width(dimensions.spaceSmall))
                Icon(
                    painter = painterResource(id = R.drawable.filter_icon),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.clickable { onFilterIconClick() }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun TopBannerPreview() {
    EShopTheme {
        TopBanner(
            iconId = R.drawable.eshoplogo,
            titleId = R.string.eshop,
            subtitleId = R.string.your_online_shop_destination,
            onFilterIconClick = {},
            onSearchIconClick = {}
        )
    }
}