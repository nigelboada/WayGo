package com.example.waygo.data.repository


import com.example.waygo.data.local.dao.AccessLogDao
import com.example.waygo.data.local.entity.AccessLogEntity
import com.example.waygo.domain.repository.AccessLogRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessLogRepositoryImpl @Inject constructor(
    private val accessLogDao: AccessLogDao
) : AccessLogRepository {
    override suspend fun insertAccessLog(accessLog: AccessLogEntity): Boolean {
        return accessLogDao.insertAccessLog(accessLog) > 0
    }
}
