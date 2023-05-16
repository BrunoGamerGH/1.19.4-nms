package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketListener;

public interface PacketListenerPlayOut extends PacketListener {
   void a(PacketPlayOutSpawnEntity var1);

   void a(PacketPlayOutSpawnEntityExperienceOrb var1);

   void a(PacketPlayOutScoreboardObjective var1);

   void a(PacketPlayOutNamedEntitySpawn var1);

   void a(PacketPlayOutAnimation var1);

   void a(ClientboundHurtAnimationPacket var1);

   void a(PacketPlayOutStatistic var1);

   void a(PacketPlayOutRecipes var1);

   void a(PacketPlayOutBlockBreakAnimation var1);

   void a(PacketPlayOutOpenSignEditor var1);

   void a(PacketPlayOutTileEntityData var1);

   void a(PacketPlayOutBlockAction var1);

   void a(PacketPlayOutBlockChange var1);

   void a(ClientboundSystemChatPacket var1);

   void a(ClientboundPlayerChatPacket var1);

   void a(ClientboundDisguisedChatPacket var1);

   void a(ClientboundDeleteChatPacket var1);

   void a(PacketPlayOutMultiBlockChange var1);

   void a(PacketPlayOutMap var1);

   void a(PacketPlayOutCloseWindow var1);

   void a(PacketPlayOutWindowItems var1);

   void a(PacketPlayOutOpenWindowHorse var1);

   void a(PacketPlayOutWindowData var1);

   void a(PacketPlayOutSetSlot var1);

   void a(PacketPlayOutCustomPayload var1);

   void a(PacketPlayOutKickDisconnect var1);

   void a(PacketPlayOutEntityStatus var1);

   void a(PacketPlayOutAttachEntity var1);

   void a(PacketPlayOutMount var1);

   void a(PacketPlayOutExplosion var1);

   void a(PacketPlayOutGameStateChange var1);

   void a(PacketPlayOutKeepAlive var1);

   void a(ClientboundLevelChunkWithLightPacket var1);

   void a(ClientboundChunksBiomesPacket var1);

   void a(PacketPlayOutUnloadChunk var1);

   void a(PacketPlayOutWorldEvent var1);

   void a(PacketPlayOutLogin var1);

   void a(PacketPlayOutEntity var1);

   void a(PacketPlayOutPosition var1);

   void a(PacketPlayOutWorldParticles var1);

   void a(ClientboundPingPacket var1);

   void a(PacketPlayOutAbilities var1);

   void a(ClientboundPlayerInfoRemovePacket var1);

   void a(ClientboundPlayerInfoUpdatePacket var1);

   void a(PacketPlayOutEntityDestroy var1);

   void a(PacketPlayOutRemoveEntityEffect var1);

   void a(PacketPlayOutRespawn var1);

   void a(PacketPlayOutEntityHeadRotation var1);

   void a(PacketPlayOutHeldItemSlot var1);

   void a(PacketPlayOutScoreboardDisplayObjective var1);

   void a(PacketPlayOutEntityMetadata var1);

   void a(PacketPlayOutEntityVelocity var1);

   void a(PacketPlayOutEntityEquipment var1);

   void a(PacketPlayOutExperience var1);

   void a(PacketPlayOutUpdateHealth var1);

   void a(PacketPlayOutScoreboardTeam var1);

   void a(PacketPlayOutScoreboardScore var1);

   void a(PacketPlayOutSpawnPosition var1);

   void a(PacketPlayOutUpdateTime var1);

   void a(PacketPlayOutNamedSoundEffect var1);

   void a(PacketPlayOutEntitySound var1);

   void a(PacketPlayOutCollect var1);

   void a(PacketPlayOutEntityTeleport var1);

   void a(PacketPlayOutUpdateAttributes var1);

   void a(PacketPlayOutEntityEffect var1);

   void a(PacketPlayOutTags var1);

   void a(ClientboundPlayerCombatEndPacket var1);

   void a(ClientboundPlayerCombatEnterPacket var1);

   void a(ClientboundPlayerCombatKillPacket var1);

   void a(PacketPlayOutServerDifficulty var1);

   void a(PacketPlayOutCamera var1);

   void a(ClientboundInitializeBorderPacket var1);

   void a(ClientboundSetBorderLerpSizePacket var1);

   void a(ClientboundSetBorderSizePacket var1);

   void a(ClientboundSetBorderWarningDelayPacket var1);

   void a(ClientboundSetBorderWarningDistancePacket var1);

   void a(ClientboundSetBorderCenterPacket var1);

   void a(PacketPlayOutPlayerListHeaderFooter var1);

   void a(PacketPlayOutResourcePackSend var1);

   void a(PacketPlayOutBoss var1);

   void a(PacketPlayOutSetCooldown var1);

   void a(PacketPlayOutVehicleMove var1);

   void a(PacketPlayOutAdvancements var1);

   void a(PacketPlayOutSelectAdvancementTab var1);

   void a(PacketPlayOutAutoRecipe var1);

   void a(PacketPlayOutCommands var1);

   void a(PacketPlayOutStopSound var1);

   void a(PacketPlayOutTabComplete var1);

   void a(PacketPlayOutRecipeUpdate var1);

   void a(PacketPlayOutLookAt var1);

   void a(PacketPlayOutNBTQuery var1);

   void a(PacketPlayOutLightUpdate var1);

   void a(PacketPlayOutOpenBook var1);

   void a(PacketPlayOutOpenWindow var1);

   void a(PacketPlayOutOpenWindowMerchant var1);

   void a(PacketPlayOutViewDistance var1);

   void a(ClientboundSetSimulationDistancePacket var1);

   void a(PacketPlayOutViewCentre var1);

   void a(ClientboundBlockChangedAckPacket var1);

   void a(ClientboundSetActionBarTextPacket var1);

   void a(ClientboundSetSubtitleTextPacket var1);

   void a(ClientboundSetTitleTextPacket var1);

   void a(ClientboundSetTitlesAnimationPacket var1);

   void a(ClientboundClearTitlesPacket var1);

   void a(ClientboundServerDataPacket var1);

   void a(ClientboundCustomChatCompletionsPacket var1);

   void a(ClientboundUpdateEnabledFeaturesPacket var1);

   void a(ClientboundBundlePacket var1);

   void a(ClientboundDamageEventPacket var1);
}
