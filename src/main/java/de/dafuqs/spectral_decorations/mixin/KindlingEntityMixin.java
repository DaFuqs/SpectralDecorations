package de.dafuqs.spectral_decorations.mixin;

import de.dafuqs.spectral_decorations.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.entity.variants.*;
import net.minecraft.item.*;
import net.minecraft.loot.*;
import net.minecraft.loot.context.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

@Mixin(KindlingEntity.class)
public abstract class KindlingEntityMixin {

	@Inject(at = @At("HEAD"), method = "getClippedStacks(Lnet/minecraft/server/world/ServerWorld;)Ljava/util/List;", cancellable = true)
	private void spectral_decorations$modifyKindlingClippingLoot(ServerWorld world, CallbackInfoReturnable<List<ItemStack>> cir) {
		KindlingEntity kindling = (KindlingEntity) (Object) this;
		KindlingVariant variant = kindling.getKindlingVariant();
		Optional<DyeColor> variantColor = SpectralDecorationsKindlingVariants.getColor(variant);

		if (variantColor.isPresent()) {
			Identifier lootTableIdentifier = SpectralDecorations.locate("gameplay/kindling_clipping/" + variantColor.get().asString());
			LootTable lootTable = world.getServer().getLootManager().getLootTable(lootTableIdentifier);
			cir.setReturnValue(lootTable.generateLoot((new LootContextParameterSet.Builder(world)).add(LootContextParameters.THIS_ENTITY, kindling).build(LootContextTypes.BARTER)));
		}
	}

}