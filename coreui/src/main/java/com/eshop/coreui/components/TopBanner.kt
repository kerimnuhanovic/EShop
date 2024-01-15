package com.eshop.coreui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    Card(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background,
        elevation = dimensions.spaceSmall
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = dimensions.spaceExtraSmall)
        ) {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.size(dimensions.size_60)
            )
            Column(
                modifier = Modifier.padding(horizontal = dimensions.spaceSmall)
            ) {
                Text(text = stringResource(id = titleId),
                    fontSize = dimensions.font_14,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colors.onSurface
                )
                Text(text = stringResource(id = subtitleId),
                    fontSize = dimensions.font_12,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.surface
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
            subtitleId = R.string.your_online_shop_destination
        )
    }
}