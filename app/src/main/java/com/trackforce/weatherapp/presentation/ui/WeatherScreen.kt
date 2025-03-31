package com.trackforce.weatherapp.presentation.ui

import androidx.annotation.OptIn
import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.trackforce.weatherapp.R
import com.trackforce.weatherapp.domain.model.Weather
import com.trackforce.weatherapp.presentation.viewmodel.WeatherViewModel
import com.trackforce.weatherapp.Utils.celsiusToFahrenheit
import com.trackforce.weatherapp.Utils.getUvInfo
import com.trackforce.weatherapp.Utils.timestampToTime

@Composable
fun WeatherScreen(weatherViewModel: WeatherViewModel) {
    val weatherState by weatherViewModel.weatherData.collectAsState()

    weatherState?.let { weather ->

        val videoRes = getWeatherVideo(weather)

        Box(modifier = Modifier.fillMaxSize()) {
            VideoBackground(videoRes)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 1f)),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                WeatherInformationBody(weather)
            }
        }
    } ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun WeatherInformationBody(weather: Weather) {
    Column(modifier = Modifier.testTag("weatherInformationBody")
        .fillMaxWidth()
        .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            Text(
                text = weather.timezone,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Light,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.5f),
                        offset = Offset(2f, 2f),
                        blurRadius = 8f,
                    )
                )
            )

            Text(
                text = timestampToTime(weather.current.dt).split(";")[0],
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Light
            )
        }

        Space(16.dp)

        Text(text = timestampToTime(weather.current.dt).split(";")[1],
            fontSize = 30.sp,
            color =  Color.White,
            fontWeight = FontWeight.Light)

        Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Bottom) {
            Text(
                text = "${weather.current.temp.toInt()}°C",
                fontSize = 100.sp,
                color =  Color.White,
                fontWeight = FontWeight.Light
            )

            Text(
                text = "/${celsiusToFahrenheit(weather.current.temp).toInt()}°F",
                fontSize = 25.sp,
                color =  Color.White,
                fontWeight = FontWeight.Light
            )
        }

        Text(text = weather.current.weather[0].description.toUpperCase(),
            fontSize = 16.sp,
            color =  Color.White,
            fontWeight = FontWeight.Light)

        Text(
            text = "Feels like: ${weather.current.feelsLike.toInt()}°C / ${celsiusToFahrenheit(weather.current.temp).toInt()}°F",
            fontSize = 16.sp,
            color =  Color.White,
            fontWeight = FontWeight.Light
        )

        Text(text = "H: ${weather.lat.toInt()} L: ${weather.lon.toInt()}",
            fontSize = 16.sp,
            color =  Color.White,
            fontWeight = FontWeight.Light)

        Space(8.dp)
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 16.dp))
        Space(8.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ico_sunrise),
                        contentDescription = "Icon",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = timestampToTime(weather.current.sunrise).split(";")[1],
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }

            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ico_sunset),
                        contentDescription = "Icon",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = timestampToTime(weather.current.sunset).split(";")[1],
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }

        Card(shape = RoundedCornerShape(8.dp), modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .align(Alignment.CenterHorizontally),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))){
            Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(id = R.drawable.ico_uv),
                    contentDescription = "Icon",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                )

                Column {
                    Text(
                        text ="UV: ${getUvInfo(weather.current.uvi).split("-")[0]}",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Light
                    )

                    Text(
                        text = "Rec: ${getUvInfo(weather.current.uvi).split('-')[1]}",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "HUMIDITY",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Light)

                    Space(5.dp)
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(1.dp)
                            .background(Color.White)
                            .padding(vertical = 108.dp)
                    )
                    Space(5.dp)

                    Text(
                        text = "${weather.current.humidity}%",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "PRESSURE",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Light
                    )

                    Space(5.dp)
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(1.dp)
                            .background(Color.White)
                            .padding(vertical = 8.dp)
                    )
                    Space(5.dp)

                    Text(
                        text = "${weather.current.pressure} hPa",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "WIND SPEED",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Light
                    )

                    Space(5.dp)
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(1.dp)
                            .background(Color.White)
                            .padding(vertical = 8.dp)
                    )
                    Space(5.dp)

                    Text(
                        text = "${weather.current.windSpeed} m/s",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "WIND DEGREE",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Light
                    )

                    Space(5.dp)
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(1.dp)
                            .background(Color.White)
                            .padding(vertical = 8.dp)
                    )
                    Space(5.dp)

                    Text(
                        text = "${weather.current.windDeg}°",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Space(8.dp)

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 16.dp))

        MapView(weather.lat, weather.lon)
    }
}

@Composable
fun MapView(lat: Double, lon: Double) {
    val latLng = LatLng(lat, lon)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 11f)
    }

    Card (shape = RoundedCornerShape(8.dp), modifier = Modifier.padding(top = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.3f))) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.Gray),
            contentAlignment = Alignment.BottomEnd
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                properties = MapProperties(mapType = MapType.TERRAIN),
                uiSettings = MapUiSettings(
                    zoomGesturesEnabled = false,
                    zoomControlsEnabled = false,
                    scrollGesturesEnabled = false,
                    rotationGesturesEnabled = false
                ),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = rememberMarkerState(position = latLng),
                    title = "Ubicación Actual"
                )
            }
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun VideoBackground(@RawRes videoRes: Int) {
    val context = LocalContext.current
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri("android.resource://${context.packageName}/$videoRes")
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_ONE
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
                useController = false
            }
        },
        modifier = Modifier.fillMaxHeight()
    )
}

fun getWeatherVideo(weather: Weather): Int {
    val currentTime = weather.current.dt
    val sunriseTime = weather.current.sunrise
    val sunsetTime = weather.current.sunset
    val weatherId = weather.current.weather[0].id

    return when {
        // Night
        currentTime < sunriseTime || currentTime > sunsetTime -> R.raw.night_video
        // Sunny
        weatherId == 800 -> R.raw.sunny_video
        // Cloudy
        weatherId in 801..804 -> R.raw.cloudy_video
        // Rainy
        else -> R.raw.rainy_video
    }
}

@Composable
fun Space(dim: Dp){
    Spacer(modifier = Modifier
        .height(dim))
}