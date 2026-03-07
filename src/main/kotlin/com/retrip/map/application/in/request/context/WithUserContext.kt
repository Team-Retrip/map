package com.retrip.map.application.`in`.request.context

import io.swagger.v3.oas.annotations.Parameter


@Retention(AnnotationRetention.RUNTIME)
@Target(allowedTargets = [AnnotationTarget.VALUE_PARAMETER])
@Parameter(hidden = true)
annotation class WithUserContext()
