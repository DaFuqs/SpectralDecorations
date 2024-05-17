package de.dafuqs.spectral_decorations;

import de.dafuqs.spectrum.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;

public class SpectralDecorationsItemTags {
	
	public static final TagKey<Item> BEDROCK_ARMOR = spectrum("bedrock_armor");
	
	private static TagKey<Item> spectrum(String id) {
		return TagKey.of(RegistryKeys.ITEM, SpectrumCommon.locate(id));
	}

}
