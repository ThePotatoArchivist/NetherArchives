package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.item.BurnedBlockItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.stream.Stream;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {
	@Inject(at = @At("HEAD"), method = "onItemEntityDestroyed")
	private void lodestoneDrop(ItemEntity entity, CallbackInfo ci) {
		if (((BlockItem) (Object) this).getBlock() == Blocks.LODESTONE && entity.isOnFire()) {
			ItemUsage.spawnItemContents(entity, Stream.of(new ItemStack(Items.NETHERITE_INGOT)));
		}
	}
}