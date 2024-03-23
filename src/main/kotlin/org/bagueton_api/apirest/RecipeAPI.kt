package org.bagueton_api.apirest

import org.bagueton_api.model.RecipeBean
import org.bagueton_api.model.RecipeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bagueton")
class RecipeAPI (val recipeService: RecipeService){

    @PostMapping("/createrecipe")
    fun createRecipe(@RequestBody recipe: RecipeBean){

        recipeService.createRecipe(title = recipe.title, image = recipe.image, ingredients = recipe.ingredients, steps = recipe.steps)

    }

    @GetMapping("/readrecipes")
    fun readAllRecipes(): List<RecipeBean> {
        return recipeService.findAllRecipes()
    }

}