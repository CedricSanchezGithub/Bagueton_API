package org.bagueton_api.apirest

import org.bagueton_api.model.RecipeBean
import org.bagueton_api.model.RecipeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// Contrôleur REST pour gérer les endpoints liés aux recettes.
@RestController
@RequestMapping("/bagueton")
class RecipeAPI (val recipeService: RecipeService) {

    // Endpoint pour créer une nouvelle recette. Les données de la recette sont reçues dans le corps de la requête.
    @PostMapping("/createrecipe")
    fun createRecipe(@RequestBody recipe: RecipeBean) {
        recipeService.createRecipe(title = recipe.title, image = recipe.image, ingredients = recipe.ingredients, steps = recipe.steps)
    }

    // Endpoint pour récupérer toutes les recettes disponibles.
    @GetMapping("/readrecipes")
    fun readAllRecipes(): List<RecipeBean> = recipeService.findAllRecipes()

}


