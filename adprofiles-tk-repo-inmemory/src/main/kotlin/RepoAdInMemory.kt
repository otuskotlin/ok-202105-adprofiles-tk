package ru.otus.otuskotlin.marketplace.backend.repo.inmemory

import kotlinx.coroutines.runBlocking
import model.Ad
import org.ehcache.Cache
import org.ehcache.CacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import row.AdRow
import ru.otus.otuskotlin.marketplace.backend.repo.common.IRepoAd
import java.time.Duration
import java.util.*


class RepoAdInMemory(
    private val initObjects: List<Ad> = listOf(),
    private val ttl: Duration = Duration.ofMinutes(1)
) : IRepoAd {

    private val cache: Cache<String, AdRow> = let {
        val cacheManager: CacheManager = CacheManagerBuilder
            .newCacheManagerBuilder()
            .build(true)

        cacheManager.createCache(
            "marketplace-ad-cache",
            CacheConfigurationBuilder
                .newCacheConfigurationBuilder(
                    String::class.java,
                    AdRow::class.java,
                    ResourcePoolsBuilder.heap(100)
                )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(ttl))
                .build()
        )
    }

    init {
        runBlocking {
            initObjects.forEach {
                save(it)
            }
        }
    }

    private suspend fun save(item: Ad): Ad {
        val row = AdRow(item)
        if (row.id == null) {
            throw IllegalArgumentException("Id is empty")
        }
        cache.put(row.id, row)
        return row.toInternal()
    }

    override suspend fun create(req: Ad): Ad =
        save(req.copy(id = UUID.randomUUID().toString()))

    override suspend fun get(req: Ad): Ad =
        cache.get(req.id)?.toInternal() ?: throw Throwable("Ad not found")

    override suspend fun update(req: Ad): Ad {
        val key = req.id.takeIf { it.isNotBlank() }
            ?: throw IllegalArgumentException("Id is empty")

        return if (cache.containsKey(key as String?)) {
            save(req)
        } else throw Throwable("Ad not found")
    }

    override suspend fun delete(req: Ad): Ad {
        val key = req.id.takeIf { it.isNotBlank() }
            ?: throw IllegalArgumentException("Id is empty")

        val row = cache.get(key) ?: throw IllegalArgumentException("Id is empty")
        cache.remove(key)
        return row.toInternal()
    }
}
