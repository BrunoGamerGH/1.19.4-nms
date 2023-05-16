package org.bukkit.craftbukkit.v1_19_R3.map;

import java.util.ArrayList;
import org.bukkit.map.MapCursor;

public class RenderData {
   public final byte[] buffer = new byte[16384];
   public final ArrayList<MapCursor> cursors = new ArrayList();
}
