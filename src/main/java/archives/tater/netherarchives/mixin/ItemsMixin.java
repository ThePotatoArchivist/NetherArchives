package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.block.NetherArchivesBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public class ItemsMixin {
    @Redirect(
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/item/Items;BLAZE_ROD:Lnet/minecraft/item/Item;",
                            opcode = Opcodes.PUTSTATIC,
                            ordinal = 0
                    )
            ),
            at = @At(
                    value = "NEW",
                    args = "class=net/minecraft/item/Item"
            ),
            method = "<clinit>"
    )
    private static Item interceptItemRegister(Item.Settings settings) {
        return new BlockItem(NetherArchivesBlocks.BLAZE_POWDER_BLOCK, new Item.Settings());
    }
}
