package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.NetherArchives;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.resources.model.EquipmentClientInfo;

@Mixin(EquipmentClientInfo.LayerType.class)
public enum EquipmentClientInfoLayerTypeMixin {
    NETHERARCHIVES_SKIS(NetherArchives.MOD_ID + "_skis");

    @Shadow
    EquipmentClientInfoLayerTypeMixin(String id) {
    }
}
