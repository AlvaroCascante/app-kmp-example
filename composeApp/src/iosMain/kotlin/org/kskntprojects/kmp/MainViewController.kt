package org.kskntprojects.kmp

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import io.ktor.client.engine.darwin.Darwin
import org.kskntprojects.kmp.clients.PostClient
import org.kskntprojects.kmp.network.createHttpBasicClient
import org.kskntprojects.kmp.ui.App

fun MainViewController() = ComposeUIViewController {
    App(
        postClient = remember {
            PostClient(
                createHttpBasicClient(
                username = "username",
                password = "password",
                engine = Darwin.create()
            )
            )
        }
    )
}