package cz.monetplus

import org.jetbrains.exposed.v1.jdbc.Database
 
fun configureDatabases() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/postgres",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "root"
    )
}