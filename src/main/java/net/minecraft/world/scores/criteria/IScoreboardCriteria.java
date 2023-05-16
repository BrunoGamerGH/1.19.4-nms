package net.minecraft.world.scores.criteria;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.EnumChatFormat;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.stats.StatisticWrapper;
import net.minecraft.util.INamable;

public class IScoreboardCriteria {
   private static final Map<String, IScoreboardCriteria> n = Maps.newHashMap();
   public static final Map<String, IScoreboardCriteria> o = Maps.newHashMap();
   public static final IScoreboardCriteria a = b("dummy");
   public static final IScoreboardCriteria b = b("trigger");
   public static final IScoreboardCriteria c = b("deathCount");
   public static final IScoreboardCriteria d = b("playerKillCount");
   public static final IScoreboardCriteria e = b("totalKillCount");
   public static final IScoreboardCriteria f = a("health", true, IScoreboardCriteria.EnumScoreboardHealthDisplay.b);
   public static final IScoreboardCriteria g = a("food", true, IScoreboardCriteria.EnumScoreboardHealthDisplay.a);
   public static final IScoreboardCriteria h = a("air", true, IScoreboardCriteria.EnumScoreboardHealthDisplay.a);
   public static final IScoreboardCriteria i = a("armor", true, IScoreboardCriteria.EnumScoreboardHealthDisplay.a);
   public static final IScoreboardCriteria j = a("xp", true, IScoreboardCriteria.EnumScoreboardHealthDisplay.a);
   public static final IScoreboardCriteria k = a("level", true, IScoreboardCriteria.EnumScoreboardHealthDisplay.a);
   public static final IScoreboardCriteria[] l = new IScoreboardCriteria[]{
      b("teamkill." + EnumChatFormat.a.g()),
      b("teamkill." + EnumChatFormat.b.g()),
      b("teamkill." + EnumChatFormat.c.g()),
      b("teamkill." + EnumChatFormat.d.g()),
      b("teamkill." + EnumChatFormat.e.g()),
      b("teamkill." + EnumChatFormat.f.g()),
      b("teamkill." + EnumChatFormat.g.g()),
      b("teamkill." + EnumChatFormat.h.g()),
      b("teamkill." + EnumChatFormat.i.g()),
      b("teamkill." + EnumChatFormat.j.g()),
      b("teamkill." + EnumChatFormat.k.g()),
      b("teamkill." + EnumChatFormat.l.g()),
      b("teamkill." + EnumChatFormat.m.g()),
      b("teamkill." + EnumChatFormat.n.g()),
      b("teamkill." + EnumChatFormat.o.g()),
      b("teamkill." + EnumChatFormat.p.g())
   };
   public static final IScoreboardCriteria[] m = new IScoreboardCriteria[]{
      b("killedByTeam." + EnumChatFormat.a.g()),
      b("killedByTeam." + EnumChatFormat.b.g()),
      b("killedByTeam." + EnumChatFormat.c.g()),
      b("killedByTeam." + EnumChatFormat.d.g()),
      b("killedByTeam." + EnumChatFormat.e.g()),
      b("killedByTeam." + EnumChatFormat.f.g()),
      b("killedByTeam." + EnumChatFormat.g.g()),
      b("killedByTeam." + EnumChatFormat.h.g()),
      b("killedByTeam." + EnumChatFormat.i.g()),
      b("killedByTeam." + EnumChatFormat.j.g()),
      b("killedByTeam." + EnumChatFormat.k.g()),
      b("killedByTeam." + EnumChatFormat.l.g()),
      b("killedByTeam." + EnumChatFormat.m.g()),
      b("killedByTeam." + EnumChatFormat.n.g()),
      b("killedByTeam." + EnumChatFormat.o.g()),
      b("killedByTeam." + EnumChatFormat.p.g())
   };
   private final String p;
   private final boolean q;
   private final IScoreboardCriteria.EnumScoreboardHealthDisplay r;

   private static IScoreboardCriteria a(String var0, boolean var1, IScoreboardCriteria.EnumScoreboardHealthDisplay var2) {
      IScoreboardCriteria var3 = new IScoreboardCriteria(var0, var1, var2);
      n.put(var0, var3);
      return var3;
   }

   private static IScoreboardCriteria b(String var0) {
      return a(var0, false, IScoreboardCriteria.EnumScoreboardHealthDisplay.a);
   }

   protected IScoreboardCriteria(String var0) {
      this(var0, false, IScoreboardCriteria.EnumScoreboardHealthDisplay.a);
   }

   protected IScoreboardCriteria(String var0, boolean var1, IScoreboardCriteria.EnumScoreboardHealthDisplay var2) {
      this.p = var0;
      this.q = var1;
      this.r = var2;
      o.put(var0, this);
   }

   public static Set<String> c() {
      return ImmutableSet.copyOf(n.keySet());
   }

   public static Optional<IScoreboardCriteria> a(String var0) {
      IScoreboardCriteria var1 = o.get(var0);
      if (var1 != null) {
         return Optional.of(var1);
      } else {
         int var2 = var0.indexOf(58);
         return var2 < 0
            ? Optional.empty()
            : BuiltInRegistries.x.b(MinecraftKey.a(var0.substring(0, var2), '.')).flatMap(var2x -> a(var2x, MinecraftKey.a(var0.substring(var2 + 1), '.')));
      }
   }

   private static <T> Optional<IScoreboardCriteria> a(StatisticWrapper<T> var0, MinecraftKey var1) {
      return var0.a().b(var1).map(var0::b);
   }

   public String d() {
      return this.p;
   }

   public boolean e() {
      return this.q;
   }

   public IScoreboardCriteria.EnumScoreboardHealthDisplay f() {
      return this.r;
   }

   public static enum EnumScoreboardHealthDisplay implements INamable {
      a("integer"),
      b("hearts");

      private final String d;
      public static final INamable.a<IScoreboardCriteria.EnumScoreboardHealthDisplay> c = INamable.a(IScoreboardCriteria.EnumScoreboardHealthDisplay::values);

      private EnumScoreboardHealthDisplay(String var2) {
         this.d = var2;
      }

      public String a() {
         return this.d;
      }

      @Override
      public String c() {
         return this.d;
      }

      public static IScoreboardCriteria.EnumScoreboardHealthDisplay a(String var0) {
         return c.a(var0, a);
      }
   }
}
