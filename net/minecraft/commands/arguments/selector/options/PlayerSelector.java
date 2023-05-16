package net.minecraft.commands.arguments.selector.options;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.advancements.critereon.CriterionConditionRange;
import net.minecraft.advancements.critereon.CriterionConditionValue;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.selector.ArgumentParserSelector;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.MojangsonParser;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.AdvancementDataPlayer;
import net.minecraft.server.AdvancementDataWorld;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.ScoreboardScore;
import net.minecraft.world.scores.ScoreboardTeamBase;

public class PlayerSelector {
   private static final Map<String, PlayerSelector.b> i = Maps.newHashMap();
   public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("argument.entity.options.unknown", var0));
   public static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(
      var0 -> IChatBaseComponent.a("argument.entity.options.inapplicable", var0)
   );
   public static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.entity.options.distance.negative"));
   public static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.entity.options.level.negative"));
   public static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.entity.options.limit.toosmall"));
   public static final DynamicCommandExceptionType f = new DynamicCommandExceptionType(
      var0 -> IChatBaseComponent.a("argument.entity.options.sort.irreversible", var0)
   );
   public static final DynamicCommandExceptionType g = new DynamicCommandExceptionType(
      var0 -> IChatBaseComponent.a("argument.entity.options.mode.invalid", var0)
   );
   public static final DynamicCommandExceptionType h = new DynamicCommandExceptionType(
      var0 -> IChatBaseComponent.a("argument.entity.options.type.invalid", var0)
   );

   private static void a(String var0, PlayerSelector.a var1, Predicate<ArgumentParserSelector> var2, IChatBaseComponent var3) {
      i.put(var0, new PlayerSelector.b(var1, var2, var3));
   }

   public static void a() {
      if (i.isEmpty()) {
         a("name", var0 -> {
            int var1 = var0.g().getCursor();
            boolean var2 = var0.e();
            String var3 = var0.g().readString();
            if (var0.w() && !var2) {
               var0.g().setCursor(var1);
               throw b.createWithContext(var0.g(), "name");
            } else {
               if (var2) {
                  var0.c(true);
               } else {
                  var0.b(true);
               }

               var0.a(var2x -> var2x.Z().getString().equals(var3) != var2);
            }
         }, var0 -> !var0.v(), IChatBaseComponent.c("argument.entity.options.name.description"));
         a("distance", var0 -> {
            int var1 = var0.g().getCursor();
            CriterionConditionValue.DoubleRange var2 = CriterionConditionValue.DoubleRange.a(var0.g());
            if ((var2.a() == null || !(var2.a() < 0.0)) && (var2.b() == null || !(var2.b() < 0.0))) {
               var0.a(var2);
               var0.h();
            } else {
               var0.g().setCursor(var1);
               throw c.createWithContext(var0.g());
            }
         }, var0 -> var0.i().c(), IChatBaseComponent.c("argument.entity.options.distance.description"));
         a("level", var0 -> {
            int var1 = var0.g().getCursor();
            CriterionConditionValue.IntegerRange var2 = CriterionConditionValue.IntegerRange.a(var0.g());
            if ((var2.a() == null || var2.a() >= 0) && (var2.b() == null || var2.b() >= 0)) {
               var0.a(var2);
               var0.a(false);
            } else {
               var0.g().setCursor(var1);
               throw d.createWithContext(var0.g());
            }
         }, var0 -> var0.j().c(), IChatBaseComponent.c("argument.entity.options.level.description"));
         a("x", var0 -> {
            var0.h();
            var0.a(var0.g().readDouble());
         }, var0 -> var0.m() == null, IChatBaseComponent.c("argument.entity.options.x.description"));
         a("y", var0 -> {
            var0.h();
            var0.b(var0.g().readDouble());
         }, var0 -> var0.n() == null, IChatBaseComponent.c("argument.entity.options.y.description"));
         a("z", var0 -> {
            var0.h();
            var0.c(var0.g().readDouble());
         }, var0 -> var0.o() == null, IChatBaseComponent.c("argument.entity.options.z.description"));
         a("dx", var0 -> {
            var0.h();
            var0.d(var0.g().readDouble());
         }, var0 -> var0.p() == null, IChatBaseComponent.c("argument.entity.options.dx.description"));
         a("dy", var0 -> {
            var0.h();
            var0.e(var0.g().readDouble());
         }, var0 -> var0.q() == null, IChatBaseComponent.c("argument.entity.options.dy.description"));
         a("dz", var0 -> {
            var0.h();
            var0.f(var0.g().readDouble());
         }, var0 -> var0.r() == null, IChatBaseComponent.c("argument.entity.options.dz.description"));
         a(
            "x_rotation",
            var0 -> var0.a(CriterionConditionRange.a(var0.g(), true, MathHelper::g)),
            var0 -> var0.k() == CriterionConditionRange.a,
            IChatBaseComponent.c("argument.entity.options.x_rotation.description")
         );
         a(
            "y_rotation",
            var0 -> var0.b(CriterionConditionRange.a(var0.g(), true, MathHelper::g)),
            var0 -> var0.l() == CriterionConditionRange.a,
            IChatBaseComponent.c("argument.entity.options.y_rotation.description")
         );
         a("limit", var0 -> {
            int var1 = var0.g().getCursor();
            int var2 = var0.g().readInt();
            if (var2 < 1) {
               var0.g().setCursor(var1);
               throw e.createWithContext(var0.g());
            } else {
               var0.a(var2);
               var0.d(true);
            }
         }, var0 -> !var0.u() && !var0.x(), IChatBaseComponent.c("argument.entity.options.limit.description"));
         a("sort", var0 -> {
            int var1 = var0.g().getCursor();
            String var2 = var0.g().readUnquotedString();
            var0.a((var0x, var1x) -> ICompletionProvider.b(Arrays.asList("nearest", "furthest", "random", "arbitrary"), var0x));
            BiConsumer var10001;
            switch(var2) {
               case "nearest":
                  var10001 = ArgumentParserSelector.k;
                  break;
               case "furthest":
                  var10001 = ArgumentParserSelector.l;
                  break;
               case "random":
                  var10001 = ArgumentParserSelector.m;
                  break;
               case "arbitrary":
                  var10001 = EntitySelector.b;
                  break;
               default:
                  var0.g().setCursor(var1);
                  throw f.createWithContext(var0.g(), var2);
            }

            var0.a(var10001);
            var0.e(true);
         }, var0 -> !var0.u() && !var0.y(), IChatBaseComponent.c("argument.entity.options.sort.description"));
         a("gamemode", var0 -> {
            var0.a((var1x, var2x) -> {
               String var3x = var1x.getRemaining().toLowerCase(Locale.ROOT);
               boolean var4x = !var0.A();
               boolean var5 = true;
               if (!var3x.isEmpty()) {
                  if (var3x.charAt(0) == '!') {
                     var4x = false;
                     var3x = var3x.substring(1);
                  } else {
                     var5 = false;
                  }
               }

               for(EnumGamemode var9 : EnumGamemode.values()) {
                  if (var9.b().toLowerCase(Locale.ROOT).startsWith(var3x)) {
                     if (var5) {
                        var1x.suggest("!" + var9.b());
                     }

                     if (var4x) {
                        var1x.suggest(var9.b());
                     }
                  }
               }

               return var1x.buildFuture();
            });
            int var1 = var0.g().getCursor();
            boolean var2 = var0.e();
            if (var0.A() && !var2) {
               var0.g().setCursor(var1);
               throw b.createWithContext(var0.g(), "gamemode");
            } else {
               String var3 = var0.g().readUnquotedString();
               EnumGamemode var4 = EnumGamemode.a(var3, null);
               if (var4 == null) {
                  var0.g().setCursor(var1);
                  throw g.createWithContext(var0.g(), var3);
               } else {
                  var0.a(false);
                  var0.a(var2x -> {
                     if (!(var2x instanceof EntityPlayer)) {
                        return false;
                     } else {
                        EnumGamemode var3x = ((EntityPlayer)var2x).d.b();
                        return var2 ? var3x != var4 : var3x == var4;
                     }
                  });
                  if (var2) {
                     var0.g(true);
                  } else {
                     var0.f(true);
                  }
               }
            }
         }, var0 -> !var0.z(), IChatBaseComponent.c("argument.entity.options.gamemode.description"));
         a("team", var0 -> {
            boolean var1 = var0.e();
            String var2 = var0.g().readUnquotedString();
            var0.a(var2x -> {
               if (!(var2x instanceof EntityLiving)) {
                  return false;
               } else {
                  ScoreboardTeamBase var3 = var2x.cb();
                  String var4 = var3 == null ? "" : var3.b();
                  return var4.equals(var2) != var1;
               }
            });
            if (var1) {
               var0.i(true);
            } else {
               var0.h(true);
            }
         }, var0 -> !var0.B(), IChatBaseComponent.c("argument.entity.options.team.description"));
         a("type", var0 -> {
            var0.a((var1x, var2x) -> {
               ICompletionProvider.a(BuiltInRegistries.h.e(), var1x, String.valueOf('!'));
               ICompletionProvider.a(BuiltInRegistries.h.j().map(TagKey::b), var1x, "!#");
               if (!var0.F()) {
                  ICompletionProvider.a(BuiltInRegistries.h.e(), var1x);
                  ICompletionProvider.a(BuiltInRegistries.h.j().map(TagKey::b), var1x, String.valueOf('#'));
               }

               return var1x.buildFuture();
            });
            int var1 = var0.g().getCursor();
            boolean var2 = var0.e();
            if (var0.F() && !var2) {
               var0.g().setCursor(var1);
               throw b.createWithContext(var0.g(), "type");
            } else {
               if (var2) {
                  var0.D();
               }

               if (var0.f()) {
                  TagKey<EntityTypes<?>> var3 = TagKey.a(Registries.r, MinecraftKey.a(var0.g()));
                  var0.a(var2x -> var2x.ae().a(var3) != var2);
               } else {
                  MinecraftKey var3 = MinecraftKey.a(var0.g());
                  EntityTypes<?> var4 = BuiltInRegistries.h.b(var3).orElseThrow(() -> {
                     var0.g().setCursor(var1);
                     return h.createWithContext(var0.g(), var3.toString());
                  });
                  if (Objects.equals(EntityTypes.bt, var4) && !var2) {
                     var0.a(false);
                  }

                  var0.a(var2x -> Objects.equals(var4, var2x.ae()) != var2);
                  if (!var2) {
                     var0.a(var4);
                  }
               }
            }
         }, var0 -> !var0.E(), IChatBaseComponent.c("argument.entity.options.type.description"));
         a("tag", var0 -> {
            boolean var1 = var0.e();
            String var2 = var0.g().readUnquotedString();
            var0.a(var2x -> {
               if ("".equals(var2)) {
                  return var2x.ag().isEmpty() != var1;
               } else {
                  return var2x.ag().contains(var2) != var1;
               }
            });
         }, var0 -> true, IChatBaseComponent.c("argument.entity.options.tag.description"));
         a("nbt", var0 -> {
            boolean var1 = var0.e();
            NBTTagCompound var2 = new MojangsonParser(var0.g()).f();
            var0.a(var2x -> {
               NBTTagCompound var3 = var2x.f(new NBTTagCompound());
               if (var2x instanceof EntityPlayer) {
                  ItemStack var4 = ((EntityPlayer)var2x).fJ().f();
                  if (!var4.b()) {
                     var3.a("SelectedItem", var4.b(new NBTTagCompound()));
                  }
               }

               return GameProfileSerializer.a(var2, var3, true) != var1;
            });
         }, var0 -> true, IChatBaseComponent.c("argument.entity.options.nbt.description"));
         a("scores", var0 -> {
            StringReader var1 = var0.g();
            Map<String, CriterionConditionValue.IntegerRange> var2 = Maps.newHashMap();
            var1.expect('{');
            var1.skipWhitespace();

            while(var1.canRead() && var1.peek() != '}') {
               var1.skipWhitespace();
               String var3 = var1.readUnquotedString();
               var1.skipWhitespace();
               var1.expect('=');
               var1.skipWhitespace();
               CriterionConditionValue.IntegerRange var4 = CriterionConditionValue.IntegerRange.a(var1);
               var2.put(var3, var4);
               var1.skipWhitespace();
               if (var1.canRead() && var1.peek() == ',') {
                  var1.skip();
               }
            }

            var1.expect('}');
            if (!var2.isEmpty()) {
               var0.a(var1x -> {
                  Scoreboard var2x = var1x.cH().aF();
                  String var3x = var1x.cu();

                  for(Entry<String, CriterionConditionValue.IntegerRange> var5 : var2.entrySet()) {
                     ScoreboardObjective var6 = var2x.d(var5.getKey());
                     if (var6 == null) {
                        return false;
                     }

                     if (!var2x.b(var3x, var6)) {
                        return false;
                     }

                     ScoreboardScore var7 = var2x.c(var3x, var6);
                     int var8 = var7.b();
                     if (!var5.getValue().d(var8)) {
                        return false;
                     }
                  }

                  return true;
               });
            }

            var0.j(true);
         }, var0 -> !var0.G(), IChatBaseComponent.c("argument.entity.options.scores.description"));
         a("advancements", var0 -> {
            StringReader var1 = var0.g();
            Map<MinecraftKey, Predicate<AdvancementProgress>> var2 = Maps.newHashMap();
            var1.expect('{');
            var1.skipWhitespace();

            while(var1.canRead() && var1.peek() != '}') {
               var1.skipWhitespace();
               MinecraftKey var3 = MinecraftKey.a(var1);
               var1.skipWhitespace();
               var1.expect('=');
               var1.skipWhitespace();
               if (var1.canRead() && var1.peek() == '{') {
                  Map<String, Predicate<CriterionProgress>> var4 = Maps.newHashMap();
                  var1.skipWhitespace();
                  var1.expect('{');
                  var1.skipWhitespace();

                  while(var1.canRead() && var1.peek() != '}') {
                     var1.skipWhitespace();
                     String var5 = var1.readUnquotedString();
                     var1.skipWhitespace();
                     var1.expect('=');
                     var1.skipWhitespace();
                     boolean var6 = var1.readBoolean();
                     var4.put(var5, var1x -> var1x.a() == var6);
                     var1.skipWhitespace();
                     if (var1.canRead() && var1.peek() == ',') {
                        var1.skip();
                     }
                  }

                  var1.skipWhitespace();
                  var1.expect('}');
                  var1.skipWhitespace();
                  var2.put(var3, var1x -> {
                     for(Entry<String, Predicate<CriterionProgress>> var3x : var4.entrySet()) {
                        CriterionProgress var4x = var1x.c((String)var3x.getKey());
                        if (var4x == null || !((Predicate)var3x.getValue()).test(var4x)) {
                           return false;
                        }
                     }

                     return true;
                  });
               } else {
                  boolean var4 = var1.readBoolean();
                  var2.put(var3, var1x -> var1x.a() == var4);
               }

               var1.skipWhitespace();
               if (var1.canRead() && var1.peek() == ',') {
                  var1.skip();
               }
            }

            var1.expect('}');
            if (!var2.isEmpty()) {
               var0.a(var1x -> {
                  if (!(var1x instanceof EntityPlayer)) {
                     return false;
                  } else {
                     EntityPlayer var2x = (EntityPlayer)var1x;
                     AdvancementDataPlayer var3x = var2x.M();
                     AdvancementDataWorld var4x = var2x.cH().az();

                     for(Entry<MinecraftKey, Predicate<AdvancementProgress>> var6x : var2.entrySet()) {
                        Advancement var7x = var4x.a((MinecraftKey)var6x.getKey());
                        if (var7x == null || !((Predicate)var6x.getValue()).test(var3x.b(var7x))) {
                           return false;
                        }
                     }

                     return true;
                  }
               });
               var0.a(false);
            }

            var0.k(true);
         }, var0 -> !var0.H(), IChatBaseComponent.c("argument.entity.options.advancements.description"));
         a(
            "predicate",
            var0 -> {
               boolean var1 = var0.e();
               MinecraftKey var2 = MinecraftKey.a(var0.g());
               var0.a(
                  var2x -> {
                     if (!(var2x.H instanceof WorldServer)) {
                        return false;
                     } else {
                        WorldServer var3 = (WorldServer)var2x.H;
                        LootItemCondition var4 = var3.n().aI().a(var2);
                        if (var4 == null) {
                           return false;
                        } else {
                           LootTableInfo var5 = new LootTableInfo.Builder(var3)
                              .a(LootContextParameters.a, var2x)
                              .a(LootContextParameters.f, var2x.de())
                              .a(LootContextParameterSets.d);
                           return var1 ^ var4.test(var5);
                        }
                     }
                  }
               );
            },
            var0 -> true,
            IChatBaseComponent.c("argument.entity.options.predicate.description")
         );
      }
   }

   public static PlayerSelector.a a(ArgumentParserSelector var0, String var1, int var2) throws CommandSyntaxException {
      PlayerSelector.b var3 = i.get(var1);
      if (var3 != null) {
         if (var3.b.test(var0)) {
            return var3.a;
         } else {
            throw b.createWithContext(var0.g(), var1);
         }
      } else {
         var0.g().setCursor(var2);
         throw a.createWithContext(var0.g(), var1);
      }
   }

   public static void a(ArgumentParserSelector var0, SuggestionsBuilder var1) {
      String var2 = var1.getRemaining().toLowerCase(Locale.ROOT);

      for(Entry<String, PlayerSelector.b> var4 : i.entrySet()) {
         if (var4.getValue().b.test(var0) && var4.getKey().toLowerCase(Locale.ROOT).startsWith(var2)) {
            var1.suggest((String)var4.getKey() + "=", var4.getValue().c);
         }
      }
   }

   public interface a {
      void handle(ArgumentParserSelector var1) throws CommandSyntaxException;
   }

   static record b(PlayerSelector.a modifier, Predicate<ArgumentParserSelector> canUse, IChatBaseComponent description) {
      final PlayerSelector.a a;
      final Predicate<ArgumentParserSelector> b;
      final IChatBaseComponent c;

      b(PlayerSelector.a var0, Predicate<ArgumentParserSelector> var1, IChatBaseComponent var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }
   }
}
