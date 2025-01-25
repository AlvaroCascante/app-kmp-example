package org.kskntprojects.kmp.repositories

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kskntprojects.kmp.models.Founder

interface FounderLocalDatasource {
    fun getFounders(): Flow<List<Founder>>
}

val foundersList = listOf(
    Founder("Carlos Gutierrez", "Big Boss", Icons.Default.Person),
    Founder("Alberto Cambronero", "Python Expert", Icons.Default.Person),
    Founder("Daniel Rodriguez", "Camera Boy", Icons.Default.Person),
    Founder("Daniel Villaplana", "Gamification", Icons.Default.Person),
    Founder("Mariano Rivera", "AI Magician", Icons.Default.Person),
    Founder("Santiago Gonzalez", "FE & Marketing", Icons.Default.Person),
    Founder("Arturo Zamora", "UI UX", Icons.Default.Person),
    Founder("Alvaro Cascante", "Kotlin", Icons.Default.Person)
)

class FounderLocalDatasourceImpl: FounderLocalDatasource {
    override fun getFounders(): Flow<List<Founder>> {
        return flow {
            emit(foundersList)
        }
    }
}