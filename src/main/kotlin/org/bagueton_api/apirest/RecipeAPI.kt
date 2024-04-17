package org.bagueton_api.apirest

import org.bagueton_api.model.RecipeBean
import org.bagueton_api.model.RecipeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// Contrôleur REST pour gérer les endpoints liés aux recettes.
@RestController
@RequestMapping("/bagueton")

class RecipeAPI (val recipeService: RecipeService) {

    // Endpoint pour créer une nouvelle recette. Les données de la recette sont reçues dans le corps de la requête.
    @PostMapping("/createrecipe")
    fun createRecipe(@RequestBody recipe: RecipeBean) : ResponseEntity<Any> {
        println("Creation de la recette ${recipe.title}")
        recipe.title?.let { recipeService.createRecipe(name = it, steps = recipe.steps, ingredients = recipe.ingredients) }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La nouvelle recette '${recipe.title}' a été ajoutée avec succès!")
    }

    // Endpoint pour récupérer toutes les recettes disponibles.
    @GetMapping("/readrecipes")
    fun readAllRecipes(): List<RecipeBean> {
        println("Appel de la fonction readAllRecipe")
        return recipeService.findAllRecipes()
    }

    // Endpoint pour supprimer une recette précise
    @DeleteMapping("/deleterecipe/{id}")
    fun deleteMatch(@PathVariable id: Long) : ResponseEntity<Any>  {
        if (!recipeService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune recette trouvée pour l'ID spécifié.")
        }
        recipeService.deleteRecipe(id)
        return ResponseEntity.ok("Recette $id supprimée avec succès.")

    }

    // Endpoint pour modifier une recette. Les nouvelles données de la recette sont reçues dans le corps de la requête.
    @PatchMapping("/updaterecipe/{id}")
    fun updateRecipe(@PathVariable id: Long, @RequestBody recipe: RecipeBean) : ResponseEntity<Any> {
        if (!recipeService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune recette trouvée pour l'ID spécifié.")
        }
        // Appel du service pour mettre à jour partiellement ou completement la recette
        recipeService.updateRecipe(id, recipe)
        return ResponseEntity.ok("Recette '${recipe.title}' mise à jour avec succès.")
    }

}


