package com.haiphong.weatherforecastapp.ui.screens.main

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.haiphong.weatherforecastapp.R
import com.haiphong.weatherforecastapp.composables.FavoriteListItem
import com.haiphong.weatherforecastapp.composables.WeatherListItem
import com.haiphong.weatherforecastapp.composables.WeatherTopBar
import com.haiphong.weatherforecastapp.data.Favorite
import com.haiphong.weatherforecastapp.model.WeatherItem
import com.haiphong.weatherforecastapp.util.formatDate
import com.haiphong.weatherforecastapp.util.formatDegree
import com.haiphong.weatherforecastapp.util.formatTime
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val payload by mainViewModel.data
    val uiState by mainViewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (payload.loading == true) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (payload.data != null) {
        val weather = payload.data!!
        val weatherItem = weather.list[0]
        val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"
        ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
            ModalDrawerSheet {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Favorite Cities",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Divider()
                    LazyColumn {
                        items(uiState.favoriteList) { favItem ->
                            FavoriteListItem(
                                favoriteCity = favItem,
                                onClick = {
                                    mainViewModel.getWeather(favItem.city)
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                })
                        }
                    }
//                    Spacer(modifier = Modifier.height(50.dp))
//                    TextButton(onClick = mainViewModel::flush) {
//                        Text(text = "Flush", color = Color.Red)
//                    }
                }
            }
        }) {
            Scaffold(topBar = {
                WeatherTopBar(
                    openSearchBar = uiState.openSearch,
                    title = weather.city.name + ", " + weather.city.country,
                    searchCityValue = uiState.searchCity,
                    onCityValueChange = mainViewModel::onSearchCityChange,
                    onSubmitSearch = mainViewModel::submitSearch,
                    onSearch = mainViewModel::openSearchBar,
                    addFavorite = {
                        mainViewModel.insertFavorite(
                            Favorite(
                                city = uiState.searchCity,
                                country = weather.city.country
                            )
                        )
                    },
                    deleteFavorite = {
                        val currentFavorite =
                            uiState.favoriteList.filter { it.city == uiState.searchCity }[0]
                        mainViewModel.deleteFavorite(currentFavorite)
                    },
                    openFavorite = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
                    isFavorite = uiState.isFavorite
                )
            }) { paddingValue ->

                Column(
                    modifier = Modifier
                        .padding(paddingValue)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = formatDate(weatherItem.dt),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(6.dp)
                    )
                    Surface(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(200.dp),
                        shape = CircleShape,
                        color = Color(0xFFFF9800)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Weather Image",
                                modifier = Modifier
                                    .size(90.dp)
                            )
                            Text(
                                text = formatDegree(weatherItem.temp.day),
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(text = weatherItem.weather[0].main, fontStyle = FontStyle.Italic)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    HumidWindPressureRow(weatherItem = weatherItem)
                    Divider()
                    SunsetSunriseRow(weatherItem = weatherItem)
                    Text(text = "This Week", style = MaterialTheme.typography.titleLarge)
                    Surface(
                        modifier = Modifier.padding(3.dp),
                        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                        color = Color.White,
                        shadowElevation = 6.dp
                    ) {
                        LazyColumn {
                            items(weather.list) { item ->
                                WeatherListItem(weatherItem = item)
                            }
                        }
                    }
                }
            }
        }

    } else if (payload.e != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = payload.e.toString())
        }
    }
}

@Composable
fun HumidWindPressureRow(weatherItem: WeatherItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weatherItem.humidity}%", style = MaterialTheme.typography.labelMedium)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weatherItem.pressure} psi", style = MaterialTheme.typography.labelMedium)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weatherItem.speed} km/h", style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
fun SunsetSunriseRow(weatherItem: WeatherItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise time",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = formatTime(weatherItem.sunrise),
                style = MaterialTheme.typography.labelMedium
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Text(
                text = formatTime(weatherItem.sunset),
                style = MaterialTheme.typography.labelMedium
            )
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunset time",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}


