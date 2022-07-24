package com.github.mejiomah17.kotlin.concurrent.collections

expect class ThreadSafeMap<K, V : Any> : MutableMap<K, V> {
    fun computeIfAbsent(key: K, mapper: (K) -> V): V
}