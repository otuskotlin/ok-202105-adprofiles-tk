package logics.workers


import cor.ICorChainDsl
import cor.handlers.worker
import context.MpContext
import models.MpStubCase
import models.WorkMode
import repo.IRepoProfile

internal fun ICorChainDsl<MpContext>.chooseDb(title: String) = worker {
    this.title = title
    description = """
        Here we choose either prod or test DB repository. 
        In case of STUB request here we use empty repo and set stubCase=SUCCESS if unset
    """.trimIndent()

    handle {
        profileRepo = when(workMode) {
            WorkMode.PROD -> config.repoProd
            WorkMode.TEST -> config.repoTest
            WorkMode.STUB -> {
                if (stubCase == MpStubCase.NONE){
                    stubCase = MpStubCase.SUCCESS
                }
                IRepoProfile.NONE
            }
        }
    }
}
