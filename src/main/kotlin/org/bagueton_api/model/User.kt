package org.bagueton_api.model

import jakarta.persistence.*
import org.apache.catalina.startup.PasswdUserDatabase

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val username: String ? = "Utilisateur $id",
    val password: String ?
)
