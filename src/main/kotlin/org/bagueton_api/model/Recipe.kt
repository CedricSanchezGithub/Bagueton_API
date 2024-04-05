package org.bagueton_api.model

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service


// Entité représentant un ingrédient avec son nom, quantité et unité.
// @Column(length = 10000) garanti une longeur suffisante de la colonne.
// Utile lors de migrations par exemple (mysql vers une autre bdd)
@Entity
@Table(name = "bagueton_recipe")
data class RecipeBean(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id_recipe: Long? = null,
    val title: String? = null,
    val image: String? = "http://2.9.163.31:8081/logo.png",
    val ingredients: String? = "",
    @Column(length = 10000)
    val steps: String? = ""
)



/*
// Entité représentant une recette, incluant un titre, une image, une liste d'ingrédients
// et des étapes de préparation.
@Entity
@Table(name = "bagueton_ingredients")
data class Ingredient(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val name: String? = null,
    val quantity: Double? = null,
    val unit: String? = null,
    @ManyToOne // Chaque ingrédient appartient à une recette
    @JoinColumn(name = "recipe_id") // Crée une colonne de clé étrangère dans 'ingredient' pointant vers 'recipe'
    var recipe: RecipeBean? = null
)
*/


// Repository pour accéder aux opérations de base de données des recettes.

@Repository interface RecipeRepository : JpaRepository<RecipeBean, Long>

/*
Repository pour accéder aux opérations de base de données des ingrédients.
@Repository
interface IngredientRepository : JpaRepository<Ingredient, Long>
*/

// Service gérant la logique métier de la création de recettes.
@Service
class RecipeService(val recipeRepository: RecipeRepository) {


    fun createRecipe(name: String, steps: String?, ingredients: String?) {
        if (name.isNullOrBlank()) {
            throw Exception("Le titre de la recette est obligatoire.")
        }
        // Création et sauvegarde de l'entité RecipeBean
        val recipe = RecipeBean(title = name, steps = steps, ingredients = ingredients)
        println("Sauvegarde de la recette suivante: ${recipe.title}")
        recipeRepository.save(recipe)
    }

    // Récupère toutes les recettes disponibles dans la base de données.
    fun findAllRecipes(): MutableList<RecipeBean> = recipeRepository.findAll()
}

