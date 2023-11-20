package com.haiphong.weatherforecastapp.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.haiphong.weatherforecastapp.data.Favorite

@Composable
fun FavoriteListItem(
    favoriteCity: Favorite,
//    onDelete: () -> Unit,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(6.dp)
            .clickable { onClick(favoriteCity.city) },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(corner = CornerSize(8.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = favoriteCity.city, style = MaterialTheme.typography.titleMedium)
            Text(text = favoriteCity.country, style = MaterialTheme.typography.titleMedium)
//            IconButton(onClick = onDelete) {
//                Icon(
//                    imageVector = Icons.Default.Delete,
//                    contentDescription = "Delete favorite",
//                    tint = Color.Red
//                )
//            }
        }
    }

}