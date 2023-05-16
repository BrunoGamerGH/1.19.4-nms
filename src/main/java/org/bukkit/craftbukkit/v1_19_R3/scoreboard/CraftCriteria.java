package org.bukkit.craftbukkit.v1_19_R3.scoreboard;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.RenderType;

public final class CraftCriteria implements Criteria {
   static final Map<String, CraftCriteria> DEFAULTS;
   static final CraftCriteria DUMMY;
   final IScoreboardCriteria criteria;
   final String bukkitName;

   static {
      Builder<String, CraftCriteria> defaults = ImmutableMap.builder();

      for(Entry<String, IScoreboardCriteria> entry : IScoreboardCriteria.o.entrySet()) {
         String name = entry.getKey();
         IScoreboardCriteria criteria = entry.getValue();
         defaults.put(name, new CraftCriteria(criteria));
      }

      DEFAULTS = defaults.build();
      DUMMY = DEFAULTS.get("dummy");
   }

   private CraftCriteria(String bukkitName) {
      this.bukkitName = bukkitName;
      this.criteria = DUMMY.criteria;
   }

   private CraftCriteria(IScoreboardCriteria criteria) {
      this.criteria = criteria;
      this.bukkitName = criteria.d();
   }

   public String getName() {
      return this.bukkitName;
   }

   public boolean isReadOnly() {
      return this.criteria.e();
   }

   public RenderType getDefaultRenderType() {
      return RenderType.values()[this.criteria.f().ordinal()];
   }

   static CraftCriteria getFromNMS(ScoreboardObjective objective) {
      return DEFAULTS.get(objective.c().d());
   }

   public static CraftCriteria getFromBukkit(String name) {
      CraftCriteria criteria = DEFAULTS.get(name);
      return criteria != null ? criteria : IScoreboardCriteria.a(name).map(CraftCriteria::new).orElseGet(() -> new CraftCriteria(name));
   }

   @Override
   public boolean equals(Object that) {
      return !(that instanceof CraftCriteria) ? false : ((CraftCriteria)that).bukkitName.equals(this.bukkitName);
   }

   @Override
   public int hashCode() {
      return this.bukkitName.hashCode() ^ CraftCriteria.class.hashCode();
   }
}
