package com.retrip.map.infra.adapter.out.persistence.mysql.query

import com.querydsl.core.types.Predicate
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.retrip.map.application.`in`.response.LocationDetailResponse
import com.retrip.map.application.out.repository.LocationDetailQueryRepository
import com.retrip.map.domain.entity.LocationDetail
import com.retrip.map.domain.entity.QLocationDetail.locationDetail
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*
import kotlin.io.path.name


@Repository
class LocationDetailQuerydslRepository(
    private val query: JPAQueryFactory
) : LocationDetailQueryRepository {
    override fun findLocationDetails(locationDetailIds: List<UUID>): List<LocationDetailResponse> {
        return query.select(
            Projections.constructor(
                LocationDetailResponse::class.java,
                locationDetail.id,
                locationDetail.name.value,
                locationDetail.category.value,
                locationDetail.description.value,
                locationDetail.telephone,
                locationDetail.address.address,
                locationDetail.address.roadAddress,
                locationDetail.geoPoint.latitude,
                locationDetail.geoPoint.longitude,
                locationDetail.type
            )
        ).from(locationDetail)
            .where(
                locationDetail.id.`in`(locationDetailIds)
            )
            .orderBy(locationDetail.createdAt.desc())
            .fetch()
    }


    override fun findLocationDetailsByPage(locationId: UUID, id: UUID?, page: Pageable): Page<LocationDetailResponse> {
        val locationDetails = query.select(
            Projections.constructor(
                LocationDetailResponse::class.java,
                locationDetail.id,
                locationDetail.name.value,
                locationDetail.category.value,
                locationDetail.description.value,
                locationDetail.telephone,
                locationDetail.address.address,
                locationDetail.address.roadAddress,
                locationDetail.geoPoint.latitude,
                locationDetail.geoPoint.longitude,
                locationDetail.type
            )
        ).from(locationDetail)
            .where(
                eqLocationDetail(id), eqLocation(locationId)
            )
            .offset(page.offset)
            .limit(page.pageSize.toLong())
            .orderBy(locationDetail.createdAt.desc())
            .fetch()
        val count = query
            .select(locationDetail.count())
            .from(locationDetail)
            .where(
                eqLocation(id)
            )
            .fetchOne()
        return PageImpl(locationDetails, page, count ?: 0)
    }

    override fun findLocationDetailsByEditedAt(editedAt: LocalDateTime): List<LocationDetail> {
        return query.selectFrom(locationDetail)
            .where(
                locationDetail.editedAt.gt(editedAt)
            ).fetch()
    }

    private fun eqLocation(locationId: UUID?): Predicate? {
        return locationId?.let { locationDetail.location.id.eq(it) }
    }
    private fun eqLocationDetail(id: UUID?): Predicate? {
        return id?.let { locationDetail.id.eq(it) }
    }
}
