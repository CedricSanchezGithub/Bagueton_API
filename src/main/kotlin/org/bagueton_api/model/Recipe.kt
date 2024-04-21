package org.bagueton_api.model

import jakarta.persistence.*
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service


// Entité représentant un ingrédient avec son nom, quantité et unité.
// @Column(length = 10000) garanti une longeur suffisante de la colonne.
// Utile lors de migrations par exemple (mysql vers une autre bdd)
//@Entity
//@Table(name = "bagueton_recipe")
//data class RecipeBean(
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id_recipe: Long? = null,
//    var title: String? = null,
//    var image: String? = "http://90.51.140.217:8081//logo.png",
//    var ingredients: String? = "",
//    @Column(length = 10000)
//    var steps: String? = ""
//)
@Entity
@Table(name = "recipes")
data class RecipeBean(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var title: String? = null,
    var image: String = "http://90.51.140.217:8081/logo.png",
    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var ingredients: Set<Ingredients> = HashSet(),
    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var steps: Set<Steps> = HashSet()
)

@Entity
@Table(name = "ingredients")
data class Ingredients(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var name: String? = null,
    var quantity: String? = null,  // Quantité et unité combinées pour simplifier
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    var recipe: RecipeBean? = null
)

@Entity
@Table(name = "steps")
data class Steps(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var stepNumber: Int? = null,
    var description: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    var recipe: RecipeBean? = null
)

// Repository pour accéder aux opérations de base de données des recettes.
@Repository interface RecipeRepository : JpaRepository<RecipeBean, Long>

// Service gérant la logique métier de la création de recettes.
@Service
class RecipeService(val recipeRepository: RecipeRepository) {



//    // Crée une recette avec un nomme, des étapes et des ingrédients
//    fun createRecipe(name: String, steps: String?, ingredients: String?) {
//        if (name.isBlank()) {
//            throw Exception("Le titre de la recette est obligatoire.")
//        }
//        // Création et sauvegarde de l'entité RecipeBean
//        val recipe = RecipeBean(title = name, steps = steps, ingredients = ingredients)
//        println("Sauvegarde de la recette suivante: ${recipe.title}")
//        recipeRepository.save(recipe)
//    }

    @Service
    class RecipeService(val recipeRepository: RecipeRepository) {
        // Crée une recette avec un nom, des étapes et des ingrédients
        fun createRecipe(name: String, ingredientList: List<String>, stepList: List<String>) {
            if (name.isBlank()) {
                throw Exception("Le titre de la recette est obligatoire.")
            }

            val ingredients = ingredientList.map {
                Ingredients(name = it, quantity = "some quantity") // Modifiez en fonction de vos besoins
            }.toSet()

            val steps = stepList.mapIndexed { index, step ->
                Steps(stepNumber = index + 1, description = step)
            }.toSet()

            val recipe = RecipeBean(title = name, ingredients = ingredients, steps = steps)

            println("Sauvegarde de la recette suivante: ${recipe.title}")
            recipeRepository.save(recipe)
        }
    }


    // Permet de vérifier qu'une id (donc une recette) existe
    fun existsById(id: Long): Boolean = recipeRepository.existsById(id)

    // Récupère toutes les recettes disponibles dans la base de données.
    fun findAllRecipes(): MutableList<RecipeBean> = recipeRepository.findAll()

    // Supprime la recette via son id, renvoie un booléen indiquant si la suppression a été effectuée.
    fun deleteRecipe(id: Long): Boolean {
        if (!recipeRepository.existsById(id)) {
            return false
        }
        recipeRepository.deleteById(id)
        return true
    }

    // Met à jour la recette via son id
    fun updateRecipe(id: Long, updatedRecipe: RecipeBean) {
        val recipeToUpdateOptional = recipeRepository.findById(id)
        if (recipeToUpdateOptional.isEmpty) {
            throw Exception("Aucune recette trouvée avec l'ID spécifié.") // Consider using a custom exception
        }

        val recipeToUpdate = recipeToUpdateOptional.get()

        // Mise à jour des champs non nulls uniquement
        updatedRecipe.title?.let { recipeToUpdate.title = it }
        updatedRecipe.steps?.let { recipeToUpdate.steps = it }
        updatedRecipe.ingredients?.let { recipeToUpdate.ingredients = it }

        recipeRepository.save(recipeToUpdate) // Sauvegarde des modifications
    }
}


