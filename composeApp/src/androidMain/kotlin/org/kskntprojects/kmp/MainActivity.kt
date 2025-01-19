package org.kskntprojects.kmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import io.ktor.client.engine.okhttp.OkHttp
import org.kskntprojects.kmp.clients.PostClient
import org.kskntprojects.kmp.manages.BatteryManager
import org.kskntprojects.kmp.network.createHttpBasicClient
import org.kskntprojects.kmp.ui.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(
                postClient = remember {
                        PostClient(createHttpBasicClient(
                            username = "username",
                            password = "password",
                            engine = OkHttp.create()
                        ))
                },
                batteryManager = remember {
                    BatteryManager(applicationContext)
                }
            )
        }
    }
}
