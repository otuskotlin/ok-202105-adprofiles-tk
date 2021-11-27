import repo.IRepoProfile
import repo.inmemory.RepoProfileInMemory
import repo.test.RepoProfileReadTest

class RepoProfileInMemoryReadTest : RepoProfileReadTest() {
    override val repo: IRepoProfile = RepoProfileInMemory(
        initObjects = initObjects
    )
}
