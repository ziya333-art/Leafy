package com.ujizin.leafy.home.ui

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ujizin.leafy.core.ui.components.Section
import com.ujizin.leafy.core.ui.components.animated.AnimatedButtonIcon
import com.ujizin.leafy.core.ui.components.animated.animation.Animation
import com.ujizin.leafy.core.ui.components.image.Icons
import com.ujizin.leafy.core.ui.extensions.capitalize
import org.json.JSONArray

private const val ATTRIBUTION = "Plant data: Permapeople.org (CC BY-SA 4.0) | Weather: Open-Meteo.com"

private data class GuidePlant(
    val id: Long,
    val name: String,
    val sci: String?,
    val desc: String,
    val alt: String?,
    val water: String?,
    val light: String?,
    val zone: String?,
    val soil: String?,
    val family: String?,
    val layer: String?,
    val cycle: String?,
    val growth: String?,
    val edibleParts: String?,
    val warning: String?,
    val nativeTo: String?,
    val height: String?,
)

@Composable
fun PlantGuide(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
        ) {
            var selected by remember { mutableStateOf<GuidePlant?>(null) }
            if (selected != null) {
                GuideDetail(plant = selected!!, onBack = { selected = null })
            } else {
                GuideSearch(onPlantClick = { selected = it })
            }
        }
    }
}

@Composable
private fun GuideSearch(onPlantClick: (GuidePlant) -> Unit) {
    val context = LocalContext.current
    val plants = remember { parseGuidePlants(context) }
    var query by remember { mutableStateOf("") }
    val results = remember(query, plants) {
        if (query.isBlank()) {
            plants.take(30)
        } else {
            plants.filter {
                it.name.contains(query, true) ||
                    it.sci?.contains(query, true) == true ||
                    it.alt?.contains(query, true) == true
            }.take(50)
        }
    }
    Section(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        title = "Plant Guide",
        subTitle = plants.size.toString() + " species, offline",
        trailingIcon = {
            AnimatedButtonIcon(icon = Icons.Back, animation = Animation.None, onClick = {})
        },
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            singleLine = true,
            placeholder = { Text("Search a plant\u2026".capitalize()) },
        )
        Spacer(Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
        ) {
            items(results, key = { it.id }) { plant ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPlantClick(plant) }
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text(plant.name, style = MaterialTheme.typography.bodyLarge)
                        plant.sci?.let {
                            Text(
                                it,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        plant.water?.let {
                            Text(
                                "Water: " + it,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }
                }
            }
        }
        Text(
            ATTRIBUTION,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun GuideDetail(plant: GuidePlant, onBack: () -> Unit) {
    Section(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        title = plant.name,
        subTitle = plant.sci ?: "",
        trailingIcon = {
            AnimatedButtonIcon(icon = Icons.Back, animation = Animation.None, onClick = onBack)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
        ) {
            val interval = suggestedIntervalDays(plant.water)
            if (interval != null) {
                Text(
                    ("Leafy suggests watering " + interval + ".").capitalize(),
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(Modifier.height(12.dp))
            }
            GuideRow("Water", plant.water)
            GuideRow("Light", plant.light)
            GuideRow("Hardiness zone", plant.zone)
            GuideRow("Soil", plant.soil)
            GuideRow("Family", plant.family)
            GuideRow("Layer", plant.layer)
            GuideRow("Life cycle", plant.cycle)
            GuideRow("Growth", plant.growth)
            GuideRow("Edible parts", plant.edibleParts)
            GuideRow("Native to", plant.nativeTo)
            GuideRow("Height", plant.height)
            if (plant.warning != null) {
                Spacer(Modifier.height(12.dp))
                Text(
                    ("Warning: " + plant.warning).capitalize(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                )
            }
            if (plant.desc.isNotBlank()) {
                Spacer(Modifier.height(12.dp))
                Text(plant.desc, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(Modifier.height(16.dp))
            Text(
                ATTRIBUTION,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun GuideRow(label: String, value: String?) {
    if (value.isNullOrBlank()) return
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
    ) {
        Text(
            label,
            modifier = Modifier.padding(end = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}

private fun suggestedIntervalDays(water: String?): String? = when {
    water == null || water.isBlank() -> null
    water.contains("Low", true) -> "every 10-14 days"
    water.contains("Wet", true) || water.contains("Water", true) -> "every 1-2 days"
    water.contains("Dry", true) && water.contains("Moist", true) -> "every 4-5 days"
    water.contains("Dry", true) -> "every 7-10 days"
    water.contains("Moist", true) -> "every 2-3 days"
    else -> null
}

private fun parseGuidePlants(context: Context): List<GuidePlant> {
    return try {
        val text = context.assets.open("plants-bundled.json").bufferedReader().use { it.readText() }
        val arr = JSONArray(text)
        buildList {
            for (i in 0 until arr.length()) {
                val o = arr.getJSONObject(i)
                add(
                    GuidePlant(
                        id = o.optLong("id"),
                        name = o.optString("name"),
                        sci = (if (o.isNull("sci")) null else o.optString("sci")).ifBlank { null },
                        desc = (if (o.isNull("desc")) "" else o.optString("desc")),
                        alt = (if (o.isNull("alt")) null else o.optString("alt")).ifBlank { null },
                        water = (if (o.isNull("water")) null else o.optString("water")).ifBlank { null },
                        light = (if (o.isNull("light")) null else o.optString("light")).ifBlank { null },
                        zone = (if (o.isNull("zone")) null else o.optString("zone")).ifBlank { null },
                        soil = (if (o.isNull("soil")) null else o.optString("soil")).ifBlank { null },
                        family = (if (o.isNull("family")) null else o.optString("family")).ifBlank { null },
                        layer = (if (o.isNull("layer")) null else o.optString("layer")).ifBlank { null },
                        cycle = (if (o.isNull("cycle")) null else o.optString("cycle")).ifBlank { null },
                        growth = (if (o.isNull("growth")) null else o.optString("growth")).ifBlank { null },
                        edibleParts = (if (o.isNull("edibleParts")) null else o.optString("edibleParts")).ifBlank { null },
                        warning = (if (o.isNull("warning")) null else o.optString("warning")).ifBlank { null },
                        nativeTo = (if (o.isNull("native")) null else o.optString("native")).ifBlank { null },
                        height = (if (o.isNull("height")) null else o.optString("height")).ifBlank { null },
                    ),
                )
            }
        }
    } catch (e: Exception) {
        emptyList()
    }
}
