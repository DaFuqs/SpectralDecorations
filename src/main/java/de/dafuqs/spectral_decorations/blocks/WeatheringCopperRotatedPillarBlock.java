package de.dafuqs.spectral_decorations.blocks;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

public class WeatheringCopperRotatedPillarBlock extends RotatedPillarBlock implements WeatheringCopper {
	
	public static final MapCodec<WeatheringCopperRotatedPillarBlock> CODEC = RecordCodecBuilder.mapCodec((b) -> b.group(
			WeatherState.CODEC.fieldOf("weathering_state").forGetter(ChangeOverTimeBlock::getAge), propertiesCodec()
	).apply(b, WeatheringCopperRotatedPillarBlock::new));
	private final WeatheringCopper.WeatherState weatherState;
	
	public MapCodec<WeatheringCopperRotatedPillarBlock> codec() {
		return CODEC;
	}
	
	public WeatheringCopperRotatedPillarBlock(WeatheringCopper.WeatherState weatherState, BlockBehaviour.Properties properties) {
		super(properties);
		this.weatherState = weatherState;
	}
	
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		this.changeOverTime(state, level, pos, random);
	}
	
	protected boolean isRandomlyTicking(BlockState state) {
		return WeatheringCopper.getNext(state.getBlock()).isPresent();
	}
	
	public WeatheringCopper.WeatherState getAge() {
		return this.weatherState;
	}
}
