package de.dafuqs.spectral_decorations;

import de.dafuqs.spectral_decorations.recipe.*;
import de.dafuqs.spectrum.recipe.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.item.crafting.*;

public class SpectralDecorationsRecipeTypes {
	
	public static final RecipeSerializer<?> BEDROCK_ARMOR_COLORING_SERIALIZER = register("bedrock_armor_coloring", new EmptyRecipeSerializer<>(BedrockArmorColoringRecipe::new));
	public static final RecipeSerializer<?> BOTTOMLESS_BUNDLE_COLORING_SERIALIZER = register("bottomless_bundle_coloring", new EmptyRecipeSerializer<>(BottomlessBundleColoringRecipe::new));
	
	private static RecipeSerializer<?> register(String id, RecipeSerializer<?> recipeSerializer) {
		return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, SpectralDecorations.locate(id), recipeSerializer);
	}
	
	public static void registerRecipeSerializers() {
	
	}

}
