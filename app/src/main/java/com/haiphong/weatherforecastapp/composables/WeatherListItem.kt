package com.haiphong.weatherforecastapp.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.haiphong.weatherforecastapp.model.WeatherItem
import com.haiphong.weatherforecastapp.util.formatDate
import com.haiphong.weatherforecastapp.util.formatDegree

@Composable
fun WeatherListItem(weatherItem: WeatherItem) {

    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"
    Card(
        modifier = Modifier
            .padding(6.dp),
        shape = RoundedCornerShape(corner = CornerSize(6.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatDate(weatherItem.dt),
                style = MaterialTheme.typography.labelMedium
            )
            AsyncImage(
                model = imageUrl,
                contentDescription = "Weather Image",
                modifier = Modifier.size(80.dp)
            )
            Surface(
                shape = RoundedCornerShape(corner = CornerSize(4.dp)),
                color = Color(0xFFFF9800)
            ) {
                Text(
                    modifier = Modifier.padding(2.dp),
                    text = weatherItem.weather[0].description,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Row {
                Text(text = formatDegree(weatherItem.temp.max), color = Color.Red)
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = formatDegree(weatherItem.temp.min), color = Color.Blue)
            }
        }
    }

}