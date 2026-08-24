package de.dafuqs.spectral_decorations;

import de.dafuqs.spectrum.*;
import net.minecraft.core.registries.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;

public class SpectralDecorationsItemTags {
	
	public static final TagKey<Item> BEDROCK_ARMOR = spectrum("bedrock_armor");
	public static final TagKey<Item> PIGMENTS = spectrum("pigments");
	
	public static final TagKey<Item> BOTTOMLESS_BUNDLES = spectralDecorations("bottomless_bundles");
	
	private static TagKey<Item> spectralDecorations(String name) {
		return TagKey.create(Registries.ITEM, SpectralDecorations.locate(name));
	}
	
	private static TagKey<Item> spectrum(String name) {
		return TagKey.create(Registries.ITEM, SpectrumCommon.locate(name));
	}

}
