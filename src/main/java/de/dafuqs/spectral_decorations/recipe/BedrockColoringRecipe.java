package de.dafuqs.spectral_decorations.recipe;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.blocks.pedestal.*;
import de.dafuqs.spectrum.items.*;
import de.dafuqs.spectrum.items.armor.*;
import de.dafuqs.spectrum.recipe.pedestal.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import java.util.*;

public class BedrockColoringRecipe extends ShapelessPedestalRecipe {
	
	public static final ResourceLocation UNLOCK_IDENTIFIER = SpectrumCommon.locate("unlocks/equipment/bedrock_tools");
	
	public BedrockColoringRecipe() {
		super("", false, Optional.of(UNLOCK_IDENTIFIER), PedestalRecipeTier.BASIC, List.of(
						IngredientStack.of(Ingredient.of(SpectralDecorationsItemTags.BEDROCK_ARMOR)),
						IngredientStack.of(Ingredient.of(SpectralDecorationsItemTags.PIGMENTS))),
				Map.of(),
				BedrockArmorColorizer.setColor(SpectrumItems.BEDROCK_CHESTPLATE.getDefaultInstance(), DyeColor.CYAN),
				0F, 120, false, false);
	}
	
	@Override
	public ItemStack assemble(PedestalRecipeInput inv, HolderLookup.Provider drm) {
		ItemStack armorStack = null;
		PigmentItem pigment = null;
		
		for (int i = 0; i < inv.size(); ++i) {
			ItemStack stack = inv.getItem(i);
			if (stack.getItem() instanceof BedrockArmorItem) {
				armorStack = stack;
			}
			if (stack.getItem() instanceof PigmentItem pigmentItem) {
				pigment = pigmentItem;
			}
		}
		
		if (armorStack == null || pigment == null) {
			return ItemStack.EMPTY;
		}
		
		Optional<DyeColor> dyeColor = pigment.getInkColor().getDyeColor();
		if (dyeColor.isEmpty()) {
			return armorStack;
		}
		
		return BedrockArmorColorizer.setColor(armorStack.copy(), dyeColor.get());
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectralDecorationsRecipeTypes.BEDROCK_ARMOR_COLORING_SERIALIZER;
	}
	
}
