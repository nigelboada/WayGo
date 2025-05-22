package com.example.waygo.domain.repository

import com.example.waygo.data.local.entity.AccessLogEntity

interface AccessLogRepository {
    suspend fun insertAccessLog(accessLog: AccessLogEntity): Boolean
}
