package org.bagueton_api.model
/*

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Entity
@Table(name = "user")
data class UserBean(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val username: String?,
    val password: String?
)

@Repository
interface UserRepository : JpaRepository<UserBean, Long>

@Service
class UserService(val userRepository: UserRepository) {

    // Crée une nouvelle recette après validation des champs obligatoires.
    fun createUser(username: String?, password: String?) {
        if (username.isNullOrBlank()) {
            throw Exception("Il manque le pseudo")
        }
        if (password.isNullOrEmpty()) {
            throw Exception("Il manque le password")
        }

        val newUser = UserBean(null, username, password)
        userRepository.save(newUser)
    }
}*/
