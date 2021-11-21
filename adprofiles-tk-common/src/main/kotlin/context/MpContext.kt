package context

import model.Ad
import ru.otus.otuskotlin.marketplace.backend.repo.common.IRepoAd

data class MpContext(
    var ad: Ad = Ad(),
    var operation: MpOperations = MpOperations.NONE,
    var adRepo: IRepoAd
){

    enum class MpOperations {
        NONE,
        GET,
        CREATE,
        UPDATE,
        DELETE
    }
}
