package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.NetherArchives;
import archives.tater.netherarchives.block.NetherArchivesBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Items.class)
public class ItemsMixin {
    @Inject(at = @At("HEAD"), method = "register(Ljava/lang/String;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;", cancellable = true)
    private static void interceptItemRegister(String id, Item item, CallbackInfoReturnable<Item> cir) {
        if (id.equals("blaze_powder")) {
            // Required because minecraft throws an error if an item is constructed but not registered
            Items.register(new Identifier(NetherArchives.NAMESPACE, "void1"), item);
            cir.setReturnValue(
                    Items.register(new Identifier(id), new BlockItem(NetherArchivesBlocks.BLAZE_POWDER_BLOCK, new Item.Settings()))
            );
        }
    }
}
