package com.apps.aivisioncmp.domain.usecase.app

import com.apps.aivisioncmp.domain.repository.LocalResourceRepository

class GetStringResourceExampleUseCase(private  val repository: LocalResourceRepository) {
    operator fun invoke() = repository.getTextExamples()
}