package net.minecraft.world.entity.ai.village;

public interface ReputationEvent {
   ReputationEvent a = a("zombie_villager_cured");
   ReputationEvent b = a("golem_killed");
   ReputationEvent c = a("villager_hurt");
   ReputationEvent d = a("villager_killed");
   ReputationEvent e = a("trade");

   static ReputationEvent a(final String var0) {
      return new ReputationEvent() {
         @Override
         public String toString() {
            return var0;
         }
      };
   }
}
