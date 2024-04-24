package de.dafuqs.spectral_decorations;

import de.dafuqs.spectral_decorations.recipe.*;
import net.minecraft.recipe.*;
import net.minecraft.util.registry.*;

public class SpectralDecorationsRecipeTypes {
	
	private static void register(RecipeSerializer<?> recipeSerializer, String id) {
		Registry.register(Registry.RECIPE_SERIALIZER, SpectralDecorations.locate(id), recipeSerializer);
	}
	
	public static void registerRecipeSerializers() {
		register(BedrockColoringRecipe.SERIALIZER, "bedrock_armor_coloring");
	}

}
