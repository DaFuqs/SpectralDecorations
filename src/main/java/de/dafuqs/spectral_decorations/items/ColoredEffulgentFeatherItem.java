package de.dafuqs.spectral_decorations.items;

import com.google.common.collect.*;
import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.items.conditional.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import java.util.*;

public class ColoredEffulgentFeatherItem extends CloakedItem {

	public static final Identifier CLOAK_ADVANCEMENT = SpectralDecorations.locate("lategame/pluck_colored_effulgent_feather");

	private static final Map<DyeColor, ColoredEffulgentFeatherItem> FEATHERS = Maps.newEnumMap(DyeColor.class);
	protected final DyeColor color;

	public ColoredEffulgentFeatherItem(Settings settings, DyeColor color) {
		super(settings, CLOAK_ADVANCEMENT, DyeItem.byColor(color));
		this.color = color;
		FEATHERS.put(color, this);
	}

	public DyeColor getColor() {
		return this.color;
	}

	public static ColoredEffulgentFeatherItem byColor(DyeColor color) {
		return FEATHERS.get(color);
	}

}
