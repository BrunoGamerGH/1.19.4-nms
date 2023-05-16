package net.minecraft.world.scores;

import net.minecraft.EnumChatFormat;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.level.saveddata.PersistentBase;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;

public class PersistentScoreboard extends PersistentBase {
   public static final String a = "scoreboard";
   private final Scoreboard b;

   public PersistentScoreboard(Scoreboard var0) {
      this.b = var0;
   }

   public PersistentScoreboard b(NBTTagCompound var0) {
      this.b(var0.c("Objectives", 10));
      this.b.a(var0.c("PlayerScores", 10));
      if (var0.b("DisplaySlots", 10)) {
         this.c(var0.p("DisplaySlots"));
      }

      if (var0.b("Teams", 9)) {
         this.a(var0.c("Teams", 10));
      }

      return this;
   }

   private void a(NBTTagList var0) {
      for(int var1 = 0; var1 < var0.size(); ++var1) {
         NBTTagCompound var2 = var0.a(var1);
         String var3 = var2.l("Name");
         ScoreboardTeam var4 = this.b.g(var3);
         IChatBaseComponent var5 = IChatBaseComponent.ChatSerializer.a(var2.l("DisplayName"));
         if (var5 != null) {
            var4.a(var5);
         }

         if (var2.b("TeamColor", 8)) {
            var4.a(EnumChatFormat.b(var2.l("TeamColor")));
         }

         if (var2.b("AllowFriendlyFire", 99)) {
            var4.a(var2.q("AllowFriendlyFire"));
         }

         if (var2.b("SeeFriendlyInvisibles", 99)) {
            var4.b(var2.q("SeeFriendlyInvisibles"));
         }

         if (var2.b("MemberNamePrefix", 8)) {
            IChatBaseComponent var6 = IChatBaseComponent.ChatSerializer.a(var2.l("MemberNamePrefix"));
            if (var6 != null) {
               var4.b(var6);
            }
         }

         if (var2.b("MemberNameSuffix", 8)) {
            IChatBaseComponent var6 = IChatBaseComponent.ChatSerializer.a(var2.l("MemberNameSuffix"));
            if (var6 != null) {
               var4.c(var6);
            }
         }

         if (var2.b("NameTagVisibility", 8)) {
            ScoreboardTeamBase.EnumNameTagVisibility var6 = ScoreboardTeamBase.EnumNameTagVisibility.a(var2.l("NameTagVisibility"));
            if (var6 != null) {
               var4.a(var6);
            }
         }

         if (var2.b("DeathMessageVisibility", 8)) {
            ScoreboardTeamBase.EnumNameTagVisibility var6 = ScoreboardTeamBase.EnumNameTagVisibility.a(var2.l("DeathMessageVisibility"));
            if (var6 != null) {
               var4.b(var6);
            }
         }

         if (var2.b("CollisionRule", 8)) {
            ScoreboardTeamBase.EnumTeamPush var6 = ScoreboardTeamBase.EnumTeamPush.a(var2.l("CollisionRule"));
            if (var6 != null) {
               var4.a(var6);
            }
         }

         this.a(var4, var2.c("Players", 8));
      }
   }

   private void a(ScoreboardTeam var0, NBTTagList var1) {
      for(int var2 = 0; var2 < var1.size(); ++var2) {
         this.b.a(var1.j(var2), var0);
      }
   }

   private void c(NBTTagCompound var0) {
      for(int var1 = 0; var1 < 19; ++var1) {
         if (var0.b("slot_" + var1, 8)) {
            String var2 = var0.l("slot_" + var1);
            ScoreboardObjective var3 = this.b.d(var2);
            this.b.a(var1, var3);
         }
      }
   }

   private void b(NBTTagList var0) {
      for(int var1 = 0; var1 < var0.size(); ++var1) {
         NBTTagCompound var2 = var0.a(var1);
         IScoreboardCriteria.a(var2.l("CriteriaName")).ifPresent(var1x -> {
            String var2x = var2.l("Name");
            IChatBaseComponent var3x = IChatBaseComponent.ChatSerializer.a(var2.l("DisplayName"));
            IScoreboardCriteria.EnumScoreboardHealthDisplay var4 = IScoreboardCriteria.EnumScoreboardHealthDisplay.a(var2.l("RenderType"));
            this.b.a(var2x, var1x, var3x, var4);
         });
      }
   }

   @Override
   public NBTTagCompound a(NBTTagCompound var0) {
      var0.a("Objectives", this.d());
      var0.a("PlayerScores", this.b.i());
      var0.a("Teams", this.a());
      this.d(var0);
      return var0;
   }

   private NBTTagList a() {
      NBTTagList var0 = new NBTTagList();

      for(ScoreboardTeam var3 : this.b.g()) {
         NBTTagCompound var4 = new NBTTagCompound();
         var4.a("Name", var3.b());
         var4.a("DisplayName", IChatBaseComponent.ChatSerializer.a(var3.c()));
         if (var3.n().b() >= 0) {
            var4.a("TeamColor", var3.n().g());
         }

         var4.a("AllowFriendlyFire", var3.h());
         var4.a("SeeFriendlyInvisibles", var3.i());
         var4.a("MemberNamePrefix", IChatBaseComponent.ChatSerializer.a(var3.e()));
         var4.a("MemberNameSuffix", IChatBaseComponent.ChatSerializer.a(var3.f()));
         var4.a("NameTagVisibility", var3.j().e);
         var4.a("DeathMessageVisibility", var3.k().e);
         var4.a("CollisionRule", var3.l().e);
         NBTTagList var5 = new NBTTagList();

         for(String var7 : var3.g()) {
            var5.add(NBTTagString.a(var7));
         }

         var4.a("Players", var5);
         var0.add(var4);
      }

      return var0;
   }

   private void d(NBTTagCompound var0) {
      NBTTagCompound var1 = new NBTTagCompound();
      boolean var2 = false;

      for(int var3 = 0; var3 < 19; ++var3) {
         ScoreboardObjective var4 = this.b.a(var3);
         if (var4 != null) {
            var1.a("slot_" + var3, var4.b());
            var2 = true;
         }
      }

      if (var2) {
         var0.a("DisplaySlots", var1);
      }
   }

   private NBTTagList d() {
      NBTTagList var0 = new NBTTagList();

      for(ScoreboardObjective var3 : this.b.c()) {
         if (var3.c() != null) {
            NBTTagCompound var4 = new NBTTagCompound();
            var4.a("Name", var3.b());
            var4.a("CriteriaName", var3.c().d());
            var4.a("DisplayName", IChatBaseComponent.ChatSerializer.a(var3.d()));
            var4.a("RenderType", var3.f().a());
            var0.add(var4);
         }
      }

      return var0;
   }
}
