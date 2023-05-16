package net.minecraft.world.level;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicLike;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandDispatcher;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayOutEntityStatus;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import org.slf4j.Logger;

public class GameRules {
   public static final int a = 3;
   static final Logger U = LogUtils.getLogger();
   private static final Map<GameRules.GameRuleKey<?>, GameRules.GameRuleDefinition<?>> V = Maps.newTreeMap(
      Comparator.comparing(gamerules_gamerulekey -> gamerules_gamerulekey.a)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> b = a("doFireTick", GameRules.GameRuleCategory.e, GameRules.GameRuleBoolean.a(true));
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> c = a("mobGriefing", GameRules.GameRuleCategory.b, GameRules.GameRuleBoolean.a(true));
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> d = a(
      "keepInventory", GameRules.GameRuleCategory.a, GameRules.GameRuleBoolean.a(false)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> e = a("doMobSpawning", GameRules.GameRuleCategory.c, GameRules.GameRuleBoolean.a(true));
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> f = a("doMobLoot", GameRules.GameRuleCategory.d, GameRules.GameRuleBoolean.a(true));
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> g = a("doTileDrops", GameRules.GameRuleCategory.d, GameRules.GameRuleBoolean.a(true));
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> h = a("doEntityDrops", GameRules.GameRuleCategory.d, GameRules.GameRuleBoolean.a(true));
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> i = a(
      "commandBlockOutput", GameRules.GameRuleCategory.f, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> j = a(
      "naturalRegeneration", GameRules.GameRuleCategory.a, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> k = a(
      "doDaylightCycle", GameRules.GameRuleCategory.e, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> l = a(
      "logAdminCommands", GameRules.GameRuleCategory.f, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> m = a(
      "showDeathMessages", GameRules.GameRuleCategory.f, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleInt> n = a("randomTickSpeed", GameRules.GameRuleCategory.e, GameRules.GameRuleInt.a(3));
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> o = a(
      "sendCommandFeedback", GameRules.GameRuleCategory.f, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> p = a(
      "reducedDebugInfo", GameRules.GameRuleCategory.g, GameRules.GameRuleBoolean.a(false, (minecraftserver, gamerules_gameruleboolean) -> {
         int i = gamerules_gameruleboolean.a() ? 22 : 23;
   
         for(EntityPlayer entityplayer : minecraftserver.ac().t()) {
            entityplayer.b.a(new PacketPlayOutEntityStatus(entityplayer, (byte)i));
         }
      })
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> q = a(
      "spectatorsGenerateChunks", GameRules.GameRuleCategory.a, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleInt> r = a("spawnRadius", GameRules.GameRuleCategory.a, GameRules.GameRuleInt.a(10));
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> s = a(
      "disableElytraMovementCheck", GameRules.GameRuleCategory.a, GameRules.GameRuleBoolean.a(false)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleInt> t = a("maxEntityCramming", GameRules.GameRuleCategory.b, GameRules.GameRuleInt.a(24));
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> u = a(
      "doWeatherCycle", GameRules.GameRuleCategory.e, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> v = a(
      "doLimitedCrafting", GameRules.GameRuleCategory.a, GameRules.GameRuleBoolean.a(false)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleInt> w = a(
      "maxCommandChainLength", GameRules.GameRuleCategory.g, GameRules.GameRuleInt.a(65536)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleInt> x = a(
      "commandModificationBlockLimit", GameRules.GameRuleCategory.g, GameRules.GameRuleInt.a(32768)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> y = a(
      "announceAdvancements", GameRules.GameRuleCategory.f, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> z = a("disableRaids", GameRules.GameRuleCategory.b, GameRules.GameRuleBoolean.a(false));
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> A = a("doInsomnia", GameRules.GameRuleCategory.c, GameRules.GameRuleBoolean.a(true));
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> B = a(
      "doImmediateRespawn", GameRules.GameRuleCategory.a, GameRules.GameRuleBoolean.a(false, (minecraftserver, gamerules_gameruleboolean) -> {
         for(EntityPlayer entityplayer : minecraftserver.ac().t()) {
            entityplayer.b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.l, gamerules_gameruleboolean.a() ? 1.0F : 0.0F));
         }
      })
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> C = a(
      "drowningDamage", GameRules.GameRuleCategory.a, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> D = a("fallDamage", GameRules.GameRuleCategory.a, GameRules.GameRuleBoolean.a(true));
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> E = a("fireDamage", GameRules.GameRuleCategory.a, GameRules.GameRuleBoolean.a(true));
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> F = a("freezeDamage", GameRules.GameRuleCategory.a, GameRules.GameRuleBoolean.a(true));
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> G = a(
      "doPatrolSpawning", GameRules.GameRuleCategory.c, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> H = a(
      "doTraderSpawning", GameRules.GameRuleCategory.c, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> I = a(
      "doWardenSpawning", GameRules.GameRuleCategory.c, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> J = a(
      "forgiveDeadPlayers", GameRules.GameRuleCategory.b, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> K = a(
      "universalAnger", GameRules.GameRuleCategory.b, GameRules.GameRuleBoolean.a(false)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleInt> L = a(
      "playersSleepingPercentage", GameRules.GameRuleCategory.a, GameRules.GameRuleInt.a(100)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> M = a(
      "blockExplosionDropDecay", GameRules.GameRuleCategory.d, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> N = a(
      "mobExplosionDropDecay", GameRules.GameRuleCategory.d, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> O = a(
      "tntExplosionDropDecay", GameRules.GameRuleCategory.d, GameRules.GameRuleBoolean.a(false)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleInt> P = a("snowAccumulationHeight", GameRules.GameRuleCategory.e, GameRules.GameRuleInt.a(1));
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> Q = a(
      "waterSourceConversion", GameRules.GameRuleCategory.e, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> R = a(
      "lavaSourceConversion", GameRules.GameRuleCategory.e, GameRules.GameRuleBoolean.a(false)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> S = a(
      "globalSoundEvents", GameRules.GameRuleCategory.g, GameRules.GameRuleBoolean.a(true)
   );
   public static final GameRules.GameRuleKey<GameRules.GameRuleBoolean> T = a("doVinesSpread", GameRules.GameRuleCategory.e, GameRules.GameRuleBoolean.a(true));
   private final Map<GameRules.GameRuleKey<?>, GameRules.GameRuleValue<?>> W;

   private static <T extends GameRules.GameRuleValue<T>> GameRules.GameRuleKey<T> a(
      String s, GameRules.GameRuleCategory gamerules_gamerulecategory, GameRules.GameRuleDefinition<T> gamerules_gameruledefinition
   ) {
      GameRules.GameRuleKey<T> gamerules_gamerulekey = new GameRules.GameRuleKey<>(s, gamerules_gamerulecategory);
      GameRules.GameRuleDefinition<?> gamerules_gameruledefinition1 = V.put(gamerules_gamerulekey, gamerules_gameruledefinition);
      if (gamerules_gameruledefinition1 != null) {
         throw new IllegalStateException("Duplicate game rule registration for " + s);
      } else {
         return gamerules_gamerulekey;
      }
   }

   public GameRules(DynamicLike<?> dynamiclike) {
      this();
      this.a(dynamiclike);
   }

   public GameRules() {
      this.W = V.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, entry -> ((GameRules.GameRuleDefinition)entry.getValue()).a()));
   }

   private GameRules(Map<GameRules.GameRuleKey<?>, GameRules.GameRuleValue<?>> map) {
      this.W = map;
   }

   public <T extends GameRules.GameRuleValue<T>> T a(GameRules.GameRuleKey<T> gamerules_gamerulekey) {
      return (T)this.W.get(gamerules_gamerulekey);
   }

   public NBTTagCompound a() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      this.W.forEach((gamerules_gamerulekey, gamerules_gamerulevalue) -> nbttagcompound.a(gamerules_gamerulekey.a, gamerules_gamerulevalue.b()));
      return nbttagcompound;
   }

   private void a(DynamicLike<?> dynamiclike) {
      this.W.forEach((gamerules_gamerulekey, gamerules_gamerulevalue) -> {
         Optional<String> optional = dynamiclike.get(gamerules_gamerulekey.a).asString().result();
         optional.ifPresent(gamerules_gamerulevalue::a);
      });
   }

   public GameRules b() {
      return new GameRules(
         this.W.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, entry -> ((GameRules.GameRuleValue)entry.getValue()).f()))
      );
   }

   public static void a(GameRules.GameRuleVisitor gamerules_gamerulevisitor) {
      V.forEach((gamerules_gamerulekey, gamerules_gameruledefinition) -> a(gamerules_gamerulevisitor, gamerules_gamerulekey, gamerules_gameruledefinition));
   }

   private static <T extends GameRules.GameRuleValue<T>> void a(
      GameRules.GameRuleVisitor gamerules_gamerulevisitor,
      GameRules.GameRuleKey<?> gamerules_gamerulekey,
      GameRules.GameRuleDefinition<?> gamerules_gameruledefinition
   ) {
      gamerules_gamerulevisitor.a(gamerules_gamerulekey, gamerules_gameruledefinition);
      gamerules_gameruledefinition.a(gamerules_gamerulevisitor, gamerules_gamerulekey);
   }

   public void a(GameRules gamerules, @Nullable MinecraftServer minecraftserver) {
      gamerules.W.keySet().forEach(gamerules_gamerulekey -> this.a(gamerules_gamerulekey, gamerules, minecraftserver));
   }

   private <T extends GameRules.GameRuleValue<T>> void a(
      GameRules.GameRuleKey<T> gamerules_gamerulekey, GameRules gamerules, @Nullable MinecraftServer minecraftserver
   ) {
      T t0 = gamerules.a(gamerules_gamerulekey);
      this.<T>a(gamerules_gamerulekey).a(t0, minecraftserver);
   }

   public boolean b(GameRules.GameRuleKey<GameRules.GameRuleBoolean> gamerules_gamerulekey) {
      return this.a(gamerules_gamerulekey).a();
   }

   public int c(GameRules.GameRuleKey<GameRules.GameRuleInt> gamerules_gamerulekey) {
      return this.a(gamerules_gamerulekey).a();
   }

   public static class GameRuleBoolean extends GameRules.GameRuleValue<GameRules.GameRuleBoolean> {
      private boolean b;

      static GameRules.GameRuleDefinition<GameRules.GameRuleBoolean> a(boolean flag, BiConsumer<MinecraftServer, GameRules.GameRuleBoolean> biconsumer) {
         return new GameRules.GameRuleDefinition<>(
            BoolArgumentType::bool,
            gamerules_gameruledefinition -> new GameRules.GameRuleBoolean(gamerules_gameruledefinition, flag),
            biconsumer,
            GameRules.GameRuleVisitor::b
         );
      }

      static GameRules.GameRuleDefinition<GameRules.GameRuleBoolean> a(boolean flag) {
         return a(flag, (minecraftserver, gamerules_gameruleboolean) -> {
         });
      }

      public GameRuleBoolean(GameRules.GameRuleDefinition<GameRules.GameRuleBoolean> gamerules_gameruledefinition, boolean flag) {
         super(gamerules_gameruledefinition);
         this.b = flag;
      }

      @Override
      protected void a(CommandContext<CommandListenerWrapper> commandcontext, String s) {
         this.b = BoolArgumentType.getBool(commandcontext, s);
      }

      public boolean a() {
         return this.b;
      }

      public void a(boolean flag, @Nullable MinecraftServer minecraftserver) {
         this.b = flag;
         this.a(minecraftserver);
      }

      @Override
      public String b() {
         return Boolean.toString(this.b);
      }

      @Override
      public void a(String s) {
         this.b = Boolean.parseBoolean(s);
      }

      @Override
      public int c() {
         return this.b ? 1 : 0;
      }

      protected GameRules.GameRuleBoolean d() {
         return this;
      }

      protected GameRules.GameRuleBoolean e() {
         return new GameRules.GameRuleBoolean(this.a, this.b);
      }

      public void a(GameRules.GameRuleBoolean gamerules_gameruleboolean, @Nullable MinecraftServer minecraftserver) {
         this.b = gamerules_gameruleboolean.b;
         this.a(minecraftserver);
      }
   }

   public static enum GameRuleCategory {
      a("gamerule.category.player"),
      b("gamerule.category.mobs"),
      c("gamerule.category.spawning"),
      d("gamerule.category.drops"),
      e("gamerule.category.updates"),
      f("gamerule.category.chat"),
      g("gamerule.category.misc");

      private final String h;

      private GameRuleCategory(String s) {
         this.h = s;
      }

      public String a() {
         return this.h;
      }
   }

   public static class GameRuleDefinition<T extends GameRules.GameRuleValue<T>> {
      private final Supplier<ArgumentType<?>> a;
      private final Function<GameRules.GameRuleDefinition<T>, T> b;
      final BiConsumer<MinecraftServer, T> c;
      private final GameRules.h<T> d;

      GameRuleDefinition(
         Supplier<ArgumentType<?>> supplier,
         Function<GameRules.GameRuleDefinition<T>, T> function,
         BiConsumer<MinecraftServer, T> biconsumer,
         GameRules.h<T> gamerules_h
      ) {
         this.a = supplier;
         this.b = function;
         this.c = biconsumer;
         this.d = gamerules_h;
      }

      public RequiredArgumentBuilder<CommandListenerWrapper, ?> a(String s) {
         return CommandDispatcher.a(s, (ArgumentType<T>)this.a.get());
      }

      public T a() {
         return this.b.apply(this);
      }

      public void a(GameRules.GameRuleVisitor gamerules_gamerulevisitor, GameRules.GameRuleKey<T> gamerules_gamerulekey) {
         this.d.call(gamerules_gamerulevisitor, gamerules_gamerulekey, this);
      }
   }

   public static class GameRuleInt extends GameRules.GameRuleValue<GameRules.GameRuleInt> {
      private int b;

      private static GameRules.GameRuleDefinition<GameRules.GameRuleInt> a(int i, BiConsumer<MinecraftServer, GameRules.GameRuleInt> biconsumer) {
         return new GameRules.GameRuleDefinition<>(
            IntegerArgumentType::integer,
            gamerules_gameruledefinition -> new GameRules.GameRuleInt(gamerules_gameruledefinition, i),
            biconsumer,
            GameRules.GameRuleVisitor::c
         );
      }

      static GameRules.GameRuleDefinition<GameRules.GameRuleInt> a(int i) {
         return a(i, (minecraftserver, gamerules_gameruleint) -> {
         });
      }

      public GameRuleInt(GameRules.GameRuleDefinition<GameRules.GameRuleInt> gamerules_gameruledefinition, int i) {
         super(gamerules_gameruledefinition);
         this.b = i;
      }

      @Override
      protected void a(CommandContext<CommandListenerWrapper> commandcontext, String s) {
         this.b = IntegerArgumentType.getInteger(commandcontext, s);
      }

      public int a() {
         return this.b;
      }

      public void a(int i, @Nullable MinecraftServer minecraftserver) {
         this.b = i;
         this.a(minecraftserver);
      }

      @Override
      public String b() {
         return Integer.toString(this.b);
      }

      @Override
      public void a(String s) {
         this.b = c(s);
      }

      public boolean b(String s) {
         try {
            this.b = Integer.parseInt(s);
            return true;
         } catch (NumberFormatException var3) {
            return false;
         }
      }

      private static int c(String s) {
         if (!s.isEmpty()) {
            try {
               return Integer.parseInt(s);
            } catch (NumberFormatException var2) {
               GameRules.U.warn("Failed to parse integer {}", s);
            }
         }

         return 0;
      }

      @Override
      public int c() {
         return this.b;
      }

      protected GameRules.GameRuleInt d() {
         return this;
      }

      protected GameRules.GameRuleInt e() {
         return new GameRules.GameRuleInt(this.a, this.b);
      }

      public void a(GameRules.GameRuleInt gamerules_gameruleint, @Nullable MinecraftServer minecraftserver) {
         this.b = gamerules_gameruleint.b;
         this.a(minecraftserver);
      }
   }

   public static final class GameRuleKey<T extends GameRules.GameRuleValue<T>> {
      final String a;
      private final GameRules.GameRuleCategory b;

      public GameRuleKey(String s, GameRules.GameRuleCategory gamerules_gamerulecategory) {
         this.a = s;
         this.b = gamerules_gamerulecategory;
      }

      @Override
      public String toString() {
         return this.a;
      }

      @Override
      public boolean equals(Object object) {
         return this == object ? true : object instanceof GameRules.GameRuleKey && ((GameRules.GameRuleKey)object).a.equals(this.a);
      }

      @Override
      public int hashCode() {
         return this.a.hashCode();
      }

      public String a() {
         return this.a;
      }

      public String b() {
         return "gamerule." + this.a;
      }

      public GameRules.GameRuleCategory c() {
         return this.b;
      }
   }

   public abstract static class GameRuleValue<T extends GameRules.GameRuleValue<T>> {
      protected final GameRules.GameRuleDefinition<T> a;

      public GameRuleValue(GameRules.GameRuleDefinition<T> gamerules_gameruledefinition) {
         this.a = gamerules_gameruledefinition;
      }

      protected abstract void a(CommandContext<CommandListenerWrapper> var1, String var2);

      public void b(CommandContext<CommandListenerWrapper> commandcontext, String s) {
         this.a(commandcontext, s);
         this.a(((CommandListenerWrapper)commandcontext.getSource()).l());
      }

      public void a(@Nullable MinecraftServer minecraftserver) {
         if (minecraftserver != null) {
            this.a.c.accept(minecraftserver, this.g());
         }
      }

      public abstract void a(String var1);

      public abstract String b();

      @Override
      public String toString() {
         return this.b();
      }

      public abstract int c();

      protected abstract T g();

      protected abstract T f();

      public abstract void a(T var1, @Nullable MinecraftServer var2);
   }

   public interface GameRuleVisitor {
      default <T extends GameRules.GameRuleValue<T>> void a(
         GameRules.GameRuleKey<T> gamerules_gamerulekey, GameRules.GameRuleDefinition<T> gamerules_gameruledefinition
      ) {
      }

      default void b(
         GameRules.GameRuleKey<GameRules.GameRuleBoolean> gamerules_gamerulekey,
         GameRules.GameRuleDefinition<GameRules.GameRuleBoolean> gamerules_gameruledefinition
      ) {
      }

      default void c(
         GameRules.GameRuleKey<GameRules.GameRuleInt> gamerules_gamerulekey, GameRules.GameRuleDefinition<GameRules.GameRuleInt> gamerules_gameruledefinition
      ) {
      }
   }

   private interface h<T extends GameRules.GameRuleValue<T>> {
      void call(GameRules.GameRuleVisitor var1, GameRules.GameRuleKey<T> var2, GameRules.GameRuleDefinition<T> var3);
   }
}
