package com.haiphong.weatherforecastapp.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun WeatherTopBar(
    openSearchBar: Boolean,
    searchCityValue: String,
    onCityValueChange: (String) -> Unit,
    onSubmitSearch: () -> Unit,
    title: String,
    onSearch: () -> Unit,
    openFavorite: () -> Unit,
    addFavorite: () -> Unit,
    deleteFavorite: () -> Unit,
    isFavorite: Boolean
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(shape = RoundedCornerShape(corner = CornerSize(8.dp)), shadowElevation = 6.dp) {
        CenterAlignedTopAppBar(title = {
            if (!openSearchBar) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            } else {
                AnimatedVisibility(visible = openSearchBar) {
                    OutlinedTextField(
                        value = searchCityValue,
                        onValueChange = onCityValueChange,
                        textStyle = MaterialTheme.typography.labelLarge,
                        label = {
                            Text(
                                text = "City"
                            )
                        },
                        placeholder = { Text(text = "Enter a city's name...") },
                        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(onSearch = {
                            keyboardController?.hide()
                            onSubmitSearch()
                        })
                    )
                }
            }
        }, actions = {
            if (!openSearchBar) {
                IconButton(onClick = onSearch) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
                IconButton(onClick = openFavorite) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = "Bookmarks")
                }
            }
        }, navigationIcon = {
            if (!openSearchBar) {
                if (!isFavorite) {
                    IconButton(onClick = addFavorite) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = Color(0xFFF76B9A)
                        )
                    }
                } else {
                    IconButton(onClick = deleteFavorite) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "isFavorite",
                            tint = Color(0xFFF76B9A)
                        )
                    }
                }
            }
        })
    }
}