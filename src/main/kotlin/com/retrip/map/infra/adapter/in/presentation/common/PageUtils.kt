package com.retrip.map.infra.adapter.`in`.presentation.common

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

object PageUtils {
    fun getSafePageable(pageable: Pageable): Pageable {
        if (pageable.sort.isUnsorted) {
            return pageable
        }

        val validOrders = pageable.sort.filter { order ->
            order.property.isNotBlank() && order.property != "[]"
        }.toList()


        return if (validOrders.isEmpty()) {
            PageRequest.of(pageable.pageNumber, pageable.pageSize)
        } else {
            PageRequest.of(pageable.pageNumber, pageable.pageSize, Sort.by(validOrders))
        }
    }
}
