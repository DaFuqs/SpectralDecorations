package de.dafuqs.spectral_decorations.recipe;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.blocks.bottomless_bundle.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class BottomlessBundleColoringRecipe extends ColoringRecipe {
	
	public static final ResourceLocation UNLOCK_IDENTIFIER = SpectrumCommon.locate("unlocks/items/bottomless_bundle");
	
	public BottomlessBundleColoringRecipe() {
		super(Ingredient.of(SpectralDecorationsItemTags.BOTTOMLESS_BUNDLES), Optional.of(UNLOCK_IDENTIFIER));
	}
	
	@Override
	public boolean testColorable(ItemStack stack) {
		return stack.getItem() instanceof BottomlessBundleItem;
	}
	
	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return SpectralDecorationsRecipeSerializers.BOTTOMLESS_BUNDLE_COLORING_SERIALIZER;
	}
	
}
