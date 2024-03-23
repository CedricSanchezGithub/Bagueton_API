package org.bagueton_api.model

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Entity
@Table(name = "favorite_recipe")
data class FavoriteBean(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User ? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    val recipe: RecipeBean ? = null
)


@Repository
interface FavoriteRepository : JpaRepository<FavoriteBean, Long>

@Service
class FavoriteService(val favoriteRep:FavoriteRepository) {

}