package org.bukkit.craftbukkit.v1_19_R3.advancement;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.server.AdvancementDataPlayer;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;

public class CraftAdvancementProgress implements AdvancementProgress {
   private final CraftAdvancement advancement;
   private final AdvancementDataPlayer playerData;
   private final net.minecraft.advancements.AdvancementProgress handle;

   public CraftAdvancementProgress(CraftAdvancement advancement, AdvancementDataPlayer player, net.minecraft.advancements.AdvancementProgress handle) {
      this.advancement = advancement;
      this.playerData = player;
      this.handle = handle;
   }

   public Advancement getAdvancement() {
      return this.advancement;
   }

   public boolean isDone() {
      return this.handle.a();
   }

   public boolean awardCriteria(String criteria) {
      return this.playerData.a(this.advancement.getHandle(), criteria);
   }

   public boolean revokeCriteria(String criteria) {
      return this.playerData.b(this.advancement.getHandle(), criteria);
   }

   public Date getDateAwarded(String criteria) {
      CriterionProgress criterion = this.handle.c(criteria);
      return criterion == null ? null : criterion.d();
   }

   public Collection<String> getRemainingCriteria() {
      return Collections.unmodifiableCollection(Lists.newArrayList(this.handle.e()));
   }

   public Collection<String> getAwardedCriteria() {
      return Collections.unmodifiableCollection(Lists.newArrayList(this.handle.f()));
   }
}
