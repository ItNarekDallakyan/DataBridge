package narek.dallakyan.app2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import narek.dallakyan.common.ext.readDataLink
import narek.dallakyan.common.ext.writeDataLink
import narek.dallakyan.ui.components.DataLinkUI

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DataLinkUI(
                context = this,
                onWrite = { key, value ->
                    this.writeDataLink(key = key, value = value)
                },
                onRead = { key ->
                    return@DataLinkUI this.readDataLink(key)
                }
            )
        }
    }
}