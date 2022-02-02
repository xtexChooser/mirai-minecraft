package com.xtex.mirai.minecraft.data.mojang

import kotlinx.serialization.Serializable

@Serializable
data class StatisticsRequest(
    val metricKeys: List<String>
)

@Serializable
data class StatisticsResponse(
    val total: Long,
    val last24h: Long,
    val saleVelocityPerSeconds: Float
) {

    fun buildMessage() = "共 $total 份，最近24小时 $last24h 份，平均 ${1f / saleVelocityPerSeconds} s/份"

}
