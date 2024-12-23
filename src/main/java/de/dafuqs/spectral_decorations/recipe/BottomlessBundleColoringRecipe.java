package de.dafuqs.spectral_decorations.recipe;

import de.dafuqs.matchbooks.recipe.*;
import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.blocks.bottomless_bundle.*;
import de.dafuqs.spectrum.items.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.recipe.pedestal.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

import java.util.*;

public class BottomlessBundleColoringRecipe extends ShapelessPedestalRecipe {
	
	public static final Identifier UNLOCK_IDENTIFIER = SpectrumCommon.locate("unlocks/items/bottomless_bundle");
	public static final RecipeSerializer<BottomlessBundleColoringRecipe> SERIALIZER = new EmptyRecipeSerializer<>(BottomlessBundleColoringRecipe::new);
	
	public BottomlessBundleColoringRecipe(Identifier id) {
		super(id, "", false, UNLOCK_IDENTIFIER, PedestalRecipeTier.BASIC, List.of(
						IngredientStack.of(Ingredient.ofItems(SpectrumItems.BOTTOMLESS_BUNDLE)),
						IngredientStack.of(Ingredient.fromTag(SpectralDecorationsItemTags.PIGMENTS))),
				Map.of(),
				BedrockArmorColorizer.setColor(SpectrumItems.BOTTOMLESS_BUNDLE.getDefaultStack(), DyeColor.CYAN),
				0F, 120, false, false);
	}
	
	@Override
	public ItemStack craft(Inventory inv, DynamicRegistryManager drm) {
		ItemStack bundleStack = null;
		PigmentItem pigment = null;
		
		
		for (int i = 0; i < inv.size(); ++i) {
			ItemStack stack = inv.getStack(i);
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
		
		return BedrockArmorColorizer.setColor(bundleStack.copy(), pigment.getColor());
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}
	
}
