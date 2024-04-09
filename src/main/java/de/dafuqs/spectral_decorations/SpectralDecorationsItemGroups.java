package de.dafuqs.spectral_decorations;

import net.fabricmc.fabric.api.client.itemgroup.*;
import net.minecraft.item.*;

public class SpectralDecorationsItemGroups {
	
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.create(SpectralDecorations.locate("main"))
			.icon(() -> new ItemStack(SpectralDecorationsBlocks.items.get(0).getA()))
			.build();
	
}
