package com.example.seivink.db.dao

import androidx.room.*
import com.example.seivink.db.entity.User

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    fun findUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE email = :email AND passwordHash = :passwordHash LIMIT 1")
    fun loginUser(email: String, passwordHash: String): User?

}