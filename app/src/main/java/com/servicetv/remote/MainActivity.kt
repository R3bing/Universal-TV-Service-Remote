package com.servicetv.remote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.servicetv.remote.ir.IrBlasterManager
import com.servicetv.remote.ir.MacroExecutor
import com.servicetv.remote.ui.screens.RemoteScreen
import com.servicetv.remote.ui.theme.DarkColorScheme

class MainActivity : ComponentActivity() {

    private lateinit var irBlasterManager: IrBlasterManager
    private lateinit var macroExecutor: MacroExecutor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize IR Manager and Macro Engine
        irBlasterManager = IrBlasterManager(applicationContext)
        macroExecutor = MacroExecutor(irBlasterManager)

        setContent {
            MaterialTheme(colorScheme = DarkColorScheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RemoteScreen(
                        irBlasterManager = irBlasterManager,
                        macroExecutor = macroExecutor
                    )
                }
            }
        }
    }
}
