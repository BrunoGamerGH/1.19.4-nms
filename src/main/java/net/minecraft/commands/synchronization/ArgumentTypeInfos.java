package net.minecraft.commands.synchronization;

import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import java.util.Locale;
import java.util.Map;
import net.minecraft.SharedConstants;
import net.minecraft.commands.arguments.ArgumentAnchor;
import net.minecraft.commands.arguments.ArgumentAngle;
import net.minecraft.commands.arguments.ArgumentChat;
import net.minecraft.commands.arguments.ArgumentChatComponent;
import net.minecraft.commands.arguments.ArgumentChatFormat;
import net.minecraft.commands.arguments.ArgumentCriterionValue;
import net.minecraft.commands.arguments.ArgumentDimension;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.ArgumentInventorySlot;
import net.minecraft.commands.arguments.ArgumentMathOperation;
import net.minecraft.commands.arguments.ArgumentMinecraftKeyRegistered;
import net.minecraft.commands.arguments.ArgumentNBTBase;
import net.minecraft.commands.arguments.ArgumentNBTKey;
import net.minecraft.commands.arguments.ArgumentNBTTag;
import net.minecraft.commands.arguments.ArgumentParticle;
import net.minecraft.commands.arguments.ArgumentProfile;
import net.minecraft.commands.arguments.ArgumentScoreboardCriteria;
import net.minecraft.commands.arguments.ArgumentScoreboardObjective;
import net.minecraft.commands.arguments.ArgumentScoreboardSlot;
import net.minecraft.commands.arguments.ArgumentScoreboardTeam;
import net.minecraft.commands.arguments.ArgumentScoreholder;
import net.minecraft.commands.arguments.ArgumentTime;
import net.minecraft.commands.arguments.ArgumentUUID;
import net.minecraft.commands.arguments.GameModeArgument;
import net.minecraft.commands.arguments.HeightmapTypeArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.arguments.ResourceKeyArgument;
import net.minecraft.commands.arguments.ResourceOrTagArgument;
import net.minecraft.commands.arguments.ResourceOrTagKeyArgument;
import net.minecraft.commands.arguments.TemplateMirrorArgument;
import net.minecraft.commands.arguments.TemplateRotationArgument;
import net.minecraft.commands.arguments.blocks.ArgumentBlockPredicate;
import net.minecraft.commands.arguments.blocks.ArgumentTile;
import net.minecraft.commands.arguments.coordinates.ArgumentPosition;
import net.minecraft.commands.arguments.coordinates.ArgumentRotation;
import net.minecraft.commands.arguments.coordinates.ArgumentRotationAxis;
import net.minecraft.commands.arguments.coordinates.ArgumentVec2;
import net.minecraft.commands.arguments.coordinates.ArgumentVec2I;
import net.minecraft.commands.arguments.coordinates.ArgumentVec3;
import net.minecraft.commands.arguments.item.ArgumentItemPredicate;
import net.minecraft.commands.arguments.item.ArgumentItemStack;
import net.minecraft.commands.arguments.item.ArgumentTag;
import net.minecraft.commands.synchronization.brigadier.ArgumentSerializerString;
import net.minecraft.commands.synchronization.brigadier.DoubleArgumentInfo;
import net.minecraft.commands.synchronization.brigadier.FloatArgumentInfo;
import net.minecraft.commands.synchronization.brigadier.IntegerArgumentInfo;
import net.minecraft.commands.synchronization.brigadier.LongArgumentInfo;
import net.minecraft.core.IRegistry;
import net.minecraft.gametest.framework.GameTestHarnessTestClassArgument;
import net.minecraft.gametest.framework.GameTestHarnessTestFunctionArgument;

public class ArgumentTypeInfos {
   private static final Map<Class<?>, ArgumentTypeInfo<?, ?>> a = Maps.newHashMap();

   private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.a<A>> ArgumentTypeInfo<A, T> a(
      IRegistry<ArgumentTypeInfo<?, ?>> var0, String var1, Class<? extends A> var2, ArgumentTypeInfo<A, T> var3
   ) {
      a.put(var2, var3);
      return IRegistry.a(var0, var1, var3);
   }

   public static ArgumentTypeInfo<?, ?> a(IRegistry<ArgumentTypeInfo<?, ?>> var0) {
      a(var0, "brigadier:bool", BoolArgumentType.class, SingletonArgumentInfo.a(BoolArgumentType::bool));
      a(var0, "brigadier:float", FloatArgumentType.class, new FloatArgumentInfo());
      a(var0, "brigadier:double", DoubleArgumentType.class, new DoubleArgumentInfo());
      a(var0, "brigadier:integer", IntegerArgumentType.class, new IntegerArgumentInfo());
      a(var0, "brigadier:long", LongArgumentType.class, new LongArgumentInfo());
      a(var0, "brigadier:string", StringArgumentType.class, new ArgumentSerializerString());
      a(var0, "entity", ArgumentEntity.class, new ArgumentEntity.Info());
      a(var0, "game_profile", ArgumentProfile.class, SingletonArgumentInfo.a(ArgumentProfile::a));
      a(var0, "block_pos", ArgumentPosition.class, SingletonArgumentInfo.a(ArgumentPosition::a));
      a(var0, "column_pos", ArgumentVec2I.class, SingletonArgumentInfo.a(ArgumentVec2I::a));
      a(var0, "vec3", ArgumentVec3.class, SingletonArgumentInfo.a(ArgumentVec3::a));
      a(var0, "vec2", ArgumentVec2.class, SingletonArgumentInfo.a(ArgumentVec2::a));
      a(var0, "block_state", ArgumentTile.class, SingletonArgumentInfo.a(ArgumentTile::a));
      a(var0, "block_predicate", ArgumentBlockPredicate.class, SingletonArgumentInfo.a(ArgumentBlockPredicate::a));
      a(var0, "item_stack", ArgumentItemStack.class, SingletonArgumentInfo.a(ArgumentItemStack::a));
      a(var0, "item_predicate", ArgumentItemPredicate.class, SingletonArgumentInfo.a(ArgumentItemPredicate::a));
      a(var0, "color", ArgumentChatFormat.class, SingletonArgumentInfo.a(ArgumentChatFormat::a));
      a(var0, "component", ArgumentChatComponent.class, SingletonArgumentInfo.a(ArgumentChatComponent::a));
      a(var0, "message", ArgumentChat.class, SingletonArgumentInfo.a(ArgumentChat::a));
      a(var0, "nbt_compound_tag", ArgumentNBTTag.class, SingletonArgumentInfo.a(ArgumentNBTTag::a));
      a(var0, "nbt_tag", ArgumentNBTBase.class, SingletonArgumentInfo.a(ArgumentNBTBase::a));
      a(var0, "nbt_path", ArgumentNBTKey.class, SingletonArgumentInfo.a(ArgumentNBTKey::a));
      a(var0, "objective", ArgumentScoreboardObjective.class, SingletonArgumentInfo.a(ArgumentScoreboardObjective::a));
      a(var0, "objective_criteria", ArgumentScoreboardCriteria.class, SingletonArgumentInfo.a(ArgumentScoreboardCriteria::a));
      a(var0, "operation", ArgumentMathOperation.class, SingletonArgumentInfo.a(ArgumentMathOperation::a));
      a(var0, "particle", ArgumentParticle.class, SingletonArgumentInfo.a(ArgumentParticle::a));
      a(var0, "angle", ArgumentAngle.class, SingletonArgumentInfo.a(ArgumentAngle::a));
      a(var0, "rotation", ArgumentRotation.class, SingletonArgumentInfo.a(ArgumentRotation::a));
      a(var0, "scoreboard_slot", ArgumentScoreboardSlot.class, SingletonArgumentInfo.a(ArgumentScoreboardSlot::a));
      a(var0, "score_holder", ArgumentScoreholder.class, new ArgumentScoreholder.a());
      a(var0, "swizzle", ArgumentRotationAxis.class, SingletonArgumentInfo.a(ArgumentRotationAxis::a));
      a(var0, "team", ArgumentScoreboardTeam.class, SingletonArgumentInfo.a(ArgumentScoreboardTeam::a));
      a(var0, "item_slot", ArgumentInventorySlot.class, SingletonArgumentInfo.a(ArgumentInventorySlot::a));
      a(var0, "resource_location", ArgumentMinecraftKeyRegistered.class, SingletonArgumentInfo.a(ArgumentMinecraftKeyRegistered::a));
      a(var0, "function", ArgumentTag.class, SingletonArgumentInfo.a(ArgumentTag::a));
      a(var0, "entity_anchor", ArgumentAnchor.class, SingletonArgumentInfo.a(ArgumentAnchor::a));
      a(var0, "int_range", ArgumentCriterionValue.b.class, SingletonArgumentInfo.a(ArgumentCriterionValue::a));
      a(var0, "float_range", ArgumentCriterionValue.a.class, SingletonArgumentInfo.a(ArgumentCriterionValue::b));
      a(var0, "dimension", ArgumentDimension.class, SingletonArgumentInfo.a(ArgumentDimension::a));
      a(var0, "gamemode", GameModeArgument.class, SingletonArgumentInfo.a(GameModeArgument::a));
      a(var0, "time", ArgumentTime.class, new ArgumentTime.a());
      a(var0, "resource_or_tag", b(ResourceOrTagArgument.class), new ResourceOrTagArgument.a());
      a(var0, "resource_or_tag_key", b(ResourceOrTagKeyArgument.class), new ResourceOrTagKeyArgument.a());
      a(var0, "resource", b(ResourceArgument.class), new ResourceArgument.a());
      a(var0, "resource_key", b(ResourceKeyArgument.class), new ResourceKeyArgument.a());
      a(var0, "template_mirror", TemplateMirrorArgument.class, SingletonArgumentInfo.a(TemplateMirrorArgument::a));
      a(var0, "template_rotation", TemplateRotationArgument.class, SingletonArgumentInfo.a(TemplateRotationArgument::a));
      a(var0, "heightmap", HeightmapTypeArgument.class, SingletonArgumentInfo.a(HeightmapTypeArgument::a));
      if (SharedConstants.aO) {
         a(var0, "test_argument", GameTestHarnessTestFunctionArgument.class, SingletonArgumentInfo.a(GameTestHarnessTestFunctionArgument::a));
         a(var0, "test_class", GameTestHarnessTestClassArgument.class, SingletonArgumentInfo.a(GameTestHarnessTestClassArgument::a));
      }

      return a(var0, "uuid", ArgumentUUID.class, SingletonArgumentInfo.a(ArgumentUUID::a));
   }

   private static <T extends ArgumentType<?>> Class<T> b(Class<? super T> var0) {
      return (Class<T>)var0;
   }

   public static boolean a(Class<?> var0) {
      return a.containsKey(var0);
   }

   public static <A extends ArgumentType<?>> ArgumentTypeInfo<A, ?> a(A var0) {
      ArgumentTypeInfo<?, ?> var1 = a.get(var0.getClass());
      if (var1 == null) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "Unrecognized argument type %s (%s)", var0, var0.getClass()));
      } else {
         return var1;
      }
   }

   public static <A extends ArgumentType<?>> ArgumentTypeInfo.a<A> b(A var0) {
      return a(var0).a(var0);
   }
}
