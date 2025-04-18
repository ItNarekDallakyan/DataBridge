package narek.dallakyan.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DataLinkUI(
    context: Context,
    onWrite: (String, String) -> Unit,
    onRead: (String) -> String?
) {
    var writeKey by remember { mutableStateOf("") }
    var writeValue by remember { mutableStateOf("") }

    var readKey by remember { mutableStateOf("") }
    var readValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, top = 50.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // Write Section
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Write to DataLink", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = writeKey,
                onValueChange = { writeKey = it },
                label = { Text("Key") }
            )

            OutlinedTextField(
                value = writeValue,
                onValueChange = { writeValue = it },
                label = { Text("Value") }
            )

            Button(onClick = {
                onWrite.invoke(writeKey, writeValue)
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
            }) {
                Text("Save")
            }
        }

        HorizontalDivider()

        // Read Section
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Read from DataLink", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = readKey,
                onValueChange = { readKey = it },
                label = { Text("Key") }
            )

            Button(onClick = {
                readValue = onRead.invoke(readKey) ?: "Not found"
            }) {
                Text("Read")
            }

            if (readValue.isNotEmpty()) {
                Text("Value: $readValue", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
