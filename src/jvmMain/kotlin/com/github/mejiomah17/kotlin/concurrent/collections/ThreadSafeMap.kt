package com.github.mejiomah17.kotlin.concurrent.collections

import java.util.concurrent.ConcurrentHashMap

actual class ThreadSafeMap<K, V : Any> : MutableMap<K, V> {
    private val delegate = ConcurrentHashMap<K, V>()
    actual fun computeIfAbsent(key: K, mapper: (K) -> V): V {
        return delegate.computeIfAbsent(key, mapper)
    }

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = delegate.entries
    override val keys: MutableSet<K>
        get() = delegate.keys
    override val size: Int
        get() = delegate.size
    override val values: MutableCollection<V>
        get() = delegate.values

    override fun clear() {
        delegate.clear()
    }

    override fun isEmpty(): Boolean {
        return delegate.isEmpty()
    }

    override fun remove(key: K): V? {
        return delegate.remove(key)
    }

    override fun putAll(from: Map<out K, V>) {
        return delegate.putAll(from)
    }

    override fun put(key: K, value: V): V? {
        return delegate.put(key, value)
    }

    override fun get(key: K): V? {
        return delegate.get(key)
    }

    override fun containsValue(value: V): Boolean {
        return delegate.containsValue(value)
    }

    override fun containsKey(key: K): Boolean {
        return delegate.containsKey(key)
    }
}