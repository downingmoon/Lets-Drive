package com.dugaza.letsdrive.util

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisUtil(
    private val template: StringRedisTemplate,
) {
    fun setValueExpire(
        key: String,
        value: String,
        duration: Long,
    ) {
        template.opsForValue().set(key, value, duration)
    }

    fun getValue(key: String): String? {
        return template.opsForValue().get(key)
    }

    fun setHashValueExpire(
        key: String,
        dataMap: Map<String, String>,
        duration: Long,
    ) {
        template.opsForHash<String, String>().putAll(key, dataMap)
        template.expire(key, Duration.ofSeconds(duration))
    }

    fun getHashValue(
        key: String,
        vararg hashKeys: String,
    ): Map<String, String>? {
        return hashKeys.zip(template.opsForHash<String, String>().multiGet(key, hashKeys.toList()))
            .toMap()
    }

    fun delete(key: String) {
        template.delete(key)
    }
}
