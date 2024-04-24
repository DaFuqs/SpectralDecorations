package de.dafuqs.spectral_decorations.recipe;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.items.*;
import de.dafuqs.spectrum.items.armor.*;
import de.dafuqs.spectrum.recipe.pedestal.*;
import de.dafuqs.spectrum.registries.*;
import net.id.incubus_core.recipe.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.util.*;

import java.util.*;

public class BedrockColoringRecipe extends ShapelessPedestalRecipe {
	
	public static final Identifier UNLOCK_IDENTIFIER = SpectrumCommon.locate("unlocks/food/star_candy");
	public static final RecipeSerializer<BedrockColoringRecipe> SERIALIZER = new SpecialRecipeSerializer<>(BedrockColoringRecipe::new);
	
	public BedrockColoringRecipe(Identifier id) {
		super(id, "", false, UNLOCK_IDENTIFIER, PedestalRecipeTier.BASIC, List.of(
						IngredientStack.of(Ingredient.fromTag(SpectralDecorationsItemTags.BEDROCK_ARMOR)),
						IngredientStack.of(Ingredient.fromTag(SpectrumItemTags.PIGMENTS))),
				Map.of(),
				BedrockArmorColorizer.setColor(SpectrumItems.BEDROCK_CHESTPLATE.getDefaultStack(), DyeColor.CYAN),
				0F, 120, false, false);
	}
	
	@Override
	public ItemStack craft(Inventory inv) {
		ItemStack armorStack = null;
		PigmentItem pigment = null;
		
		
		for (int i = 0; i < inv.size(); ++i) {
			ItemStack stack = inv.getStack(i);
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
		
		return BedrockArmorColorizer.setColor(armorStack.copy(), pigment.getColor());
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}
	
}
