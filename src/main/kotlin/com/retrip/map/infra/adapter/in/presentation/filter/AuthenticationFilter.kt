package com.retrip.map.infra.adapter.`in`.presentation.filter

import com.retrip.map.application.`in`.request.context.UserContext
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.*

@Component
class AuthenticationFilter : OncePerRequestFilter() {
    @Value("\${jwt.public-key}")
    private val publicKey: String? = null

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val path = request.requestURI.lowercase()

        when {
            path == "/" ||
                    path.contains("swagger") ||
                    path.contains("api-docs") ||
                    path.contains("actuator") ||
                    path.contains("robots.txt") ||
                    path.contains("h2-console") ||
                    path.contains("status-check") ||
                    path.startsWith("/locations") || // 장소 등록 ADMIN
                    path.startsWith("/location-details") // 장소 상세 등록 ADMIN
                -> {
                filterChain.doFilter(request, response)
                return
            }
        }
        val token = resolveToken(request)
        if (token.isNullOrBlank()) {
            // Token 복호화 실패
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return
        }
        try {
            val claims = getClaims(token)
            val subject = claims?.subject ?: throw IllegalStateException()
            val memberId = UUID.fromString(subject)

            val userContext = UserContext(memberId)
            request.setAttribute("userContext", userContext)
        } catch (e: Exception) {
            //Token 내 원하는 값 없음
            response.status = HttpServletResponse.SC_FORBIDDEN
            return;
        }
        filterChain.doFilter(request, response)

    }


    private fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        if (bearerToken.isNullOrBlank() || !bearerToken.startsWith("Bearer")) return null
        return bearerToken.substring(7)
    }

    private fun getClaims(token: String): Claims? {
        val sanitizedKey = publicKey
            ?.replace("-----BEGIN PUBLIC KEY-----", "")
            ?.replace("-----END PUBLIC KEY-----", "")
            ?.replace("\\s".toRegex(), "")
        // 2. RSA 공개키 생성 (run 스코프 함수로 관련 로직 묶기)
        val publicKey = KeyFactory.getInstance("RSA").run {
            val publicBytes = Base64.getDecoder().decode(sanitizedKey)
            val keySpec = X509EncodedKeySpec(publicBytes)
            generatePublic(keySpec)
        }

        return Jwts.parser()
            .verifyWith(publicKey as RSAPublicKey) // 타입 캐스팅으로 명확성 확보
            .build()
            .parseSignedClaims(token)
            .payload
    }


}
