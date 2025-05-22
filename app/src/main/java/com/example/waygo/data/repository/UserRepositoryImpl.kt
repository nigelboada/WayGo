package com.example.waygo.data.repository


import com.example.waygo.data.local.dao.UserDao
import com.example.waygo.data.local.mapper.toDomain
import com.example.waygo.data.local.mapper.toEntity
import com.example.waygo.domain.model.User
import com.example.waygo.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUserById(id: String): User? {
        return userDao.getUserById(id)?.toDomain()
    }

    override suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers().map { it.toDomain() }
    }

    override suspend fun updateUser(user: User): Boolean {
        return userDao.updateUser(user.toEntity()) > 0
    }

    override suspend fun deleteUser(id: Int): Boolean {
        return userDao.deleteUser(id) > 0
    }

    override suspend fun insertUser(user: User): Boolean {
        return userDao.addUser(user.toEntity()) > 0
    }

    override suspend fun getUserByFirebaseUid(firebaseUid: String): User? {
        return userDao.getUserByFirebaseUid(firebaseUid)?.toDomain()
    }

}
