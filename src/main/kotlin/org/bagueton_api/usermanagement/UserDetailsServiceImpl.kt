package org.bagueton_api.usermanagement

import UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): org.springframework.security.core.userdetails.UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")

        return org.springframework.security.core.userdetails.User(
            user.username, user.password,
            listOf(org.springframework.security.core.authority.SimpleGrantedAuthority(user.role))
        )
    }
}
