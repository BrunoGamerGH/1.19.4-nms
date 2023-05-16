package net.minecraft.world.entity.ai.behavior.declarative;

import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityLiving;

public interface Trigger<E extends EntityLiving> {
   boolean trigger(WorldServer var1, E var2, long var3);
}
