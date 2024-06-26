package org.bagueton_api.recipe

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service


// Fetch Lazy passé en EAGER pour tenter de régler le probleme de serialisation
// @JsonIgnore aussi, les function equals évitent les doublons et hashCode
// Le problème de serialisation est survenu lors de l'implémentation des clés primaires/étrangères
@Entity
@Table(name = "recipes")
data class RecipeEntity(
    @Id
    // Génère automatiquement l'id avec une stratégie UUID
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,
    var title: String? = null,
    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var images: Set<ImagesEntity> = HashSet(),
    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var ingredients: Set<IngredientsEntity> = HashSet(),
    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var steps: Set<StepsEntity> = HashSet()
) {
    override fun equals(other: Any?): Boolean {
        return other is StepsEntity && id != null && id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0
}
// L'implémentation de equals et hashCode dans les entités JPA assure que les objets sont comparés de manière cohérente
// et permet un fonctionnement correct des collections et des mécanismes internes de JPA.
// Cela évite également les bugs subtils liés à la comparaison d'objets et à la gestion des collections.

@Entity
@Table(name = "images")
data class ImagesEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,
    var url: String? = null,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id")
    @JsonIgnore
    var recipe: RecipeEntity? = null
) {
    override fun equals(other: Any?): Boolean {
        return other is StepsEntity && id != null && id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0
}


@Entity
@Table(name = "ingredients")
data class IngredientsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,
    var ingredient: String? = null,
    var quantity: String? = null,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id")
    @JsonIgnore
    var recipe: RecipeEntity? = null
){
    override fun equals(other: Any?): Boolean {
        return other is StepsEntity && id != null && id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0
}

@Entity
@Table(name = "steps")
data class StepsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,
    var description: String? = null,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id")
    @JsonIgnore
    var recipe: RecipeEntity? = null
){
    override fun equals(other: Any?): Boolean {
        return other is StepsEntity && id != null && id == other.id
    }
    override fun hashCode(): Int = id?.hashCode() ?: 0
}

// Repository pour accéder aux opérations de base de données des recettes.
@Repository interface RecipeEntityRepository : JpaRepository<RecipeEntity, String>


// Service gérant la logique métier de la création de recettes.
@Service
class RecipeService( val recipeRepository: RecipeEntityRepository) {


    @Transactional
    fun saveRecipe(recipe: RecipeEntity): RecipeEntity {
        val newRecipe = RecipeEntity()
        newRecipe.title = recipe.title

        // Relier chaque image à la nouvelle recette
        newRecipe.images = recipe.images.map { image ->
            ImagesEntity(
                url = image.url,
                recipe = newRecipe
            )
        }.toSet()

        // Relier chaque ingrédient à la nouvelle recette
        newRecipe.ingredients = recipe.ingredients.map { ingredient ->
            IngredientsEntity(
                ingredient = ingredient.ingredient,
                quantity = ingredient.quantity,
                recipe = newRecipe
            )
        }.toSet()

        // Relier chaque étape à la nouvelle recette
        newRecipe.steps = recipe.steps.map { step ->
            StepsEntity(
                description = step.description,
                recipe = newRecipe
            )
        }.toSet()

        // Sauvegarder la nouvelle recette et ses dépendances en cascade
        return recipeRepository.save(newRecipe)
    }


    // Permet de vérifier qu'une id (donc une recette) existe
    fun existsById(id: String): Boolean = recipeRepository.existsById(id)

    // Récupère toutes les recettes disponibles dans la base de données.
    fun findAllRecipes(): MutableList<RecipeEntity> = recipeRepository.findAll()


    // Supprime la recette via son id, renvoie un booléen indiquant si la suppression a été effectuée.
    fun deleteRecipeById(id: String) {
        // Vérifie si la recette existe avant de tenter de la supprimer
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id)
        } else {
            throw Exception("Recette non trouvée avec l'ID: $id")
        }
    }

    // Méthode mise à jour de la recette via son id
    @Transactional
    fun updateRecipe(id: String, updatedRecipe: RecipeEntity) {
        val recipeToUpdate = recipeRepository.findById(id).orElseThrow {
            Exception("Aucune recette trouvée avec l'ID spécifié.")
        }

        // Mise à jour des champs non nulls uniquement
        updatedRecipe.title?.let { recipeToUpdate.title = it }

        // Mise à jour des images
        val updatedImages = updatedRecipe.images.map { image ->
            ImagesEntity(
                url = image.url,
                recipe = recipeToUpdate
            )
        }.toMutableSet()
        recipeToUpdate.images = updatedImages

        // Mise à jour des ingrédients
        val updatedIngredients = updatedRecipe.ingredients.map { ingredient ->
            IngredientsEntity(
                ingredient = ingredient.ingredient,
                quantity = ingredient.quantity,
                recipe = recipeToUpdate
            )
        }.toMutableSet()
        recipeToUpdate.ingredients = updatedIngredients

        // Mise à jour des étapes
        val updatedSteps = updatedRecipe.steps.map { step ->
            StepsEntity(
                description = step.description,
                recipe = recipeToUpdate
            )
        }.toMutableSet()
        recipeToUpdate.steps = updatedSteps

        // Sauvegarde des modifications
        recipeRepository.save(recipeToUpdate)
    }
}


