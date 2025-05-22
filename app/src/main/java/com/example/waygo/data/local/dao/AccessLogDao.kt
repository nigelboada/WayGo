package com.example.waygo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.waygo.data.local.entity.AccessLogEntity

@Dao
interface AccessLogDao {

    @Insert
    suspend fun insertAccessLog(accessLog: AccessLogEntity): Long

    @Query("SELECT * FROM access_logs WHERE userId = :userId ORDER BY timestamp DESC")
    suspend fun getAccessLogsForUser(userId: Int): List<AccessLogEntity>
}
