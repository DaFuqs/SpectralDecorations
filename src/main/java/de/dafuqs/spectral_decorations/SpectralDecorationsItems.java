package de.dafuqs.spectral_decorations;

import de.dafuqs.spectral_decorations.items.*;
import de.dafuqs.spectrum.api.item_group.*;
import de.dafuqs.spectrum.helpers.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

import java.util.function.*;

public class SpectralDecorationsItems {
	
	public static final DeferredRegister.Items REGISTRAR = DeferredRegister.createItems(SpectralDecorations.MOD_ID);
	
	public static void register(IEventBus modBus) {
		REGISTRAR.register(modBus);
		
		for (DyeColor color : SpectrumColorHelper.VANILLA_DYE_COLORS) {
			String colorString = color.getSerializedName();
			registerItem(ItemGroupIDs.SUBTAB_RESOURCES, colorString + "_resplendent_feather", () -> new ColoredResplendentFeatherItem(new Item.Properties().rarity(Rarity.UNCOMMON), color));
		}
	}
	
	public static <I extends Item> DeferredItem<I> registerItem(ResourceLocation subTabId, String name, Supplier<I> entry) {
		DeferredItem<I> i = REGISTRAR.register(name, entry);
		SpectralDecorations.ITEM_SUB_TAB_HOLDER.put(subTabId, i);
		return i;
	}
	
}