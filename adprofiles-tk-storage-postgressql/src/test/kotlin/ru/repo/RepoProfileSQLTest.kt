package ru.repo

import repo.IRepoProfile
import repo.test.RepoProfileCreateTest
import repo.test.RepoProfileDeleteTest
import repo.test.RepoProfileReadTest
import repo.test.RepoProfileUpdateTest

class RepoProfileSQLCreateTest : RepoProfileCreateTest() {
    override val repo: IRepoProfile = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoProfileSQLDeleteTest : RepoProfileDeleteTest() {
    override val repo: IRepoProfile = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoProfileSQLReadTest : RepoProfileReadTest() {
    override val repo: IRepoProfile = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class RepoProfileSQLUpdateTest : RepoProfileUpdateTest() {
    override val repo: IRepoProfile = SqlTestCompanion.repoUnderTestContainer(initObjects)
}
