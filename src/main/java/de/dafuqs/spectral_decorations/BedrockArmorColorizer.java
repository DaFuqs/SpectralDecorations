package de.dafuqs.spectral_decorations;

import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

import java.util.*;

public class BedrockArmorColorizer {
	
	public static Optional<DyeColor> getColor(ItemStack stack) {
		NbtCompound nbt = stack.getNbt();
		if (nbt == null || !nbt.contains("Color", NbtElement.STRING_TYPE)) {
			return Optional.empty();
		}
		
		String colorString = nbt.getString("Color");
		return Optional.of(DyeColor.byName(colorString, DyeColor.RED));
	}
	
	public static ItemStack setColor(ItemStack stack, DyeColor color) {
		NbtCompound nbt = stack.getOrCreateNbt();
		nbt.putString("Color", color.asString());
		return stack;
	}
	
}
