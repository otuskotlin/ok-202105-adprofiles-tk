package ru.otus.otuskotlin.marketplace.backend.repo.common

import model.Ad

interface IRepoAd {
    suspend fun create(req: Ad): Ad
    suspend fun get(req: Ad): Ad
    suspend fun update(req: Ad): Ad
    suspend fun delete(req: Ad): Ad
}
