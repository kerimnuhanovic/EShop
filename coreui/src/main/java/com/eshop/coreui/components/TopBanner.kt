package com.eshop.coreui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBanner(
    iconId: Int,
    titleId: Int,
    subtitleId: Int,
    onSearchIconClick: () -> Unit,
    onFilterIconClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    TopAppBar(
        modifier = modifier
            .height(dimensions.size_56),
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        title = {
            Column(
                modifier = Modifier.padding(horizontal = dimensions.spaceSmall)
            ) {
                Text(
                    text = stringResource(id = titleId),
                    fontSize = dimensions.font_14,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = stringResource(id = subtitleId),
                    fontSize = dimensions.font_12,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        },
        navigationIcon = {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.size(dimensions.size_60)
            )
        },
        actions = {
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
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
private fun TopBannerPreview() {
    EShopTheme {
        TopBanner(
            iconId = R.drawable.eshoplogo,
            titleId = R.string.eshop,
            subtitleId = R.string.your_online_shop_destination,
            onFilterIconClick = {},
            onSearchIconClick = {},
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        )
    }
}