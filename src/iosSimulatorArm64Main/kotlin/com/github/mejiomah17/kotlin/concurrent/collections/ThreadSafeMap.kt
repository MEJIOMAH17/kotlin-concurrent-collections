package com.github.mejiomah17.kotlin.concurrent.collections
import kotlinx.coroutines.sync.Mutex

actual class ThreadSafeMap<K, V : Any> : MutableMap<K, V> {
    private val delegate = HashMap<K, V>()
    private val mutex = Mutex()
    private fun <T> synchronized(block: () -> T): T {
        try {
            while (!mutex.tryLock()) {
                return block()
            }
        } finally {
            mutex.unlock()
        }
        error("Can't happen. Because of while block have to be executed")
    }

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = synchronized {
            delegate.entries.toMutableSet()
        }
    override val keys: MutableSet<K>
        get() = synchronized {
            delegate.keys.toMutableSet()
        }

    override val size: Int
        get() = synchronized { delegate.size }
    override val values: MutableCollection<V>
        get() = synchronized {
            delegate.values.toMutableSet()
        }

    override fun clear() = synchronized {
        delegate.clear()
    }

    override fun isEmpty() = synchronized { delegate.isEmpty() }
    override fun remove(key: K): V? = synchronized {
        delegate.remove(key)
    }

    override fun putAll(from: Map<out K, V>) = synchronized {
        delegate.putAll(from)
    }

    override fun put(key: K, value: V): V? = synchronized {
        delegate.put(key, value)
    }

    override fun get(key: K): V? = synchronized { delegate[key] }

    override fun containsValue(value: V) = synchronized { delegate.containsValue(value) }

    override fun containsKey(key: K): Boolean = synchronized { delegate.containsKey(key) }

    actual fun computeIfAbsent(key: K, mapper: (K) -> V): V {
        return synchronized {
            var result = delegate[key]
            if (result == null) {
                result = mapper(key)
                delegate[key] = result
            }
            result
        }
    }
}