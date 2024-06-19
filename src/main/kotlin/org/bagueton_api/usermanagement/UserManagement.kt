package org.bagueton_api.usermanagement

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.User

@Entity
@Table(name = "users")
data class ApplicationUser(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val username: String? = null,
    val password: String? = null,
    val role: String? = null,
)

@Repository
interface UserRepository : JpaRepository<ApplicationUser, Long> {
    fun findByUsername(username: String): ApplicationUser?
}

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): User {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")

        return User(
            user.username, user.password,
            listOf(SimpleGrantedAuthority(user.role))
        )
    }
}