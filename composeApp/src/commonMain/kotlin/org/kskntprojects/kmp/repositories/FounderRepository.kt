package org.kskntprojects.kmp.repositories

import kotlinx.coroutines.flow.Flow
import org.kskntprojects.kmp.models.Founder

interface FounderRepository {
    fun getFounders(): Flow<List<Founder>>
}

class FounderRepositoryImpl(
    private val localDatasource: FounderLocalDatasource
): FounderRepository {
    override fun getFounders(): Flow<List<Founder>> {
        return localDatasource.getFounders()
    }
}