import repo.IRepoProfile
import repo.inmemory.RepoProfileInMemory
import repo.test.RepoProfileDeleteTest

class RepoProfileInMemoryDeleteTest : RepoProfileDeleteTest() {
    override val repo: IRepoProfile = RepoProfileInMemory(
        initObjects = initObjects
    )
}
