package org.bagueton_api.model

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Entity
@Table(name = "bagueton_recipe")

data class Ingredient(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long? = null,
    val name: String? = null,
    val quantity: Double? = null,
    val unit: String? = null // Par exemple: "g" pour grammes, "ml" pour millilitres, etc.
)
data class RecipeBean(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String? = null,
    val image: String? = null,
    val ingredients: List<Ingredient>? = null,
    val steps: List<String>? = null // Étapes de préparation
)




@Repository
interface RecipeRepository : JpaRepository<RecipeBean, Long>

@Service
class RecipeService(val recipeRep: RecipeRepository) {

    fun createRecipe(title: String?, image: String? = null, ingredients: List<Ingredient>?, steps: List<String>?) {
        if (title.isNullOrBlank()) {
            throw Exception("Il manque le nom de la recette")
        }
        if (ingredients.isNullOrEmpty()) {
            throw Exception("Il manque les ingrédients de la recette")
        }
        if (steps.isNullOrEmpty()) {
            throw Exception("Il manque les étapes de la recette")
        }

        val recipe = RecipeBean(null, title, image, ingredients, steps)
        recipeRep.save(recipe)
    }

    fun findAllRecipes(): MutableList<RecipeBean> {
        return recipeRep.findAll()

    }
}
