package de.dafuqs.spectral_decorations;

import com.mojang.datafixers.util.*;
import de.dafuqs.fractal.api.*;
import de.dafuqs.spectrum.api.interaction.*;
import de.dafuqs.spectrum.api.item_group.*;
import de.dafuqs.spectrum.entity.*;
import de.dafuqs.spectrum.entity.variants.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.event.*;
import net.neoforged.neoforge.event.entity.player.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

@Mod(SpectralDecorations.MOD_ID)
@EventBusSubscriber(modid = SpectralDecorations.MOD_ID)
public class SpectralDecorations {
	
	public static final String MOD_ID = "spectral_decorations";
	public static final List<Pair<ResourceLocation, Supplier<? extends ItemLike>>> ITEM_SUB_TAB_HOLDER = new ArrayList<>();
	
	public SpectralDecorations(ModContainer container, IEventBus modBus) {
		SpectralDecorationsBlocks.register(modBus);
		SpectralDecorationsItems.register(modBus);
		SpectralDecorationsRecipeSerializers.register(modBus);
		
		EntityColorProcessorRegistry.register(SpectrumEntityTypes.KINDLING, (entity, dyeColor, player) -> {
			if (dyeColor.isEmpty()) {
				return false;
			}
			
			@Nullable Holder<KindlingVariant> coloredVariant = SpectralDecorationsKindlingVariants.getColoredVariant(entity.level(), dyeColor.get());
			if (coloredVariant == null) {
				return false;
			}
			if (entity.getKindlingVariant() == coloredVariant) {
				return false;
			}
			entity.setKindlingVariant(coloredVariant);
			return true;
		});
	}
	
	@SubscribeEvent
	public static void addBlockEntityTypeBlocks(BlockEntityTypeAddBlocksEvent event) {
		for (SpectralDecorationsBlocks.PropertyHolder entry : SpectralDecorationsBlocks.BLOCK_TYPE_HOLDER) {
			if (entry.type() == SpectralDecorationsBlocks.Type.AMPHORA) {
				event.modify(SpectrumBlockEntities.AMPHORA.get(), entry.block().get());
			}
		}
	}
	
	@SubscribeEvent
	public static void addItemsToSubTabs(CreativeSubTabEvent event) {
		ResourceLocation subGroupId = event.subGroup().getIdentifier();
		
		if (subGroupId.equals(ItemGroupIDs.SUBTAB_COLORED_WOOD) || subGroupId.equals(ItemGroupIDs.SUBTAB_DECORATION) || subGroupId.equals(ItemGroupIDs.SUBTAB_RESOURCES)) {
			for (Pair<ResourceLocation, Supplier<? extends ItemLike>> entry : ITEM_SUB_TAB_HOLDER) {
				if (subGroupId.equals(entry.getFirst())) {
					event.getItemDisplayBuilder().accept(entry.getSecond().get().asItem().getDefaultInstance());
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void itemTooltipEvent(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		
		if (stack.is(SpectralDecorationsItemTags.BEDROCK_ARMOR) || stack.is(SpectrumBlocks.BOTTOMLESS_BUNDLE.asItem())) {
			Optional<DyeColor> optionalColor = SpectralDecorationsColorizer.getColor(stack);
			if (optionalColor.isPresent()) {
				DyeColor c = optionalColor.get();
				Component t = Component.translatable("tooltip.spectral_decorations.colored").append(Component.translatable("color.minecraft." + c.getName()).withStyle((style -> style.withColor(c.getTextColor()))));
				event.getToolTip().add(1, t);
			}
		}
	}
	
	public static ResourceLocation locate(String name) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
	}
	
}
