import kotlinx.coroutines.runBlocking
import model.Ad
import org.junit.Test
import ru.otus.otuskotlin.marketplace.backend.repo.common.IRepoAd
import ru.otus.otuskotlin.marketplace.backend.repo.inmemory.RepoAdInMemory
import kotlin.test.assertNotNull

class RepoAdInMemoryTest {
    private val repo: IRepoAd = RepoAdInMemory()

    @Test
    fun createAd() {
        val result = runBlocking { repo.create(Ad()) }
        assertNotNull(result.id)
    }
}
