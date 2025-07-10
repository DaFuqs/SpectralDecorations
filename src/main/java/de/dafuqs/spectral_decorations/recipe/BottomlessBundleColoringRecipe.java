package de.dafuqs.spectral_decorations.recipe;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.blocks.bottomless_bundle.*;
import de.dafuqs.spectrum.blocks.pedestal.*;
import de.dafuqs.spectrum.items.*;
import de.dafuqs.spectrum.recipe.pedestal.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import java.util.*;

public class BottomlessBundleColoringRecipe extends ShapelessPedestalRecipe {
	
	public static final ResourceLocation UNLOCK_IDENTIFIER = SpectrumCommon.locate("unlocks/items/bottomless_bundle");
	
	public BottomlessBundleColoringRecipe() {
		super("", false, Optional.of(UNLOCK_IDENTIFIER), PedestalRecipeTier.BASIC, List.of(
						IngredientStack.of(Ingredient.of(SpectrumBlocks.BOTTOMLESS_BUNDLE)),
						IngredientStack.of(Ingredient.of(SpectralDecorationsItemTags.PIGMENTS))),
				Map.of(),
				BedrockArmorColorizer.setColor(SpectrumBlocks.BOTTOMLESS_BUNDLE.asItem().getDefaultInstance(), DyeColor.CYAN),
				0F, 120, false, false);
	}
	
	@Override
	public ItemStack assemble(PedestalRecipeInput inv, HolderLookup.Provider drm) {
		ItemStack bundleStack = null;
		PigmentItem pigment = null;
		
		for (int i = 0; i < inv.size(); ++i) {
			ItemStack stack = inv.getItem(i);
			if (stack.getItem() instanceof BottomlessBundleItem) {
				bundleStack = stack;
			}
			if (stack.getItem() instanceof PigmentItem pigmentItem) {
				pigment = pigmentItem;
			}
		}
		
		if (bundleStack == null || pigment == null) {
			return ItemStack.EMPTY;
		}
		
		Optional<DyeColor> dyeColor = pigment.getInkColor().getDyeColor();
		if (dyeColor.isEmpty()) {
			return bundleStack;
		}
		
		return BedrockArmorColorizer.setColor(bundleStack.copy(), dyeColor.get());
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectralDecorationsRecipeTypes.BOTTOMLESS_BUNDLE_COLORING_SERIALIZER;
	}
	
}
