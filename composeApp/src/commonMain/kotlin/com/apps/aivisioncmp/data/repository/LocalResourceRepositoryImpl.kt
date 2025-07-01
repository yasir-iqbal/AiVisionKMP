package com.apps.aivisioncmp.data.repository

import aivisionkmp.composeapp.generated.resources.Res
import aivisionkmp.composeapp.generated.resources.examples_1
import aivisionkmp.composeapp.generated.resources.examples_2
import aivisionkmp.composeapp.generated.resources.examples_3
import com.apps.aivisioncmp.domain.repository.LocalResourceRepository
import org.jetbrains.compose.resources.StringResource

class LocalResourceRepositoryImpl(): LocalResourceRepository {
    override fun getTextExamples(): List<StringResource> = listOf(Res.string.examples_1,Res.string.examples_2,Res.string.examples_3)
}