package WorkTracker.db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Schema {

    /** Root of database work */
    fun create() {
        transaction {
            SchemaUtils.create(Users)

            insertUser("John", 36)
            insertUser("Goerg", 52)
        }
    }

    /** Tables */
    object Users : Table() {
        val id: Column<Int> = integer("id").autoIncrement()
        val name: Column<String> = varchar("name", 255)
        val age: Column<Int> = integer("age")
    
        override val primaryKey = PrimaryKey(id, name="PK_User_ID")

        fun toUser(row: ResultRow): User = User(
            id = row[id],
            name = row[name],
            age = row[age]
        )
    }
    data class User(
        val id: Int? = null, 
        val name: String, 
        val age: Int
    )

    /** Operations */
    fun Transaction.insertUser(name: String, age: Int) {
        Users.insert {
            it[Users.name] = name
            it[Users.age] = age
        }
    }

    fun insertUser(name: String, age: Int) {
        transaction {
            Users.insert {
                it[Users.name] = name
                it[Users.age] = age
            }
        }
    }
}

