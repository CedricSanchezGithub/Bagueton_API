package org.bagueton_api.model

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service


// Entité représentant un ingrédient avec son nom, quantité et unité.
@Entity
@Table(name = "bagueton_recipe")
data class Ingredient(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val name: String? = null,
    val quantity: Double? = null,
    val unit: String? = null
)

// Entité représentant une recette, incluant un titre, une image, une liste d'ingrédients et des étapes de préparation.
data class RecipeBean(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String? = null,
    val image: String? = null,
    val ingredients: List<Ingredient>? = null,
    val steps: List<String>? = null
)

// Repository pour accéder aux opérations de base de données des recettes.
@Repository
interface RecipeRepository : JpaRepository<RecipeBean, Long>

// Service gérant la logique métier des recettes, notamment la création et la récupération de recettes.
@Service
class RecipeService(val recipeRep: RecipeRepository) {

    // Crée une nouvelle recette après validation des champs obligatoires.
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

    // Récupère toutes les recettes disponibles dans la base de données.
    fun findAllRecipes(): MutableList<RecipeBean> = recipeRep.findAll()
}

