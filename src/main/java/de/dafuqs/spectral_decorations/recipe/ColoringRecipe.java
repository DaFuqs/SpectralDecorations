package de.dafuqs.spectral_decorations.recipe;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.blocks.pedestal.*;
import de.dafuqs.spectrum.items.*;
import de.dafuqs.spectrum.recipe.pedestal.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import java.util.*;

public abstract class ColoringRecipe extends ShapelessPedestalRecipe {
	
	public ColoringRecipe(Ingredient colorable, Optional<ResourceLocation> unlockIdentifier) {
		super("", false, unlockIdentifier, PedestalRecipeTier.BASIC,
				List.of(IngredientStack.of(colorable)),
				Map.of(),
				ItemStack.EMPTY,
				0F, 120, false, false);
	}
	
	public abstract boolean testColorable(ItemStack stack);
	
	@Override
	public ItemStack assemble(PedestalRecipeInput inv, HolderLookup.Provider drm) {
		ItemStack colorable = null;
		PigmentItem pigment = null;
		
		for (int i = 0; i < inv.size(); ++i) {
			ItemStack stack = inv.getItem(i);
			if (testColorable(stack)) {
				colorable = stack;
			}
			if (stack.getItem() instanceof PigmentItem pigmentItem) {
				pigment = pigmentItem;
			}
		}
		
		if (colorable == null || pigment == null) {
			return ItemStack.EMPTY;
		}
		
		Optional<DyeColor> dyeColor = pigment.getInkColor().getDyeColor();
		if (dyeColor.isEmpty()) {
			return colorable;
		}
		
		return SpectralDecorationsColorizer.setColor(colorable.copy(), dyeColor.get());
	}
	
}
