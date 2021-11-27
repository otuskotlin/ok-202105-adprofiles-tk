package repo.inmemory

import kotlinx.coroutines.runBlocking
import models.CommonErrorModel
import models.ProfileIdModel
import models.ProfileModel
import org.ehcache.Cache
import org.ehcache.CacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import repo.DbProfileIdRequest
import repo.DbProfileModelRequest
import repo.DbProfileResponse
import repo.IRepoProfile
import repo.inmemory.models.ProfileRow
import java.time.Duration
import java.util.*


class RepoProfileInMemory(
    private val initObjects: List<ProfileModel> = listOf(),
    private val ttl: Duration = Duration.ofMinutes(1)
) : IRepoProfile {

    private val cache: Cache<String, ProfileRow> = let {
        val cacheManager: CacheManager = CacheManagerBuilder
            .newCacheManagerBuilder()
            .build(true)

        cacheManager.createCache(
            "marketplace-ad-cache",
            CacheConfigurationBuilder
                .newCacheConfigurationBuilder(
                    String::class.java,
                    ProfileRow::class.java,
                    ResourcePoolsBuilder.heap(100)
                )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(ttl))
                .build()
        )
    }

    init {
        runBlocking { initObjects.forEach {
            save(it)
        } }
    }

    private suspend fun save(item: ProfileModel): DbProfileResponse {
        val row = ProfileRow(item)
        if (row.id == null) {
            return DbProfileResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(
                        field = "id",
                        message = "Id must not be null or blank"
                    )
                )
            )
        }
        cache.put(row.id, row)
        return DbProfileResponse(
            result = row.toInternal(),
            isSuccess = true
        )
    }

    override suspend fun create(req: DbProfileModelRequest): DbProfileResponse =
        save(req.profile.copy(id = ProfileIdModel(UUID.randomUUID().toString())))

    override suspend fun read(req: DbProfileIdRequest): DbProfileResponse = cache.get(req.id.asString())?.let {
        DbProfileResponse(
            result = it.toInternal(),
            isSuccess = true
        )
    } ?: DbProfileResponse(
        result = null,
        isSuccess = false,
        errors = listOf(
            CommonErrorModel(
                field = "id",
                message = "Not Found"
            )
        )
    )

    override suspend fun update(req: DbProfileModelRequest): DbProfileResponse {
        val key = req.profile.id.takeIf { it != ProfileIdModel.NONE }?.asString()
            ?: return DbProfileResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(field = "id", message = "Id must not be null or blank")
                )
            )

        return if (cache.containsKey(key)) {
            save(req.profile)
            DbProfileResponse(
                result = req.profile,
                isSuccess = true
            )
        } else {
            DbProfileResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(
                        field = "id",
                        message = "Not Found"
                    )
                )
            )
        }
    }

    override suspend fun delete(req: DbProfileIdRequest): DbProfileResponse {
        val key = req.id.takeIf { it != ProfileIdModel.NONE }?.asString()
            ?: return DbProfileResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(field = "id", message = "Id must not be null or blank")
                )
            )
        val row = cache.get(key) ?: return DbProfileResponse(
            result = null,
            isSuccess = false,
            errors = listOf(
                CommonErrorModel(field = "id", message = "Not Found")
            )
        )
        cache.remove(key)
        return DbProfileResponse(
            result = row.toInternal(),
            isSuccess = true,
        )
    }
}
