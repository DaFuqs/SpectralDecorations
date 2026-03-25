package de.dafuqs.spectral_decorations;

import de.dafuqs.spectral_decorations.recipe.*;
import de.dafuqs.spectrum.recipe.*;
import net.minecraft.core.registries.*;
import net.minecraft.world.item.crafting.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class SpectralDecorationsRecipeSerializers {
	
	private static final DeferredRegister<RecipeSerializer<?>> REGISTRAR = DeferredRegister.create(Registries.RECIPE_SERIALIZER, SpectralDecorations.MOD_ID);
	
	public static final RecipeSerializer<?> BEDROCK_ARMOR_COLORING_SERIALIZER = register("bedrock_armor_coloring", new EmptyRecipeSerializer<>(BedrockArmorColoringRecipe::new));
	public static final RecipeSerializer<?> BOTTOMLESS_BUNDLE_COLORING_SERIALIZER = register("bottomless_bundle_coloring", new EmptyRecipeSerializer<>(BottomlessBundleColoringRecipe::new));
	
	static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
		REGISTRAR.register(id, () -> serializer);
		return serializer;
	}
	
	public static void register(IEventBus eventBus) {
		REGISTRAR.register(eventBus);
	}
	
}
