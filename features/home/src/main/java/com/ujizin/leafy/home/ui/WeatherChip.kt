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
import java.util.Locale

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
                text = displayTemperature(weather.maxTemperatureCelsius) + " high \u2014 " +
                    displayTemperature(weather.minTemperatureCelsius) + " low \u2014 " +
                    displayRain(weather.precipitationMillimeters) + " rain",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            if (weather.heavyRain) {
                Text(
                    text = "Heavy rain \u2014 outdoor waterings may be skipped today.".capitalize(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            if (weather.frostRisk) {
                Text(
                    text = "Frost risk \u2014 protect sensitive plants tonight.".capitalize(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}

private fun displayTemperature(celsius: Double): String {
    val useFahrenheit = Locale.getDefault().country.equals("US", ignoreCase = true)
    return if (useFahrenheit) {
        ((celsius * 9 / 5) + 32).toInt().toString() + "\u00b0F"
    } else {
        celsius.toInt().toString() + "\u00b0C"
    }
}

private fun displayRain(millimeters: Double): String {
    val useImperial = Locale.getDefault().country.equals("US", ignoreCase = true)
    return if (useImperial) {
        String.format(Locale.US, "%.1f", millimeters / 25.4) + "in"
    } else {
        millimeters.toInt().toString() + "mm"
    }
}
