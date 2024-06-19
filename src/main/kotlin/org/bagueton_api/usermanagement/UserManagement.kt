import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/register")
    fun register(@RequestBody user: User): String {
        if (user.username?.let { userRepository.findByUsername(it) } != null) {
            return "Username is already taken!"
        }

        val newUser = User(
            username = user.username,
            password = passwordEncoder.encode(user.password),
            role = "ROLE_USER"
        )

        userRepository.save(newUser)
        return "User registered successfully"
    }

    @PostMapping("/login")
    fun login(): String {
        return "Login successful"
    }
}
