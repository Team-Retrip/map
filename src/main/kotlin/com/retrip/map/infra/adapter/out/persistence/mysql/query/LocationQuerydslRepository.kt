package com.retrip.map.infra.adapter.out.persistence.mysql.query

import com.querydsl.core.types.Predicate
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.retrip.map.application.`in`.response.LocationResponse
import com.retrip.map.application.out.repository.LocationQueryRepository
import com.retrip.map.domain.entity.Location
import com.retrip.map.domain.entity.QLocation.location
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


@Repository
class LocationQuerydslRepository(
    private val query: JPAQueryFactory
) : LocationQueryRepository {
    override fun findLocations(id: UUID?, page: Pageable): Page<LocationResponse> {
        val locations = query.select(
            Projections.constructor(
                LocationResponse::class.java,
                location.id,
                location.name.value,
                location.category.value,
                location.description.value,
                location.telephone,
                location.address.address,
                location.address.roadAddress,
                location.geoPoint.latitude,
                location.geoPoint.longitude
            )
        ).from(location)
            .where(
                eqLocation(id)
            )
            .offset(page.offset)
            .limit(page.pageSize.toLong())
            .orderBy(location.createdAt.desc())
            .fetch()
        val count = query
            .select(location.count())
            .from(location)
            .where(
                eqLocation(id)
            )
            .fetchOne()
        return PageImpl(locations, page, count ?: 0)
    }

    override fun findLocationsByEditedAt(editedAt: LocalDateTime): List<Location> {
        return query.selectFrom(location)
            .where(
                location.editedAt.gt(editedAt)
            ).fetch()
    }

    private fun eqLocation(id: UUID?): Predicate? {
        return id?.let { location.id.eq(it) }
    }
}
