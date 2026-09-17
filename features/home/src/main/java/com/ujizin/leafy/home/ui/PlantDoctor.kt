package com.ujizin.leafy.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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

private const val DOCTOR_ATTRIBUTION = "Diagnosis data: PlantSolve.com (CC BY 4.0)"
private const val DOCTOR_DISCLAIMER = "Educational guidance \u2014 not a substitute for professional advice."

private data class PlantProblem(
    val slug: String,
    val name: String,
    val symptom: String,
    val fix: String,
    val plants: List<String>,
)

@Composable
fun PlantDoctor(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
        ) {
            var selected by remember { mutableStateOf<PlantProblem?>(null) }
            if (selected != null) {
                DoctorDetail(problem = selected!!, onBack = { selected = null })
            } else {
                DoctorList(onProblemClick = { selected = it })
            }
        }
    }
}

@Composable
private fun DoctorList(onProblemClick: (PlantProblem) -> Unit) {
    val context = LocalContext.current
    val problems = remember { parseProblems(context) }
    Section(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        title = "Plant Doctor",
        subTitle = "What is wrong with your plant?",
        trailingIcon = {
            AnimatedButtonIcon(icon = Icons.Back, animation = Animation.None, onClick = {})
        },
    ) {
        problems.forEach { problem ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onProblemClick(problem) }
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(problem.name, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        problem.symptom,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
        Spacer(Modifier.weight(1F))
        Text(
            DOCTOR_DISCLAIMER + "\n" + DOCTOR_ATTRIBUTION,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun DoctorDetail(problem: PlantProblem, onBack: () -> Unit) {
    Section(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        title = problem.name,
        subTitle = "Plant Doctor",
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
            Text("What you are seeing".capitalize(), style = MaterialTheme.typography.titleMedium)
            Text(problem.symptom, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(16.dp))
            Text("The fix".capitalize(), style = MaterialTheme.typography.titleMedium)
            Text(problem.fix, style = MaterialTheme.typography.bodyMedium)
            if (problem.plants.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))
                Text("Commonly affects".capitalize(), style = MaterialTheme.typography.titleMedium)
                Text(
                    problem.plants.take(8).joinToString(", ") { prettifySlug(it) } +
                        if (problem.plants.size > 8) {
                            ", and " + (problem.plants.size - 8) + " more"
                        } else {
                            ""
                        },
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(Modifier.height(24.dp))
            Text(
                DOCTOR_DISCLAIMER,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                DOCTOR_ATTRIBUTION,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(32.dp))
        }
    }
}

private fun prettifySlug(slug: String): String =
    slug.replace("-", " ").split(" ").joinToString(" ") { it.capitalize() }

private fun parseProblems(context: android.content.Context): List<PlantProblem> {
    return try {
        val text = context.assets.open("plant-doctor.json").bufferedReader().use { it.readText() }
        val arr = JSONArray(text)
        buildList {
            for (i in 0 until arr.length()) {
                val o = arr.getJSONObject(i)
                fun opt(key: String): String = if (o.isNull(key)) "" else o.optString(key)
                val plantsArray = o.optJSONArray("plants")
                add(
                    PlantProblem(
                        slug = opt("slug"),
                        name = opt("name"),
                        symptom = opt("symptom"),
                        fix = opt("fix"),
                        plants = buildList {
                            if (plantsArray != null) {
                                for (j in 0 until plantsArray.length()) add(plantsArray.optString(j))
                            }
                        },
                    ),
                )
            }
        }
    } catch (e: Exception) {
        emptyList()
    }
}
