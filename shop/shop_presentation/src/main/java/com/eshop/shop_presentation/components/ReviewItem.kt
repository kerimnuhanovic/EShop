package com.eshop.shop_presentation.components

import android.widget.Space
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.eshop.core.util.BASE_URL
import com.eshop.core.util.formatDate
import com.eshop.coreui.LocalDimensions
import com.eshop.coreui.PoppinsFontFamily
import com.eshop.coreui.theme.EShopTheme
import com.eshop.shop_domain.model.Review
import java.time.LocalDate

@Composable
fun ReviewItem(
    review: Review,
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensions.spaceMedium,
                vertical = dimensions.spaceSmall
            )
    ) {
        Row {
            Card(
                shape = CircleShape,
                backgroundColor = MaterialTheme.colors.onSecondary,
                modifier = Modifier
                    .padding(dimensions.spaceExtraSmall),
                elevation = dimensions.spaceExtraSmall
            ) {
                AsyncImage(
                    model = "${BASE_URL}/${review.authorProfileImage}",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(dimensions.size_50)
                        .clip(CircleShape)
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = dimensions.spaceMedium)
            ) {
                Text(
                    text = review.authorUsername,
                    fontSize = dimensions.font_12,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Text(
                    text = review.dateAdded.formatDate(),
                    fontSize = dimensions.font_12,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black.copy(alpha = 0.3f)
                )
            }
        }
        Spacer(modifier = Modifier.height(dimensions.spaceMedium))
        StarsRating(rating = review.rating.toInt())
        Spacer(modifier = Modifier.height(dimensions.spaceExtraSmall))
        Text(
            text = review.comment,
            fontSize = dimensions.font_12,
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ReviewItemPreview() {
    EShopTheme {
        ReviewItem(
            review = Review(
                "",
                "",
                "knuhanovic",
                "1704471244427-714891812-profile_image.jpg",
                "Had the best experience at Inoma Perfumery! The staff really know their stuff, and I left with the perfect fragrance. Highly recommend!",
                4.0,
                LocalDate.now()
            )
        )
    }
}