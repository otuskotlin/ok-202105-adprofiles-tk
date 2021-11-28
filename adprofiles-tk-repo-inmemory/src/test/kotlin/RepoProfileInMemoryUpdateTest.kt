import repo.IRepoProfile
import repo.inmemory.RepoProfileInMemory
import repo.test.RepoProfileUpdateTest

class RepoProfileInMemoryUpdateTest : RepoProfileUpdateTest() {
    override val repo: IRepoProfile = RepoProfileInMemory(
        initObjects = initObjects
    )
}
