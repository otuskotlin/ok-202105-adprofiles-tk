package repo

interface IRepoProfile {
    suspend fun create(req: DbProfileModelRequest): DbProfileResponse
    suspend fun read(req: DbProfileIdRequest): DbProfileResponse
    suspend fun update(req: DbProfileModelRequest): DbProfileResponse
    suspend fun delete(req: DbProfileIdRequest): DbProfileResponse

    object NONE : IRepoProfile {
        override suspend fun create(req: DbProfileModelRequest): DbProfileResponse {
            TODO("Not yet implemented")
        }

        override suspend fun read(req: DbProfileIdRequest): DbProfileResponse {
            TODO("Not yet implemented")
        }

        override suspend fun update(req: DbProfileModelRequest): DbProfileResponse {
            TODO("Not yet implemented")
        }

        override suspend fun delete(req: DbProfileIdRequest): DbProfileResponse {
            TODO("Not yet implemented")
        }

    }
}
