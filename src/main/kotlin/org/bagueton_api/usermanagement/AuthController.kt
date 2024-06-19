package org.bagueton_api.usermanagement

import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

data class ApiResponse(
    val message: String
)
@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    private val logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/register")
    fun register(@RequestBody user: ApplicationUser): ApiResponse {
        logger.info("Registering user: ${user.username}")
        if (userRepository.findByUsername(user.username!!) != null) {
            logger.warn("Username is already taken: ${user.username}")
            return ApiResponse(message = "Username is already taken!")
        }

        val newUser = ApplicationUser(
            username = user.username,
            password = passwordEncoder.encode(user.password),
            role = "ROLE_USER"
        )

        userRepository.save(newUser)
        logger.info("User registered successfully: ${user.username}")
        return ApiResponse( message = "User registered successfully")
    }

    @PostMapping("/login")
    fun login(): ApiResponse {
        logger.info("User login attempt")
        return ApiResponse( message = "Login successful")
    }
}