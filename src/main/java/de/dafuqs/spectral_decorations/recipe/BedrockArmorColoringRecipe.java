package de.dafuqs.spectral_decorations.recipe;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.items.armor.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import java.util.*;

public class BedrockArmorColoringRecipe extends ColoringRecipe {
	
	public static final ResourceLocation UNLOCK_IDENTIFIER = SpectrumCommon.locate("unlocks/equipment/bedrock_tools");
	
	public BedrockArmorColoringRecipe() {
		super(Ingredient.of(SpectralDecorationsItemTags.BEDROCK_ARMOR), Optional.of(UNLOCK_IDENTIFIER));
	}
	
	@Override
	public boolean testColorable(ItemStack stack) {
		return stack.getItem() instanceof BedrockArmorItem;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectralDecorationsRecipeSerializers.BEDROCK_ARMOR_COLORING_SERIALIZER;
	}
	
}
