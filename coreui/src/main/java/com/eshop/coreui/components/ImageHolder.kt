package com.eshop.coreui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.theme.EShopTheme
import com.eshop.coreui.theme.MediumGray

@Composable
fun ImageHolder(
    onExitClick: () -> Unit,
    image: Uri,
    modifier: Modifier = Modifier,
) {
    val dimensions = LocalDimensions.current

    Image(
        modifier = Modifier.size(dimensions.size_100).border(width = dimensions.size_1, color = MaterialTheme.colorScheme.onBackground),
        painter = rememberAsyncImagePainter(model = image),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
    // material 2 - I used Card, see if this works properly
    Surface(
        shape = CircleShape,
        modifier = modifier

            .size(dimensions.size_20)
            .offset(x = dimensions.size_90, y = dimensions.offset_minus_10),
        color = MediumGray
    ) {
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.clickable { onExitClick() })
    }


}

@Preview
@Composable
private fun ImageHolderPreview() {
    EShopTheme {
        ImageHolder(onExitClick = { }, image = Uri.EMPTY)
    }
}