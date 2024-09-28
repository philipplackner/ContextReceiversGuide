package com.plcoding.contextreceiversguide

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private suspend fun getUser(id: String = ""): String {
    delay(1000L)
    return "Hello world!"
}

fun doSomething() {
    val userIds = (1..10).map { it.toString() }
    with(CoroutineScope(Dispatchers.IO)) {
        userIds.forEachAsync {
            val user = getUser(it)
        }
    }
    CoroutineScope(Dispatchers.IO).launch {
        userIds.forEachAsync {
            val user = getUser(it)
        }
    }
}

context(CoroutineScope)
fun <T> Iterable<T>.forEachAsync(action: suspend (T) -> Unit) {
    forEach {
        launch {
            action(it)
        }
    }
}