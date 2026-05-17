package com.retrip.map.infra.adapter.`in`.presentation.resolver

import com.retrip.map.application.`in`.request.context.UserContext
import com.retrip.map.application.`in`.request.context.WithUserContext
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class UserContextArgumentResolver: HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(WithUserContext::class.java)
    }

    override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): UserContext {
        return webRequest.getAttribute("userContext", RequestAttributes.SCOPE_REQUEST) as? UserContext
            ?: UserContext(memberId = null)
    }

}
