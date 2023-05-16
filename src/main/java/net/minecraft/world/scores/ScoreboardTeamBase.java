package net.minecraft.world.scores;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;

public abstract class ScoreboardTeamBase {
   public boolean a(@Nullable ScoreboardTeamBase var0) {
      if (var0 == null) {
         return false;
      } else {
         return this == var0;
      }
   }

   public abstract String b();

   public abstract IChatMutableComponent d(IChatBaseComponent var1);

   public abstract boolean i();

   public abstract boolean h();

   public abstract ScoreboardTeamBase.EnumNameTagVisibility j();

   public abstract EnumChatFormat n();

   public abstract Collection<String> g();

   public abstract ScoreboardTeamBase.EnumNameTagVisibility k();

   public abstract ScoreboardTeamBase.EnumTeamPush l();

   public static enum EnumNameTagVisibility {
      a("always", 0),
      b("never", 1),
      c("hideForOtherTeams", 2),
      d("hideForOwnTeam", 3);

      private static final Map<String, ScoreboardTeamBase.EnumNameTagVisibility> g = Arrays.stream(values())
         .collect(Collectors.toMap(var0 -> var0.e, var0 -> var0));
      public final String e;
      public final int f;

      public static String[] a() {
         return g.keySet().toArray(new String[0]);
      }

      @Nullable
      public static ScoreboardTeamBase.EnumNameTagVisibility a(String var0) {
         return g.get(var0);
      }

      private EnumNameTagVisibility(String var2, int var3) {
         this.e = var2;
         this.f = var3;
      }

      public IChatBaseComponent b() {
         return IChatBaseComponent.c("team.visibility." + this.e);
      }
   }

   public static enum EnumTeamPush {
      a("always", 0),
      b("never", 1),
      c("pushOtherTeams", 2),
      d("pushOwnTeam", 3);

      private static final Map<String, ScoreboardTeamBase.EnumTeamPush> g = Arrays.stream(values()).collect(Collectors.toMap(var0 -> var0.e, var0 -> var0));
      public final String e;
      public final int f;

      @Nullable
      public static ScoreboardTeamBase.EnumTeamPush a(String var0) {
         return g.get(var0);
      }

      private EnumTeamPush(String var2, int var3) {
         this.e = var2;
         this.f = var3;
      }

      public IChatBaseComponent a() {
         return IChatBaseComponent.c("team.collision." + this.e);
      }
   }
}
