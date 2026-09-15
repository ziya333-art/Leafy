package com.ujizin.leafy.preferences.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ujizin.leafy.core.ui.extensions.capitalize
import com.ujizin.leafy.domain.model.GardenLocation

// TODO: extract strings to resources for localization
@Composable
fun GardenLocationRow(
    modifier: Modifier = Modifier,
    location: GardenLocation?,
    searchFailed: Boolean,
    onLocationSave: (String) -> Unit,
) {
    var city by remember { mutableStateOf("") }

    Column(modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Garden Location".capitalize(),
                style = MaterialTheme.typography.titleSmall,
            )
            location?.let {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                modifier = Modifier.weight(1f),
                singleLine = true,
                placeholder = { Text("City") },
                supportingText = if (searchFailed) {
                    { Text("Location not found") }
                } else {
                    null
                },
            )
            TextButton(
                onClick = {
                    onLocationSave(city)
                    city = ""
                },
                enabled = city.isNotBlank(),
            ) {
                Text("Save")
            }
        }
    }
}
