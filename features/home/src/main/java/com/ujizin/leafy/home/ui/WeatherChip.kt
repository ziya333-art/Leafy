package com.ujizin.leafy.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ujizin.leafy.core.ui.extensions.capitalize
import com.ujizin.leafy.domain.model.DailyWeather

@Composable
internal fun WeatherChip(
    weather: DailyWeather,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Today in your garden".capitalize(),
                style = MaterialTheme.typography.titleSmall,
            )
            Text(
                text = "${weather.maxTemperatureCelsius.toInt()}° high — ${weather.minTemperatureCelsius.toInt()}° low — ${weather.precipitationMillimeters.toInt()}mm rain",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            if (weather.heavyRain) {
                Text(
                    text = "Heavy rain — outdoor waterings may be skipped today.".capitalize(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            if (weather.frostRisk) {
                Text(
                    text = "Frost risk — protect sensitive plants tonight.".capitalize(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}
