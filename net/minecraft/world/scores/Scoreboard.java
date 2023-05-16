package net.minecraft.world.scores;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;
import org.slf4j.Logger;

public class Scoreboard {
   private static final Logger g = LogUtils.getLogger();
   public static final int a = 0;
   public static final int b = 1;
   public static final int c = 2;
   public static final int d = 3;
   public static final int e = 18;
   public static final int f = 19;
   private final Map<String, ScoreboardObjective> h = Maps.newHashMap();
   private final Map<IScoreboardCriteria, List<ScoreboardObjective>> i = Maps.newHashMap();
   private final Map<String, Map<ScoreboardObjective, ScoreboardScore>> j = Maps.newHashMap();
   private final ScoreboardObjective[] k = new ScoreboardObjective[19];
   private final Map<String, ScoreboardTeam> l = Maps.newHashMap();
   private final Map<String, ScoreboardTeam> m = Maps.newHashMap();
   @Nullable
   private static String[] n;

   public boolean b(String var0) {
      return this.h.containsKey(var0);
   }

   public ScoreboardObjective c(String var0) {
      return this.h.get(var0);
   }

   @Nullable
   public ScoreboardObjective d(@Nullable String var0) {
      return this.h.get(var0);
   }

   public ScoreboardObjective a(String var0, IScoreboardCriteria var1, IChatBaseComponent var2, IScoreboardCriteria.EnumScoreboardHealthDisplay var3) {
      if (this.h.containsKey(var0)) {
         throw new IllegalArgumentException("An objective with the name '" + var0 + "' already exists!");
      } else {
         ScoreboardObjective var4 = new ScoreboardObjective(this, var0, var1, var2, var3);
         this.i.computeIfAbsent(var1, var0x -> Lists.newArrayList()).add(var4);
         this.h.put(var0, var4);
         this.a(var4);
         return var4;
      }
   }

   public final void a(IScoreboardCriteria var0, String var1, Consumer<ScoreboardScore> var2) {
      this.i.getOrDefault(var0, Collections.emptyList()).forEach(var2x -> var2.accept(this.c(var1, var2x)));
   }

   public boolean b(String var0, ScoreboardObjective var1) {
      Map<ScoreboardObjective, ScoreboardScore> var2 = this.j.get(var0);
      if (var2 == null) {
         return false;
      } else {
         ScoreboardScore var3 = var2.get(var1);
         return var3 != null;
      }
   }

   public ScoreboardScore c(String var0, ScoreboardObjective var1) {
      Map<ScoreboardObjective, ScoreboardScore> var2 = this.j.computeIfAbsent(var0, var0x -> Maps.newHashMap());
      return var2.computeIfAbsent(var1, var1x -> {
         ScoreboardScore var2x = new ScoreboardScore(this, var1x, var0);
         var2x.b(0);
         return var2x;
      });
   }

   public Collection<ScoreboardScore> i(ScoreboardObjective var0) {
      List<ScoreboardScore> var1 = Lists.newArrayList();

      for(Map<ScoreboardObjective, ScoreboardScore> var3 : this.j.values()) {
         ScoreboardScore var4 = var3.get(var0);
         if (var4 != null) {
            var1.add(var4);
         }
      }

      var1.sort(ScoreboardScore.a);
      return var1;
   }

   public Collection<ScoreboardObjective> c() {
      return this.h.values();
   }

   public Collection<String> d() {
      return this.h.keySet();
   }

   public Collection<String> e() {
      return Lists.newArrayList(this.j.keySet());
   }

   public void d(String var0, @Nullable ScoreboardObjective var1) {
      if (var1 == null) {
         Map<ScoreboardObjective, ScoreboardScore> var2 = this.j.remove(var0);
         if (var2 != null) {
            this.a(var0);
         }
      } else {
         Map<ScoreboardObjective, ScoreboardScore> var2 = this.j.get(var0);
         if (var2 != null) {
            ScoreboardScore var3 = var2.remove(var1);
            if (var2.size() < 1) {
               Map<ScoreboardObjective, ScoreboardScore> var4 = this.j.remove(var0);
               if (var4 != null) {
                  this.a(var0);
               }
            } else if (var3 != null) {
               this.a(var0, var1);
            }
         }
      }
   }

   public Map<ScoreboardObjective, ScoreboardScore> e(String var0) {
      Map<ScoreboardObjective, ScoreboardScore> var1 = this.j.get(var0);
      if (var1 == null) {
         var1 = Maps.newHashMap();
      }

      return var1;
   }

   public void j(ScoreboardObjective var0) {
      this.h.remove(var0.b());

      for(int var1 = 0; var1 < 19; ++var1) {
         if (this.a(var1) == var0) {
            this.a(var1, null);
         }
      }

      List<ScoreboardObjective> var1 = this.i.get(var0.c());
      if (var1 != null) {
         var1.remove(var0);
      }

      for(Map<ScoreboardObjective, ScoreboardScore> var3 : this.j.values()) {
         var3.remove(var0);
      }

      this.c(var0);
   }

   public void a(int var0, @Nullable ScoreboardObjective var1) {
      this.k[var0] = var1;
   }

   @Nullable
   public ScoreboardObjective a(int var0) {
      return this.k[var0];
   }

   @Nullable
   public ScoreboardTeam f(String var0) {
      return this.l.get(var0);
   }

   public ScoreboardTeam g(String var0) {
      ScoreboardTeam var1 = this.f(var0);
      if (var1 != null) {
         g.warn("Requested creation of existing team '{}'", var0);
         return var1;
      } else {
         var1 = new ScoreboardTeam(this, var0);
         this.l.put(var0, var1);
         this.a(var1);
         return var1;
      }
   }

   public void d(ScoreboardTeam var0) {
      this.l.remove(var0.b());

      for(String var2 : var0.g()) {
         this.m.remove(var2);
      }

      this.c(var0);
   }

   public boolean a(String var0, ScoreboardTeam var1) {
      if (this.i(var0) != null) {
         this.h(var0);
      }

      this.m.put(var0, var1);
      return var1.g().add(var0);
   }

   public boolean h(String var0) {
      ScoreboardTeam var1 = this.i(var0);
      if (var1 != null) {
         this.b(var0, var1);
         return true;
      } else {
         return false;
      }
   }

   public void b(String var0, ScoreboardTeam var1) {
      if (this.i(var0) != var1) {
         throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + var1.b() + "'.");
      } else {
         this.m.remove(var0);
         var1.g().remove(var0);
      }
   }

   public Collection<String> f() {
      return this.l.keySet();
   }

   public Collection<ScoreboardTeam> g() {
      return this.l.values();
   }

   @Nullable
   public ScoreboardTeam i(String var0) {
      return this.m.get(var0);
   }

   public void a(ScoreboardObjective var0) {
   }

   public void b(ScoreboardObjective var0) {
   }

   public void c(ScoreboardObjective var0) {
   }

   public void a(ScoreboardScore var0) {
   }

   public void a(String var0) {
   }

   public void a(String var0, ScoreboardObjective var1) {
   }

   public void a(ScoreboardTeam var0) {
   }

   public void b(ScoreboardTeam var0) {
   }

   public void c(ScoreboardTeam var0) {
   }

   public static String b(int var0) {
      switch(var0) {
         case 0:
            return "list";
         case 1:
            return "sidebar";
         case 2:
            return "belowName";
         default:
            if (var0 >= 3 && var0 <= 18) {
               EnumChatFormat var1 = EnumChatFormat.a(var0 - 3);
               if (var1 != null && var1 != EnumChatFormat.v) {
                  return "sidebar.team." + var1.g();
               }
            }

            return null;
      }
   }

   public static int j(String var0) {
      if ("list".equalsIgnoreCase(var0)) {
         return 0;
      } else if ("sidebar".equalsIgnoreCase(var0)) {
         return 1;
      } else if ("belowName".equalsIgnoreCase(var0)) {
         return 2;
      } else {
         if (var0.startsWith("sidebar.team.")) {
            String var1 = var0.substring("sidebar.team.".length());
            EnumChatFormat var2 = EnumChatFormat.b(var1);
            if (var2 != null && var2.b() >= 0) {
               return var2.b() + 3;
            }
         }

         return -1;
      }
   }

   public static String[] h() {
      if (n == null) {
         n = new String[19];

         for(int var0 = 0; var0 < 19; ++var0) {
            n[var0] = b(var0);
         }
      }

      return n;
   }

   public void a(Entity var0) {
      if (var0 != null && !(var0 instanceof EntityHuman) && !var0.bq()) {
         String var1 = var0.ct();
         this.d(var1, null);
         this.h(var1);
      }
   }

   protected NBTTagList i() {
      NBTTagList var0 = new NBTTagList();
      this.j.values().stream().map(Map::values).forEach(var1x -> var1x.stream().filter(var0xx -> var0xx.d() != null).forEach(var1xx -> {
            NBTTagCompound var2 = new NBTTagCompound();
            var2.a("Name", var1xx.e());
            var2.a("Objective", var1xx.d().b());
            var2.a("Score", var1xx.b());
            var2.a("Locked", var1xx.g());
            var0.add(var2);
         }));
      return var0;
   }

   protected void a(NBTTagList var0) {
      for(int var1 = 0; var1 < var0.size(); ++var1) {
         NBTTagCompound var2 = var0.a(var1);
         ScoreboardObjective var3 = this.c(var2.l("Objective"));
         String var4 = var2.l("Name");
         ScoreboardScore var5 = this.c(var4, var3);
         var5.b(var2.h("Score"));
         if (var2.e("Locked")) {
            var5.a(var2.q("Locked"));
         }
      }
   }
}
