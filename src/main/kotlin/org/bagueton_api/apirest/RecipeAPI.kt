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

    // Endpoint pour créer une nouvelle recette.
    // Les données de la recette sont reçues dans le corps de la requête.

    @PostMapping("/createrecipe")
    fun createRecipe(@RequestBody recipe: RecipeEntity): ResponseEntity<RecipeEntity> {
        return ResponseEntity.ok(recipeService.saveRecipe(recipe))
    }

    // Endpoint pour récupérer toutes les recettes disponibles.
    @GetMapping("/readrecipes")
    fun readAllRecipes(): List<RecipeEntity> {
        println("Appel de la fonction readAllRecipe")
        return recipeService.findAllRecipes()
    }

    // Endpoint pour modifier une recette. Les nouvelles données
    // de la recette sont reçues dans le corps de la requête.
    @PatchMapping("/updaterecipe/{id}")
    fun updateRecipe(@PathVariable id: String,
                     @RequestBody recipe: RecipeEntity): ResponseEntity<Any> {
        return try {
            // Vérifie si la recette existe
            if (!recipeService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aucune recette trouvée pour l'ID spécifié.")
            }
            // Appel du service pour mettre à jour partiellement ou complètement la recette
            val updatedRecipe = recipeService.updateRecipe(id, recipe)
            ResponseEntity.ok(updatedRecipe)
        } catch (e: Exception) {
            // Gestion des exceptions
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur lors de la mise à jour de la recette : ${e.message}")
        }
    }
    // Endpoint pour supprimer une recette précise
    @DeleteMapping("/deleterecipe/{id}")
    fun deleteRecipe(@PathVariable id: String): ResponseEntity<String> {
        return try {
            recipeService.deleteRecipeById(id)
            ResponseEntity.ok("Recette supprimée avec succès avec l'ID: $id")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message ?:
            "Erreur lors de la suppression de la recette")
        }
    }
}



