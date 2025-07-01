package com.apps.aivisioncmp.domain.repository

import org.jetbrains.compose.resources.StringResource


interface LocalResourceRepository {

    fun getTextExamples():List<StringResource>
}

