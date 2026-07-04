package de.dafuqs.spectral_decorations;

import com.mojang.datafixers.util.*;
import de.dafuqs.spectrum.api.item_group.*;
import de.dafuqs.spectrum.blocks.amphora.*;
import de.dafuqs.spectrum.blocks.decoration.*;
import de.dafuqs.spectrum.blocks.flammable.*;
import de.dafuqs.spectrum.helpers.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.material.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

import java.util.*;
import java.util.function.*;

public class SpectralDecorationsBlocks {
	
	public static final DeferredRegister.Blocks REGISTRAR = DeferredRegister.createBlocks(SpectralDecorations.MOD_ID);
	public static final List<PropertyHolder> BLOCK_TYPE_HOLDER = new ArrayList<>();
	
	public static void register(IEventBus modBus) {
		REGISTRAR.register(modBus);
		
		for (VanillaWood wood : VanillaWood.values()) {
			String name = wood.getName();
			MapColor mapColor = wood.getMapColor();
			boolean isFireResistant = wood.isFireResistant();
			SoundType blockSoundGroup = wood.getBlockSoundGroup();
			
			registerBlockWithItem(ItemGroupIDs.SUBTAB_DECORATION, name + "_amphora", () -> new AmphoraBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).mapColor(mapColor).sound(blockSoundGroup)), new Item.Properties(), Type.AMPHORA);
			registerBlockWithItem(ItemGroupIDs.SUBTAB_DECORATION, name + "_beam",
					isFireResistant
							? () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).mapColor(mapColor).sound(blockSoundGroup))
							: () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).mapColor(mapColor).sound(blockSoundGroup)
					), new Item.Properties(), Type.BEAM);
		}
		
		for (DyeColor color : SpectrumColorHelper.VANILLA_DYE_COLORS) {
			String colorString = color.getSerializedName();
			
			registerBlockWithItem(ItemGroupIDs.SUBTAB_COLORED_WOOD, colorString + "_beam", () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.of().strength(4.0F).mapColor(color).sound(SoundType.WOOD)), new Item.Properties(), Type.BEAM);
			registerBlockWithItem(ItemGroupIDs.SUBTAB_COLORED_WOOD, colorString + "_lantern", () -> new FlexLanternBlock(BlockBehaviour.Properties.of().strength(4.0F).mapColor(color).sound(SoundType.WOOD).lightLevel(state -> 13)), new Item.Properties(), Type.LANTERN);
			registerBlockWithItem(ItemGroupIDs.SUBTAB_COLORED_WOOD, colorString + "_light", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(4.0F).mapColor(color).sound(SoundType.WOOD).lightLevel(state -> 15)), new Item.Properties(), Type.LIGHT);
			registerBlockWithItem(ItemGroupIDs.SUBTAB_COLORED_WOOD, colorString + "_amphora", () -> new AmphoraBlock(BlockBehaviour.Properties.of().strength(4.0F).mapColor(color).sound(SoundType.WOOD)), new Item.Properties(), Type.AMPHORA);
			
			registerBlockWithItem(ItemGroupIDs.SUBTAB_DECORATION, colorString + "_resplendent_block", () -> new CushionedFacingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_WOOL).mapColor(color)), new Item.Properties().rarity(Rarity.UNCOMMON), Type.RESPLENDENT_BLOCK);
			registerBlockWithItem(ItemGroupIDs.SUBTAB_DECORATION, colorString + "_resplendent_cushion", () -> new CushionBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_WOOL).mapColor(color).noOcclusion().isValidSpawn((state, world, pos, type) -> false)), new Item.Properties().rarity(Rarity.UNCOMMON), Type.RESPLENDENT_CUSHION);
			registerBlockWithItem(ItemGroupIDs.SUBTAB_DECORATION, colorString + "_resplendent_carpet", () -> new CushionedCarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET).mapColor(color)), new Item.Properties().rarity(Rarity.UNCOMMON), Type.RESPLENDENT_CARPET);
			registerBlockWithItem(ItemGroupIDs.SUBTAB_DECORATION, colorString + "_resplendent_bed", () -> new SpectrumBedBlock(color, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_BED).mapColor(color)), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), Type.RESPLENDENT_BED);
		}
		
		registerBlockWithItem(ItemGroupIDs.SUBTAB_DECORATION, "iron_tubing", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)), new Item.Properties(), Type.TUBING);
		registerBlockWithItem(ItemGroupIDs.SUBTAB_DECORATION, "gold_tubing", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK)), new Item.Properties(), Type.TUBING);
		registerBlockWithItem(ItemGroupIDs.SUBTAB_DECORATION, "copper_tubing", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)), new Item.Properties(), Type.TUBING);
		registerBlockWithItem(ItemGroupIDs.SUBTAB_DECORATION, "exposed_copper_tubing", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.EXPOSED_COPPER)), new Item.Properties(), Type.TUBING);
		registerBlockWithItem(ItemGroupIDs.SUBTAB_DECORATION, "weathered_copper_tubing", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WEATHERED_COPPER)), new Item.Properties(), Type.TUBING);
		registerBlockWithItem(ItemGroupIDs.SUBTAB_DECORATION, "oxidized_copper_tubing", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OXIDIZED_COPPER)), new Item.Properties(), Type.TUBING);
	}
	
	public static void registerBlockWithItem(ResourceLocation subTabId, String name, Supplier<Block> block, Item.Properties itemSettings, Type type) {
		DeferredBlock<Block> deferredBlock = REGISTRAR.register(name, block);
		
		DeferredItem<BlockItem> bi = SpectralDecorationsItems.REGISTRAR.register(name, () -> new BlockItem(deferredBlock.get(), itemSettings));
		BLOCK_TYPE_HOLDER.add(new PropertyHolder(deferredBlock, type));
		SpectralDecorations.ITEM_SUB_TAB_HOLDER.add(new Pair<>(subTabId, bi));
	}
	
	public enum VanillaWood {
		OAK("oak", MapColor.WOOD, SoundType.WOOD, false),
		SPRUCE("spruce", MapColor.PODZOL, SoundType.WOOD, false),
		BIRCH("birch", MapColor.SAND, SoundType.WOOD, false),
		DARK_OAK("dark_oak", MapColor.COLOR_BROWN, SoundType.WOOD, false),
		JUNGLE("jungle", MapColor.DIRT, SoundType.WOOD, false),
		ACACIA("acacia", MapColor.COLOR_ORANGE, SoundType.WOOD, false),
		BAMBOO("bamboo", MapColor.COLOR_YELLOW, SoundType.BAMBOO_WOOD, false),
		MANGROVE("mangrove", MapColor.COLOR_RED, SoundType.WOOD, false),
		CHERRY("cherry", MapColor.TERRACOTTA_WHITE, SoundType.CHERRY_WOOD, false),
		CRIMSON("crimson", MapColor.CRIMSON_STEM, SoundType.NETHER_WOOD, true),
		WARPED("warped", MapColor.WARPED_STEM, SoundType.NETHER_WOOD, true);
		
		private final String name;
		private final MapColor mapColor;
		private final SoundType blockSoundGroup;
		private final boolean isFireResistant;
		
		VanillaWood(String name, MapColor mapColor, SoundType blockSoundGroup, boolean isFireResistant) {
			this.name = name;
			this.mapColor = mapColor;
			this.blockSoundGroup = blockSoundGroup;
			this.isFireResistant = isFireResistant;
		}
		
		public String getName() {
			return name;
		}
		
		public SoundType getBlockSoundGroup() {
			return blockSoundGroup;
		}
		
		public MapColor getMapColor() {
			return mapColor;
		}
		
		public boolean isFireResistant() {
			return isFireResistant;
		}
	}

	public enum Type {
		BEAM,
		AMPHORA,
		LANTERN,
		LIGHT,
		RESPLENDENT_BLOCK,
		RESPLENDENT_CUSHION,
		RESPLENDENT_CARPET,
		RESPLENDENT_BED,
		TUBING
	}
	
	public record PropertyHolder(DeferredBlock<?> block, Type type) {
	}
	
}