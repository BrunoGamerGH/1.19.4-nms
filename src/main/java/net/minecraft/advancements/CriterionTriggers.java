package net.minecraft.advancements;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.CriterionSlideDownBlock;
import net.minecraft.advancements.critereon.CriterionTriggerBeeNestDestroyed;
import net.minecraft.advancements.critereon.CriterionTriggerBredAnimals;
import net.minecraft.advancements.critereon.CriterionTriggerBrewedPotion;
import net.minecraft.advancements.critereon.CriterionTriggerChangedDimension;
import net.minecraft.advancements.critereon.CriterionTriggerChanneledLightning;
import net.minecraft.advancements.critereon.CriterionTriggerConstructBeacon;
import net.minecraft.advancements.critereon.CriterionTriggerConsumeItem;
import net.minecraft.advancements.critereon.CriterionTriggerCuredZombieVillager;
import net.minecraft.advancements.critereon.CriterionTriggerEffectsChanged;
import net.minecraft.advancements.critereon.CriterionTriggerEnchantedItem;
import net.minecraft.advancements.critereon.CriterionTriggerEnterBlock;
import net.minecraft.advancements.critereon.CriterionTriggerEntityHurtPlayer;
import net.minecraft.advancements.critereon.CriterionTriggerFilledBucket;
import net.minecraft.advancements.critereon.CriterionTriggerFishingRodHooked;
import net.minecraft.advancements.critereon.CriterionTriggerImpossible;
import net.minecraft.advancements.critereon.CriterionTriggerInventoryChanged;
import net.minecraft.advancements.critereon.CriterionTriggerItemDurabilityChanged;
import net.minecraft.advancements.critereon.CriterionTriggerKilled;
import net.minecraft.advancements.critereon.CriterionTriggerKilledByCrossbow;
import net.minecraft.advancements.critereon.CriterionTriggerLevitation;
import net.minecraft.advancements.critereon.CriterionTriggerPlacedBlock;
import net.minecraft.advancements.critereon.CriterionTriggerPlayerGeneratesContainerLoot;
import net.minecraft.advancements.critereon.CriterionTriggerPlayerHurtEntity;
import net.minecraft.advancements.critereon.CriterionTriggerPlayerInteractedWithEntity;
import net.minecraft.advancements.critereon.CriterionTriggerRecipeUnlocked;
import net.minecraft.advancements.critereon.CriterionTriggerShotCrossbow;
import net.minecraft.advancements.critereon.CriterionTriggerSummonedEntity;
import net.minecraft.advancements.critereon.CriterionTriggerTamedAnimal;
import net.minecraft.advancements.critereon.CriterionTriggerTargetHit;
import net.minecraft.advancements.critereon.CriterionTriggerUsedEnderEye;
import net.minecraft.advancements.critereon.CriterionTriggerUsedTotem;
import net.minecraft.advancements.critereon.CriterionTriggerVillagerTrade;
import net.minecraft.advancements.critereon.DistanceTrigger;
import net.minecraft.advancements.critereon.ItemInteractWithBlockTrigger;
import net.minecraft.advancements.critereon.LightningStrikeTrigger;
import net.minecraft.advancements.critereon.PickedUpItemTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.advancements.critereon.StartRidingTrigger;
import net.minecraft.advancements.critereon.UsingItemTrigger;
import net.minecraft.resources.MinecraftKey;

public class CriterionTriggers {
   private static final Map<MinecraftKey, CriterionTrigger<?>> Z = Maps.newHashMap();
   public static final CriterionTriggerImpossible a = a(new CriterionTriggerImpossible());
   public static final CriterionTriggerKilled b = a(new CriterionTriggerKilled(new MinecraftKey("player_killed_entity")));
   public static final CriterionTriggerKilled c = a(new CriterionTriggerKilled(new MinecraftKey("entity_killed_player")));
   public static final CriterionTriggerEnterBlock d = a(new CriterionTriggerEnterBlock());
   public static final CriterionTriggerInventoryChanged e = a(new CriterionTriggerInventoryChanged());
   public static final CriterionTriggerRecipeUnlocked f = a(new CriterionTriggerRecipeUnlocked());
   public static final CriterionTriggerPlayerHurtEntity g = a(new CriterionTriggerPlayerHurtEntity());
   public static final CriterionTriggerEntityHurtPlayer h = a(new CriterionTriggerEntityHurtPlayer());
   public static final CriterionTriggerEnchantedItem i = a(new CriterionTriggerEnchantedItem());
   public static final CriterionTriggerFilledBucket j = a(new CriterionTriggerFilledBucket());
   public static final CriterionTriggerBrewedPotion k = a(new CriterionTriggerBrewedPotion());
   public static final CriterionTriggerConstructBeacon l = a(new CriterionTriggerConstructBeacon());
   public static final CriterionTriggerUsedEnderEye m = a(new CriterionTriggerUsedEnderEye());
   public static final CriterionTriggerSummonedEntity n = a(new CriterionTriggerSummonedEntity());
   public static final CriterionTriggerBredAnimals o = a(new CriterionTriggerBredAnimals());
   public static final PlayerTrigger p = a(new PlayerTrigger(new MinecraftKey("location")));
   public static final PlayerTrigger q = a(new PlayerTrigger(new MinecraftKey("slept_in_bed")));
   public static final CriterionTriggerCuredZombieVillager r = a(new CriterionTriggerCuredZombieVillager());
   public static final CriterionTriggerVillagerTrade s = a(new CriterionTriggerVillagerTrade());
   public static final CriterionTriggerItemDurabilityChanged t = a(new CriterionTriggerItemDurabilityChanged());
   public static final CriterionTriggerLevitation u = a(new CriterionTriggerLevitation());
   public static final CriterionTriggerChangedDimension v = a(new CriterionTriggerChangedDimension());
   public static final PlayerTrigger w = a(new PlayerTrigger(new MinecraftKey("tick")));
   public static final CriterionTriggerTamedAnimal x = a(new CriterionTriggerTamedAnimal());
   public static final CriterionTriggerPlacedBlock y = a(new CriterionTriggerPlacedBlock());
   public static final CriterionTriggerConsumeItem z = a(new CriterionTriggerConsumeItem());
   public static final CriterionTriggerEffectsChanged A = a(new CriterionTriggerEffectsChanged());
   public static final CriterionTriggerUsedTotem B = a(new CriterionTriggerUsedTotem());
   public static final DistanceTrigger C = a(new DistanceTrigger(new MinecraftKey("nether_travel")));
   public static final CriterionTriggerFishingRodHooked D = a(new CriterionTriggerFishingRodHooked());
   public static final CriterionTriggerChanneledLightning E = a(new CriterionTriggerChanneledLightning());
   public static final CriterionTriggerShotCrossbow F = a(new CriterionTriggerShotCrossbow());
   public static final CriterionTriggerKilledByCrossbow G = a(new CriterionTriggerKilledByCrossbow());
   public static final PlayerTrigger H = a(new PlayerTrigger(new MinecraftKey("hero_of_the_village")));
   public static final PlayerTrigger I = a(new PlayerTrigger(new MinecraftKey("voluntary_exile")));
   public static final CriterionSlideDownBlock J = a(new CriterionSlideDownBlock());
   public static final CriterionTriggerBeeNestDestroyed K = a(new CriterionTriggerBeeNestDestroyed());
   public static final CriterionTriggerTargetHit L = a(new CriterionTriggerTargetHit());
   public static final ItemInteractWithBlockTrigger M = a(new ItemInteractWithBlockTrigger(new MinecraftKey("item_used_on_block")));
   public static final CriterionTriggerPlayerGeneratesContainerLoot N = a(new CriterionTriggerPlayerGeneratesContainerLoot());
   public static final PickedUpItemTrigger O = a(new PickedUpItemTrigger(new MinecraftKey("thrown_item_picked_up_by_entity")));
   public static final PickedUpItemTrigger P = a(new PickedUpItemTrigger(new MinecraftKey("thrown_item_picked_up_by_player")));
   public static final CriterionTriggerPlayerInteractedWithEntity Q = a(new CriterionTriggerPlayerInteractedWithEntity());
   public static final StartRidingTrigger R = a(new StartRidingTrigger());
   public static final LightningStrikeTrigger S = a(new LightningStrikeTrigger());
   public static final UsingItemTrigger T = a(new UsingItemTrigger());
   public static final DistanceTrigger U = a(new DistanceTrigger(new MinecraftKey("fall_from_height")));
   public static final DistanceTrigger V = a(new DistanceTrigger(new MinecraftKey("ride_entity_in_lava")));
   public static final CriterionTriggerKilled W = a(new CriterionTriggerKilled(new MinecraftKey("kill_mob_near_sculk_catalyst")));
   public static final ItemInteractWithBlockTrigger X = a(new ItemInteractWithBlockTrigger(new MinecraftKey("allay_drop_item_on_block")));
   public static final PlayerTrigger Y = a(new PlayerTrigger(new MinecraftKey("avoid_vibration")));

   private static <T extends CriterionTrigger<?>> T a(T var0) {
      if (Z.containsKey(var0.a())) {
         throw new IllegalArgumentException("Duplicate criterion id " + var0.a());
      } else {
         Z.put(var0.a(), var0);
         return var0;
      }
   }

   @Nullable
   public static <T extends CriterionInstance> CriterionTrigger<T> a(MinecraftKey var0) {
      return (CriterionTrigger<T>)Z.get(var0);
   }

   public static Iterable<? extends CriterionTrigger<?>> a() {
      return Z.values();
   }
}
