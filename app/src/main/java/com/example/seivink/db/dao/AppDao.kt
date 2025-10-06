package com.example.seivink.db.dao

import androidx.room.*
import com.example.seivink.db.entity.SavingGoal
import com.example.seivink.db.entity.User
import kotlinx.coroutines.flow.Flow
import com.example.seivink.db.entity.Transaction


@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    fun findUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE email = :email AND passwordHash = :passwordHash LIMIT 1")
    fun loginUser(email: String, passwordHash: String): User?


    // --- NUEVAS OPERACIONES (para la pantalla principal) ---

    // Funciones para insertar nuevos datos
    @Insert
    fun insertTransaction(transaction: Transaction)

    @Insert
    fun insertSavingGoal(goal: SavingGoal)

    // Funciones "observables" con Flow para la UI de Compose
    // Room se encarga de que se actualicen automáticamente

    @Query("SELECT * FROM transactions WHERE userId = :userId ORDER BY date DESC")
    fun getAllTransactionsForUser(userId: Int): Flow<List<Transaction>>

    @Query("SELECT * FROM saving_goals WHERE userId = :userId ORDER BY creationDate DESC")
    fun getAllSavingGoalsForUser(userId: Int): Flow<List<SavingGoal>>

    @Query("SELECT SUM(CASE WHEN type = 'income' THEN amount ELSE -amount END) FROM transactions WHERE userId = :userId")
    fun getTotalBalanceForUser(userId: Int): Flow<Double?> // Devuelve un Double que puede ser null si no hay transacciones

}