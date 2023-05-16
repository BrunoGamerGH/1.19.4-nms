package net.minecraft.network;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.network.protocol.BundleDelimiterPacket;
import net.minecraft.network.protocol.BundlePacket;
import net.minecraft.network.protocol.BundlerInfo;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.network.protocol.game.ClientboundChunksBiomesPacket;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundCustomChatCompletionsPacket;
import net.minecraft.network.protocol.game.ClientboundDamageEventPacket;
import net.minecraft.network.protocol.game.ClientboundDeleteChatPacket;
import net.minecraft.network.protocol.game.ClientboundDisguisedChatPacket;
import net.minecraft.network.protocol.game.ClientboundHurtAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetSimulationDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateEnabledFeaturesPacket;
import net.minecraft.network.protocol.game.PacketPlayInAbilities;
import net.minecraft.network.protocol.game.PacketPlayInAdvancements;
import net.minecraft.network.protocol.game.PacketPlayInArmAnimation;
import net.minecraft.network.protocol.game.PacketPlayInAutoRecipe;
import net.minecraft.network.protocol.game.PacketPlayInBEdit;
import net.minecraft.network.protocol.game.PacketPlayInBeacon;
import net.minecraft.network.protocol.game.PacketPlayInBlockDig;
import net.minecraft.network.protocol.game.PacketPlayInBlockPlace;
import net.minecraft.network.protocol.game.PacketPlayInBoatMove;
import net.minecraft.network.protocol.game.PacketPlayInChat;
import net.minecraft.network.protocol.game.PacketPlayInClientCommand;
import net.minecraft.network.protocol.game.PacketPlayInCloseWindow;
import net.minecraft.network.protocol.game.PacketPlayInCustomPayload;
import net.minecraft.network.protocol.game.PacketPlayInDifficultyChange;
import net.minecraft.network.protocol.game.PacketPlayInDifficultyLock;
import net.minecraft.network.protocol.game.PacketPlayInEnchantItem;
import net.minecraft.network.protocol.game.PacketPlayInEntityAction;
import net.minecraft.network.protocol.game.PacketPlayInEntityNBTQuery;
import net.minecraft.network.protocol.game.PacketPlayInFlying;
import net.minecraft.network.protocol.game.PacketPlayInHeldItemSlot;
import net.minecraft.network.protocol.game.PacketPlayInItemName;
import net.minecraft.network.protocol.game.PacketPlayInJigsawGenerate;
import net.minecraft.network.protocol.game.PacketPlayInKeepAlive;
import net.minecraft.network.protocol.game.PacketPlayInPickItem;
import net.minecraft.network.protocol.game.PacketPlayInRecipeDisplayed;
import net.minecraft.network.protocol.game.PacketPlayInRecipeSettings;
import net.minecraft.network.protocol.game.PacketPlayInResourcePackStatus;
import net.minecraft.network.protocol.game.PacketPlayInSetCommandBlock;
import net.minecraft.network.protocol.game.PacketPlayInSetCommandMinecart;
import net.minecraft.network.protocol.game.PacketPlayInSetCreativeSlot;
import net.minecraft.network.protocol.game.PacketPlayInSetJigsaw;
import net.minecraft.network.protocol.game.PacketPlayInSettings;
import net.minecraft.network.protocol.game.PacketPlayInSpectate;
import net.minecraft.network.protocol.game.PacketPlayInSteerVehicle;
import net.minecraft.network.protocol.game.PacketPlayInStruct;
import net.minecraft.network.protocol.game.PacketPlayInTabComplete;
import net.minecraft.network.protocol.game.PacketPlayInTeleportAccept;
import net.minecraft.network.protocol.game.PacketPlayInTileNBTQuery;
import net.minecraft.network.protocol.game.PacketPlayInTrSel;
import net.minecraft.network.protocol.game.PacketPlayInUpdateSign;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import net.minecraft.network.protocol.game.PacketPlayInUseItem;
import net.minecraft.network.protocol.game.PacketPlayInVehicleMove;
import net.minecraft.network.protocol.game.PacketPlayInWindowClick;
import net.minecraft.network.protocol.game.PacketPlayOutAbilities;
import net.minecraft.network.protocol.game.PacketPlayOutAdvancements;
import net.minecraft.network.protocol.game.PacketPlayOutAnimation;
import net.minecraft.network.protocol.game.PacketPlayOutAttachEntity;
import net.minecraft.network.protocol.game.PacketPlayOutAutoRecipe;
import net.minecraft.network.protocol.game.PacketPlayOutBlockAction;
import net.minecraft.network.protocol.game.PacketPlayOutBlockBreakAnimation;
import net.minecraft.network.protocol.game.PacketPlayOutBlockChange;
import net.minecraft.network.protocol.game.PacketPlayOutBoss;
import net.minecraft.network.protocol.game.PacketPlayOutCamera;
import net.minecraft.network.protocol.game.PacketPlayOutCloseWindow;
import net.minecraft.network.protocol.game.PacketPlayOutCollect;
import net.minecraft.network.protocol.game.PacketPlayOutCommands;
import net.minecraft.network.protocol.game.PacketPlayOutCustomPayload;
import net.minecraft.network.protocol.game.PacketPlayOutEntity;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEffect;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEquipment;
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutEntitySound;
import net.minecraft.network.protocol.game.PacketPlayOutEntityStatus;
import net.minecraft.network.protocol.game.PacketPlayOutEntityTeleport;
import net.minecraft.network.protocol.game.PacketPlayOutEntityVelocity;
import net.minecraft.network.protocol.game.PacketPlayOutExperience;
import net.minecraft.network.protocol.game.PacketPlayOutExplosion;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.network.protocol.game.PacketPlayOutHeldItemSlot;
import net.minecraft.network.protocol.game.PacketPlayOutKeepAlive;
import net.minecraft.network.protocol.game.PacketPlayOutKickDisconnect;
import net.minecraft.network.protocol.game.PacketPlayOutLightUpdate;
import net.minecraft.network.protocol.game.PacketPlayOutLogin;
import net.minecraft.network.protocol.game.PacketPlayOutLookAt;
import net.minecraft.network.protocol.game.PacketPlayOutMap;
import net.minecraft.network.protocol.game.PacketPlayOutMount;
import net.minecraft.network.protocol.game.PacketPlayOutMultiBlockChange;
import net.minecraft.network.protocol.game.PacketPlayOutNBTQuery;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutNamedSoundEffect;
import net.minecraft.network.protocol.game.PacketPlayOutOpenBook;
import net.minecraft.network.protocol.game.PacketPlayOutOpenSignEditor;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindowHorse;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindowMerchant;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.network.protocol.game.PacketPlayOutPosition;
import net.minecraft.network.protocol.game.PacketPlayOutRecipeUpdate;
import net.minecraft.network.protocol.game.PacketPlayOutRecipes;
import net.minecraft.network.protocol.game.PacketPlayOutRemoveEntityEffect;
import net.minecraft.network.protocol.game.PacketPlayOutResourcePackSend;
import net.minecraft.network.protocol.game.PacketPlayOutRespawn;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardObjective;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardScore;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardTeam;
import net.minecraft.network.protocol.game.PacketPlayOutSelectAdvancementTab;
import net.minecraft.network.protocol.game.PacketPlayOutServerDifficulty;
import net.minecraft.network.protocol.game.PacketPlayOutSetCooldown;
import net.minecraft.network.protocol.game.PacketPlayOutSetSlot;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnPosition;
import net.minecraft.network.protocol.game.PacketPlayOutStatistic;
import net.minecraft.network.protocol.game.PacketPlayOutStopSound;
import net.minecraft.network.protocol.game.PacketPlayOutTabComplete;
import net.minecraft.network.protocol.game.PacketPlayOutTags;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import net.minecraft.network.protocol.game.PacketPlayOutUnloadChunk;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateAttributes;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateHealth;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateTime;
import net.minecraft.network.protocol.game.PacketPlayOutVehicleMove;
import net.minecraft.network.protocol.game.PacketPlayOutViewCentre;
import net.minecraft.network.protocol.game.PacketPlayOutViewDistance;
import net.minecraft.network.protocol.game.PacketPlayOutWindowData;
import net.minecraft.network.protocol.game.PacketPlayOutWindowItems;
import net.minecraft.network.protocol.game.PacketPlayOutWorldEvent;
import net.minecraft.network.protocol.game.PacketPlayOutWorldParticles;
import net.minecraft.network.protocol.game.ServerboundChatAckPacket;
import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
import net.minecraft.network.protocol.game.ServerboundChatSessionUpdatePacket;
import net.minecraft.network.protocol.game.ServerboundPongPacket;
import net.minecraft.network.protocol.handshake.PacketHandshakingInSetProtocol;
import net.minecraft.network.protocol.login.PacketLoginInCustomPayload;
import net.minecraft.network.protocol.login.PacketLoginInEncryptionBegin;
import net.minecraft.network.protocol.login.PacketLoginInStart;
import net.minecraft.network.protocol.login.PacketLoginOutCustomPayload;
import net.minecraft.network.protocol.login.PacketLoginOutDisconnect;
import net.minecraft.network.protocol.login.PacketLoginOutEncryptionBegin;
import net.minecraft.network.protocol.login.PacketLoginOutSetCompression;
import net.minecraft.network.protocol.login.PacketLoginOutSuccess;
import net.minecraft.network.protocol.status.PacketStatusInPing;
import net.minecraft.network.protocol.status.PacketStatusInStart;
import net.minecraft.network.protocol.status.PacketStatusOutPong;
import net.minecraft.network.protocol.status.PacketStatusOutServerInfo;
import net.minecraft.util.VisibleForDebug;
import org.slf4j.Logger;

public enum EnumProtocol implements BundlerInfo.b {
   a(-1, b().a(EnumProtocolDirection.a, new EnumProtocol.a().a(PacketHandshakingInSetProtocol.class, PacketHandshakingInSetProtocol::new))),
   b(
      0,
      b()
         .a(
            EnumProtocolDirection.b,
            new EnumProtocol.a()
               .b(ClientboundBundlePacket.class, ClientboundBundlePacket::new)
               .a(PacketPlayOutSpawnEntity.class, PacketPlayOutSpawnEntity::new)
               .a(PacketPlayOutSpawnEntityExperienceOrb.class, PacketPlayOutSpawnEntityExperienceOrb::new)
               .a(PacketPlayOutNamedEntitySpawn.class, PacketPlayOutNamedEntitySpawn::new)
               .a(PacketPlayOutAnimation.class, PacketPlayOutAnimation::new)
               .a(PacketPlayOutStatistic.class, PacketPlayOutStatistic::new)
               .a(ClientboundBlockChangedAckPacket.class, ClientboundBlockChangedAckPacket::new)
               .a(PacketPlayOutBlockBreakAnimation.class, PacketPlayOutBlockBreakAnimation::new)
               .a(PacketPlayOutTileEntityData.class, PacketPlayOutTileEntityData::new)
               .a(PacketPlayOutBlockAction.class, PacketPlayOutBlockAction::new)
               .a(PacketPlayOutBlockChange.class, PacketPlayOutBlockChange::new)
               .a(PacketPlayOutBoss.class, PacketPlayOutBoss::new)
               .a(PacketPlayOutServerDifficulty.class, PacketPlayOutServerDifficulty::new)
               .a(ClientboundChunksBiomesPacket.class, ClientboundChunksBiomesPacket::new)
               .a(ClientboundClearTitlesPacket.class, ClientboundClearTitlesPacket::new)
               .a(PacketPlayOutTabComplete.class, PacketPlayOutTabComplete::new)
               .a(PacketPlayOutCommands.class, PacketPlayOutCommands::new)
               .a(PacketPlayOutCloseWindow.class, PacketPlayOutCloseWindow::new)
               .a(PacketPlayOutWindowItems.class, PacketPlayOutWindowItems::new)
               .a(PacketPlayOutWindowData.class, PacketPlayOutWindowData::new)
               .a(PacketPlayOutSetSlot.class, PacketPlayOutSetSlot::new)
               .a(PacketPlayOutSetCooldown.class, PacketPlayOutSetCooldown::new)
               .a(ClientboundCustomChatCompletionsPacket.class, ClientboundCustomChatCompletionsPacket::new)
               .a(PacketPlayOutCustomPayload.class, PacketPlayOutCustomPayload::new)
               .a(ClientboundDamageEventPacket.class, ClientboundDamageEventPacket::new)
               .a(ClientboundDeleteChatPacket.class, ClientboundDeleteChatPacket::new)
               .a(PacketPlayOutKickDisconnect.class, PacketPlayOutKickDisconnect::new)
               .a(ClientboundDisguisedChatPacket.class, ClientboundDisguisedChatPacket::new)
               .a(PacketPlayOutEntityStatus.class, PacketPlayOutEntityStatus::new)
               .a(PacketPlayOutExplosion.class, PacketPlayOutExplosion::new)
               .a(PacketPlayOutUnloadChunk.class, PacketPlayOutUnloadChunk::new)
               .a(PacketPlayOutGameStateChange.class, PacketPlayOutGameStateChange::new)
               .a(PacketPlayOutOpenWindowHorse.class, PacketPlayOutOpenWindowHorse::new)
               .a(ClientboundHurtAnimationPacket.class, ClientboundHurtAnimationPacket::new)
               .a(ClientboundInitializeBorderPacket.class, ClientboundInitializeBorderPacket::new)
               .a(PacketPlayOutKeepAlive.class, PacketPlayOutKeepAlive::new)
               .a(ClientboundLevelChunkWithLightPacket.class, ClientboundLevelChunkWithLightPacket::new)
               .a(PacketPlayOutWorldEvent.class, PacketPlayOutWorldEvent::new)
               .a(PacketPlayOutWorldParticles.class, PacketPlayOutWorldParticles::new)
               .a(PacketPlayOutLightUpdate.class, PacketPlayOutLightUpdate::new)
               .a(PacketPlayOutLogin.class, PacketPlayOutLogin::new)
               .a(PacketPlayOutMap.class, PacketPlayOutMap::new)
               .a(PacketPlayOutOpenWindowMerchant.class, PacketPlayOutOpenWindowMerchant::new)
               .a(PacketPlayOutEntity.PacketPlayOutRelEntityMove.class, PacketPlayOutEntity.PacketPlayOutRelEntityMove::b)
               .a(PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook.class, PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook::b)
               .a(PacketPlayOutEntity.PacketPlayOutEntityLook.class, PacketPlayOutEntity.PacketPlayOutEntityLook::b)
               .a(PacketPlayOutVehicleMove.class, PacketPlayOutVehicleMove::new)
               .a(PacketPlayOutOpenBook.class, PacketPlayOutOpenBook::new)
               .a(PacketPlayOutOpenWindow.class, PacketPlayOutOpenWindow::new)
               .a(PacketPlayOutOpenSignEditor.class, PacketPlayOutOpenSignEditor::new)
               .a(ClientboundPingPacket.class, ClientboundPingPacket::new)
               .a(PacketPlayOutAutoRecipe.class, PacketPlayOutAutoRecipe::new)
               .a(PacketPlayOutAbilities.class, PacketPlayOutAbilities::new)
               .a(ClientboundPlayerChatPacket.class, ClientboundPlayerChatPacket::new)
               .a(ClientboundPlayerCombatEndPacket.class, ClientboundPlayerCombatEndPacket::new)
               .a(ClientboundPlayerCombatEnterPacket.class, ClientboundPlayerCombatEnterPacket::new)
               .a(ClientboundPlayerCombatKillPacket.class, ClientboundPlayerCombatKillPacket::new)
               .a(ClientboundPlayerInfoRemovePacket.class, ClientboundPlayerInfoRemovePacket::new)
               .a(ClientboundPlayerInfoUpdatePacket.class, ClientboundPlayerInfoUpdatePacket::new)
               .a(PacketPlayOutLookAt.class, PacketPlayOutLookAt::new)
               .a(PacketPlayOutPosition.class, PacketPlayOutPosition::new)
               .a(PacketPlayOutRecipes.class, PacketPlayOutRecipes::new)
               .a(PacketPlayOutEntityDestroy.class, PacketPlayOutEntityDestroy::new)
               .a(PacketPlayOutRemoveEntityEffect.class, PacketPlayOutRemoveEntityEffect::new)
               .a(PacketPlayOutResourcePackSend.class, PacketPlayOutResourcePackSend::new)
               .a(PacketPlayOutRespawn.class, PacketPlayOutRespawn::new)
               .a(PacketPlayOutEntityHeadRotation.class, PacketPlayOutEntityHeadRotation::new)
               .a(PacketPlayOutMultiBlockChange.class, PacketPlayOutMultiBlockChange::new)
               .a(PacketPlayOutSelectAdvancementTab.class, PacketPlayOutSelectAdvancementTab::new)
               .a(ClientboundServerDataPacket.class, ClientboundServerDataPacket::new)
               .a(ClientboundSetActionBarTextPacket.class, ClientboundSetActionBarTextPacket::new)
               .a(ClientboundSetBorderCenterPacket.class, ClientboundSetBorderCenterPacket::new)
               .a(ClientboundSetBorderLerpSizePacket.class, ClientboundSetBorderLerpSizePacket::new)
               .a(ClientboundSetBorderSizePacket.class, ClientboundSetBorderSizePacket::new)
               .a(ClientboundSetBorderWarningDelayPacket.class, ClientboundSetBorderWarningDelayPacket::new)
               .a(ClientboundSetBorderWarningDistancePacket.class, ClientboundSetBorderWarningDistancePacket::new)
               .a(PacketPlayOutCamera.class, PacketPlayOutCamera::new)
               .a(PacketPlayOutHeldItemSlot.class, PacketPlayOutHeldItemSlot::new)
               .a(PacketPlayOutViewCentre.class, PacketPlayOutViewCentre::new)
               .a(PacketPlayOutViewDistance.class, PacketPlayOutViewDistance::new)
               .a(PacketPlayOutSpawnPosition.class, PacketPlayOutSpawnPosition::new)
               .a(PacketPlayOutScoreboardDisplayObjective.class, PacketPlayOutScoreboardDisplayObjective::new)
               .a(PacketPlayOutEntityMetadata.class, PacketPlayOutEntityMetadata::new)
               .a(PacketPlayOutAttachEntity.class, PacketPlayOutAttachEntity::new)
               .a(PacketPlayOutEntityVelocity.class, PacketPlayOutEntityVelocity::new)
               .a(PacketPlayOutEntityEquipment.class, PacketPlayOutEntityEquipment::new)
               .a(PacketPlayOutExperience.class, PacketPlayOutExperience::new)
               .a(PacketPlayOutUpdateHealth.class, PacketPlayOutUpdateHealth::new)
               .a(PacketPlayOutScoreboardObjective.class, PacketPlayOutScoreboardObjective::new)
               .a(PacketPlayOutMount.class, PacketPlayOutMount::new)
               .a(PacketPlayOutScoreboardTeam.class, PacketPlayOutScoreboardTeam::new)
               .a(PacketPlayOutScoreboardScore.class, PacketPlayOutScoreboardScore::new)
               .a(ClientboundSetSimulationDistancePacket.class, ClientboundSetSimulationDistancePacket::new)
               .a(ClientboundSetSubtitleTextPacket.class, ClientboundSetSubtitleTextPacket::new)
               .a(PacketPlayOutUpdateTime.class, PacketPlayOutUpdateTime::new)
               .a(ClientboundSetTitleTextPacket.class, ClientboundSetTitleTextPacket::new)
               .a(ClientboundSetTitlesAnimationPacket.class, ClientboundSetTitlesAnimationPacket::new)
               .a(PacketPlayOutEntitySound.class, PacketPlayOutEntitySound::new)
               .a(PacketPlayOutNamedSoundEffect.class, PacketPlayOutNamedSoundEffect::new)
               .a(PacketPlayOutStopSound.class, PacketPlayOutStopSound::new)
               .a(ClientboundSystemChatPacket.class, ClientboundSystemChatPacket::new)
               .a(PacketPlayOutPlayerListHeaderFooter.class, PacketPlayOutPlayerListHeaderFooter::new)
               .a(PacketPlayOutNBTQuery.class, PacketPlayOutNBTQuery::new)
               .a(PacketPlayOutCollect.class, PacketPlayOutCollect::new)
               .a(PacketPlayOutEntityTeleport.class, PacketPlayOutEntityTeleport::new)
               .a(PacketPlayOutAdvancements.class, PacketPlayOutAdvancements::new)
               .a(PacketPlayOutUpdateAttributes.class, PacketPlayOutUpdateAttributes::new)
               .a(ClientboundUpdateEnabledFeaturesPacket.class, ClientboundUpdateEnabledFeaturesPacket::new)
               .a(PacketPlayOutEntityEffect.class, PacketPlayOutEntityEffect::new)
               .a(PacketPlayOutRecipeUpdate.class, PacketPlayOutRecipeUpdate::new)
               .a(PacketPlayOutTags.class, PacketPlayOutTags::new)
         )
         .a(
            EnumProtocolDirection.a,
            new EnumProtocol.a()
               .a(PacketPlayInTeleportAccept.class, PacketPlayInTeleportAccept::new)
               .a(PacketPlayInTileNBTQuery.class, PacketPlayInTileNBTQuery::new)
               .a(PacketPlayInDifficultyChange.class, PacketPlayInDifficultyChange::new)
               .a(ServerboundChatAckPacket.class, ServerboundChatAckPacket::new)
               .a(ServerboundChatCommandPacket.class, ServerboundChatCommandPacket::new)
               .a(PacketPlayInChat.class, PacketPlayInChat::new)
               .a(ServerboundChatSessionUpdatePacket.class, ServerboundChatSessionUpdatePacket::new)
               .a(PacketPlayInClientCommand.class, PacketPlayInClientCommand::new)
               .a(PacketPlayInSettings.class, PacketPlayInSettings::new)
               .a(PacketPlayInTabComplete.class, PacketPlayInTabComplete::new)
               .a(PacketPlayInEnchantItem.class, PacketPlayInEnchantItem::new)
               .a(PacketPlayInWindowClick.class, PacketPlayInWindowClick::new)
               .a(PacketPlayInCloseWindow.class, PacketPlayInCloseWindow::new)
               .a(PacketPlayInCustomPayload.class, PacketPlayInCustomPayload::new)
               .a(PacketPlayInBEdit.class, PacketPlayInBEdit::new)
               .a(PacketPlayInEntityNBTQuery.class, PacketPlayInEntityNBTQuery::new)
               .a(PacketPlayInUseEntity.class, PacketPlayInUseEntity::new)
               .a(PacketPlayInJigsawGenerate.class, PacketPlayInJigsawGenerate::new)
               .a(PacketPlayInKeepAlive.class, PacketPlayInKeepAlive::new)
               .a(PacketPlayInDifficultyLock.class, PacketPlayInDifficultyLock::new)
               .a(PacketPlayInFlying.PacketPlayInPosition.class, PacketPlayInFlying.PacketPlayInPosition::b)
               .a(PacketPlayInFlying.PacketPlayInPositionLook.class, PacketPlayInFlying.PacketPlayInPositionLook::b)
               .a(PacketPlayInFlying.PacketPlayInLook.class, PacketPlayInFlying.PacketPlayInLook::b)
               .a(PacketPlayInFlying.d.class, PacketPlayInFlying.d::b)
               .a(PacketPlayInVehicleMove.class, PacketPlayInVehicleMove::new)
               .a(PacketPlayInBoatMove.class, PacketPlayInBoatMove::new)
               .a(PacketPlayInPickItem.class, PacketPlayInPickItem::new)
               .a(PacketPlayInAutoRecipe.class, PacketPlayInAutoRecipe::new)
               .a(PacketPlayInAbilities.class, PacketPlayInAbilities::new)
               .a(PacketPlayInBlockDig.class, PacketPlayInBlockDig::new)
               .a(PacketPlayInEntityAction.class, PacketPlayInEntityAction::new)
               .a(PacketPlayInSteerVehicle.class, PacketPlayInSteerVehicle::new)
               .a(ServerboundPongPacket.class, ServerboundPongPacket::new)
               .a(PacketPlayInRecipeSettings.class, PacketPlayInRecipeSettings::new)
               .a(PacketPlayInRecipeDisplayed.class, PacketPlayInRecipeDisplayed::new)
               .a(PacketPlayInItemName.class, PacketPlayInItemName::new)
               .a(PacketPlayInResourcePackStatus.class, PacketPlayInResourcePackStatus::new)
               .a(PacketPlayInAdvancements.class, PacketPlayInAdvancements::new)
               .a(PacketPlayInTrSel.class, PacketPlayInTrSel::new)
               .a(PacketPlayInBeacon.class, PacketPlayInBeacon::new)
               .a(PacketPlayInHeldItemSlot.class, PacketPlayInHeldItemSlot::new)
               .a(PacketPlayInSetCommandBlock.class, PacketPlayInSetCommandBlock::new)
               .a(PacketPlayInSetCommandMinecart.class, PacketPlayInSetCommandMinecart::new)
               .a(PacketPlayInSetCreativeSlot.class, PacketPlayInSetCreativeSlot::new)
               .a(PacketPlayInSetJigsaw.class, PacketPlayInSetJigsaw::new)
               .a(PacketPlayInStruct.class, PacketPlayInStruct::new)
               .a(PacketPlayInUpdateSign.class, PacketPlayInUpdateSign::new)
               .a(PacketPlayInArmAnimation.class, PacketPlayInArmAnimation::new)
               .a(PacketPlayInSpectate.class, PacketPlayInSpectate::new)
               .a(PacketPlayInUseItem.class, PacketPlayInUseItem::new)
               .a(PacketPlayInBlockPlace.class, PacketPlayInBlockPlace::new)
         )
   ),
   c(
      1,
      b()
         .a(
            EnumProtocolDirection.a,
            new EnumProtocol.a().a(PacketStatusInStart.class, PacketStatusInStart::new).a(PacketStatusInPing.class, PacketStatusInPing::new)
         )
         .a(
            EnumProtocolDirection.b,
            new EnumProtocol.a().a(PacketStatusOutServerInfo.class, PacketStatusOutServerInfo::new).a(PacketStatusOutPong.class, PacketStatusOutPong::new)
         )
   ),
   d(
      2,
      b()
         .a(
            EnumProtocolDirection.b,
            new EnumProtocol.a()
               .a(PacketLoginOutDisconnect.class, PacketLoginOutDisconnect::new)
               .a(PacketLoginOutEncryptionBegin.class, PacketLoginOutEncryptionBegin::new)
               .a(PacketLoginOutSuccess.class, PacketLoginOutSuccess::new)
               .a(PacketLoginOutSetCompression.class, PacketLoginOutSetCompression::new)
               .a(PacketLoginOutCustomPayload.class, PacketLoginOutCustomPayload::new)
         )
         .a(
            EnumProtocolDirection.a,
            new EnumProtocol.a()
               .a(PacketLoginInStart.class, PacketLoginInStart::new)
               .a(PacketLoginInEncryptionBegin.class, PacketLoginInEncryptionBegin::new)
               .a(PacketLoginInCustomPayload.class, PacketLoginInCustomPayload::new)
         )
   );

   public static final int e = -1;
   private static final int f = -1;
   private static final int g = 2;
   private static final EnumProtocol[] h = new EnumProtocol[4];
   private static final Map<Class<? extends Packet<?>>, EnumProtocol> i = Maps.newHashMap();
   private final int j;
   private final Map<EnumProtocolDirection, ? extends EnumProtocol.a<?>> k;

   private static EnumProtocol.b b() {
      return new EnumProtocol.b();
   }

   private EnumProtocol(int var2, EnumProtocol.b var3) {
      this.j = var2;
      this.k = var3.a;
   }

   public int a(EnumProtocolDirection var0, Packet<?> var1) {
      return this.k.get(var0).a(var1.getClass());
   }

   @Override
   public BundlerInfo a(EnumProtocolDirection var0) {
      return this.k.get(var0).a();
   }

   @VisibleForDebug
   public Int2ObjectMap<Class<? extends Packet<?>>> b(EnumProtocolDirection var0) {
      Int2ObjectMap<Class<? extends Packet<?>>> var1 = new Int2ObjectOpenHashMap();
      EnumProtocol.a<?> var2 = this.k.get(var0);
      if (var2 == null) {
         return Int2ObjectMaps.emptyMap();
      } else {
         var2.b.forEach((var1x, var2x) -> var1.put(var2x, var1x));
         return var1;
      }
   }

   @Nullable
   public Packet<?> a(EnumProtocolDirection var0, int var1, PacketDataSerializer var2) {
      return this.k.get(var0).a(var1, var2);
   }

   public int a() {
      return this.j;
   }

   @Nullable
   public static EnumProtocol a(int var0) {
      return var0 >= -1 && var0 <= 2 ? h[var0 - -1] : null;
   }

   @Nullable
   public static EnumProtocol a(Packet<?> var0) {
      return i.get(var0.getClass());
   }

   static {
      for(EnumProtocol var3 : values()) {
         int var4 = var3.a();
         if (var4 < -1 || var4 > 2) {
            throw new Error("Invalid protocol ID " + var4);
         }

         h[var4 - -1] = var3;
         var3.k.forEach((var1, var2) -> var2.a(var1x -> {
               if (i.containsKey(var1x) && i.get(var1x) != var3) {
                  throw new IllegalStateException("Packet " + var1x + " is already assigned to protocol " + i.get(var1x) + " - can't reassign to " + var3);
               } else {
                  i.put(var1x, var3);
               }
            }));
      }
   }

   static class a<T extends PacketListener> {
      private static final Logger a = LogUtils.getLogger();
      final Object2IntMap<Class<? extends Packet<T>>> b = SystemUtils.a(new Object2IntOpenHashMap(), var0 -> var0.defaultReturnValue(-1));
      private final List<Function<PacketDataSerializer, ? extends Packet<T>>> c = Lists.newArrayList();
      private BundlerInfo d = BundlerInfo.c;
      private final Set<Class<? extends Packet<T>>> e = new HashSet<>();

      public <P extends Packet<T>> EnumProtocol.a<T> a(Class<P> var0, Function<PacketDataSerializer, P> var1) {
         int var2 = this.c.size();
         int var3 = this.b.put(var0, var2);
         if (var3 != -1) {
            String var4 = "Packet " + var0 + " is already registered to ID " + var3;
            a.error(LogUtils.FATAL_MARKER, var4);
            throw new IllegalArgumentException(var4);
         } else {
            this.c.add(var1);
            return this;
         }
      }

      public <P extends BundlePacket<T>> EnumProtocol.a<T> b(Class<P> var0, Function<Iterable<Packet<T>>, P> var1) {
         if (this.d != BundlerInfo.c) {
            throw new IllegalStateException("Bundle packet already configured");
         } else {
            BundleDelimiterPacket<T> var2 = new BundleDelimiterPacket<>();
            this.a(BundleDelimiterPacket.class, var1x -> var2);
            this.d = BundlerInfo.a(var0, var1, var2);
            this.e.add(var0);
            return this;
         }
      }

      public int a(Class<?> var0) {
         return this.b.getInt(var0);
      }

      @Nullable
      public Packet<?> a(int var0, PacketDataSerializer var1) {
         Function<PacketDataSerializer, ? extends Packet<T>> var2 = this.c.get(var0);
         return var2 != null ? var2.apply((T)var1) : null;
      }

      public void a(Consumer<Class<? extends Packet<?>>> var0) {
         this.b.keySet().stream().filter(var0x -> var0x != BundleDelimiterPacket.class).forEach(var0);
         this.e.forEach(var0);
      }

      public BundlerInfo a() {
         return this.d;
      }
   }

   static class b {
      final Map<EnumProtocolDirection, EnumProtocol.a<?>> a = Maps.newEnumMap(EnumProtocolDirection.class);

      public <T extends PacketListener> EnumProtocol.b a(EnumProtocolDirection var0, EnumProtocol.a<T> var1) {
         this.a.put(var0, var1);
         return this;
      }
   }
}
