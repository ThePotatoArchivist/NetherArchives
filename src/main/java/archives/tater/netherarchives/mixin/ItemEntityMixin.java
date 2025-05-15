package archives.tater.netherarchives.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    @Shadow public abstract ItemStack getStack();

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "damage",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;onItemEntityDestroyed(Lnet/minecraft/entity/ItemEntity;)V")
    )
    private void explodeBeacon(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!source.isIn(DamageTypeTags.IS_EXPLOSION) || !getStack().isOf(Items.BEACON)) return;
        getWorld().spawnEntity(new ItemEntity(getWorld(), getX(), getY(), getZ(), Items.NETHER_STAR.getDefaultStack()));
    }
}
