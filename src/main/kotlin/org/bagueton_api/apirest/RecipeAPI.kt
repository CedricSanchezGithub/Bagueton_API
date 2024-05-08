package org.bagueton_api.apirest

import org.bagueton_api.model.RecipeEntity
import org.bagueton_api.model.RecipeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


// Contrôleur REST pour gérer les endpoints liés aux recettes.
@RestController
@RequestMapping("/bagueton")

class RecipeAPI (val recipeService: RecipeService) {

    // Endpoint pour créer une nouvelle recette. Les données de la recette sont reçues dans le corps de la requête.

    @PostMapping("/create")
    fun createRecipe(@RequestBody recipe: RecipeEntity): ResponseEntity<RecipeEntity> {
        return ResponseEntity.ok(recipeService.saveRecipe(recipe))
    }

    // Endpoint pour récupérer toutes les recettes disponibles.
    @GetMapping("/readrecipes")
    fun readAllRecipes(): List<RecipeEntity> {
        println("Appel de la fonction readAllRecipe")
        return recipeService.findAllRecipes()
    }
}
//
//    // Endpoint pour supprimer une recette précise
//
//    @DeleteMapping("/delete/{id}")
//    fun deleteRecipe(@PathVariable id: Long): ResponseEntity<String> {
//        return try {
//            recipeService.deleteRecipeById(id)
//            ResponseEntity.ok("Recette supprimée avec succès avec l'ID: $id")
//        } catch (e: Exception) {
//            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message ?: "Erreur lors de la suppression de la recette")
//        }
//    }
//
//    // Endpoint pour modifier une recette. Les nouvelles données de la recette sont reçues dans le corps de la requête.
//    @PatchMapping("/updaterecipe/{id}")
//    fun updateRecipe(@PathVariable id: Long, @RequestBody recipe: RecipeEntity) : ResponseEntity<Any> {
//        if (!recipeService.existsById(id)) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune recette trouvée pour l'ID spécifié.")
//        }
//        // Appel du service pour mettre à jour partiellement ou completement la recette
//        recipeService.updateRecipe(id, recipe)
//        return ResponseEntity.ok("Recette '${recipe.title}' mise à jour avec succès.")
//    }
//
//}
//
//

