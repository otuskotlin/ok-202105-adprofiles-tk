import repo.IRepoProfile
import repo.inmemory.RepoProfileInMemory
import repo.test.RepoProfileCreateTest

class RepoProfileInMemoryCreateTest : RepoProfileCreateTest() {
    override val repo: IRepoProfile = RepoProfileInMemory(
        initObjects = initObjects
    )
}
