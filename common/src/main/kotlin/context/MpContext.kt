package context

import models.*
import repo.IRepoProfile
import java.time.Instant

data class MpContext(
    var startTime : Instant = Instant.MIN,
    var operation: MpOperations = MpOperations.NONE,
    var workMode: WorkMode = WorkMode.PROD,
    var stubCase: MpStubCase = MpStubCase.NONE,
    var config: ContextConfig = ContextConfig(),


    var onRequest: String = "",
    var requestProfileId: ProfileIdModel = ProfileIdModel.NONE,
    var requestProfile: ProfileModel = ProfileModel(),
    var dbProfile: ProfileModel = ProfileModel(),
    var permitted: Boolean = false,
    var profileRepo: IRepoProfile = IRepoProfile.NONE,
    val userSession: IUserSession<*> = IUserSession.Companion.EmptySession,

    var responseProfile: ProfileModel = ProfileModel(),
    var responseProfiles: MutableList<ProfileModel> = mutableListOf(),
    val errors: MutableList<IError> = mutableListOf(),
    var status: CorStatus = CorStatus.NONE,
) {
    enum class MpOperations {
        NONE,
        INIT,
        CREATE,
        READ,
        UPDATE,
        DELETE
    }

    /**
     * Добавляет ошибку в контекст
     *
     * @param error Ошибка, которую необходимо добавить в контекст
     * @param failingStatus Необходимо ли установить статус выполнения в FAILING (true/false)
     */
    suspend fun addError(error: IError, failingStatus: Boolean = true) = apply {
        if (failingStatus) status = CorStatus.FAILING
        errors.add(error)
    }


    suspend fun addError(
        e: Throwable,
        level: IError.Level = IError.Level.ERROR,
        field: String = "",
        failingStatus: Boolean = true
    ) {
        addError(CommonErrorModel(e, field = field, level = level), failingStatus)
    }
}
