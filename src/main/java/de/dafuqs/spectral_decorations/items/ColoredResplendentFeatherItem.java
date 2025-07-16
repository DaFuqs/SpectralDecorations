package de.dafuqs.spectral_decorations.items;

import com.google.common.collect.*;
import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.items.conditional.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;

import java.util.*;

public class ColoredResplendentFeatherItem extends CloakedItem {
	
	public static final ResourceLocation CLOAK_ADVANCEMENT = SpectralDecorations.locate("pluck_colored_resplendent_feather");
	
	private static final Map<DyeColor, ColoredResplendentFeatherItem> FEATHERS = Maps.newEnumMap(DyeColor.class);
	protected final DyeColor color;
	
	public ColoredResplendentFeatherItem(Properties settings, DyeColor color) {
		super(settings, CLOAK_ADVANCEMENT, DyeItem.byColor(color));
		this.color = color;
		FEATHERS.put(color, this);
	}

	public DyeColor getColor() {
		return this.color;
	}
	
	public static ColoredResplendentFeatherItem byColor(DyeColor color) {
		return FEATHERS.get(color);
	}

}
