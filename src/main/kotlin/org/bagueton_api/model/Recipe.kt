package org.bagueton_api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service


// Fetch Lazy passé en EAGER pour tenter de régler le probleme de serialisation
// @JsonIgnore permet de régler le probleme de serialisation

@Entity
@Table(name = "recipes")
data class RecipeEntity(
    @Id
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
    var quantity: Int? = null,
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
@Repository interface ImagesEntityRepository : JpaRepository<RecipeEntity, String>
@Repository interface IngredientsEntityRepository : JpaRepository<RecipeEntity, String>
@Repository interface StepsEntityRepository : JpaRepository<RecipeEntity, String>



// Service gérant la logique métier de la création de recettes.
@Service
class RecipeService( val recipeRepository: RecipeEntityRepository,
                     val ingredientsRepository: IngredientsEntityRepository,
                     val stepsRepository: StepsEntityRepository,
                     val imagesRepository: ImagesEntityRepository) {



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
}

//    // Met à jour la recette via son id
//    fun updateRecipe(id: Long, updatedRecipe: RecipeEntity) {
//        val recipeToUpdateOptional = recipeRepository.findById(id)
//        if (recipeToUpdateOptional.isEmpty) {
//            throw Exception("Aucune recette trouvée avec l'ID spécifié.") // Consider using a custom exception
//        }
//
//        val recipeToUpdate = recipeToUpdateOptional.get()
//
//        // Mise à jour des champs non nulls uniquement
//        updatedRecipe.title?.let { recipeToUpdate.title = it }
//
//        recipeRepository.save(recipeToUpdate) // Sauvegarde des modifications
//    }
//}



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

