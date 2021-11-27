package context

import repo.IRepoProfile

data class ContextConfig(
    val repoProd: IRepoProfile = IRepoProfile.NONE,
    val repoTest: IRepoProfile = IRepoProfile.NONE,
)
