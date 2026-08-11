package de.dafuqs.spectral_decorations;

import net.minecraft.core.component.*;
import net.minecraft.world.item.*;

import java.util.*;

public class SpectralDecorationsColorizer {
	
	public static Optional<DyeColor> getColor(ItemStack stack) {
		DyeColor dyeColor = stack.get(DataComponents.BASE_COLOR);
		if (dyeColor == null) {
			return Optional.empty();
		}
		return Optional.of(dyeColor);
	}
	
	public static ItemStack setColor(ItemStack stack, DyeColor color) {
		stack.set(DataComponents.BASE_COLOR, color);
		return stack;
	}
	
	public static ItemStack removeColor(ItemStack stack) {
		stack.remove(DataComponents.BASE_COLOR);
		return stack;
	}
	
}
