package de.dafuqs.spectral_decorations.recipe;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.blocks.bottomless_bundle.*;
import de.dafuqs.spectrum.items.armor.*;
import de.dafuqs.spectrum.recipe.crafting.dynamic.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;

public class UncoloringRecipe extends SingleItemCraftingRecipe {
	
	public UncoloringRecipe() {
	}
	
	@Override
	public boolean matches(Level level, ItemStack stack) {
		Item item = stack.getItem();
		return item instanceof BedrockArmorItem || item instanceof BottomlessBundleItem;
	}
	
	@Override
	public ItemStack assemble(ItemStack stack) {
		ItemStack returnStack = stack.copy();
		returnStack.setCount(1);
		return SpectralDecorationsColorizer.removeColor(returnStack);
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectralDecorationsRecipeSerializers.UNCOLORING_SERIALIZER;
	}
	
}
