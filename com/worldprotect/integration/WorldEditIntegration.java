/*     */ package com.worldprotect.integration;
/*     */ 
/*     */ import com.worldprotect.WorldProtect;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ 
/*     */ public class WorldEditIntegration
/*     */ {
/*  17 */   private final Map<UUID, Selection> fallbackSelections = new HashMap<>();
/*     */   private boolean worldEditAvailable = false;
/*     */   
/*     */   public WorldEditIntegration() {
/*     */     try {
/*  22 */       Class.forName("com.sk89q.worldedit.forge.ForgeWorldEdit");
/*  23 */       this.worldEditAvailable = true;
/*  24 */       WorldProtect.LOGGER.info("WorldEdit integration initialized - WorldEdit detected");
/*  25 */     } catch (ClassNotFoundException e) {
/*  26 */       this.worldEditAvailable = false;
/*  27 */       WorldProtect.LOGGER.info("WorldEdit not found - using built-in selection system");
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isWorldEditAvailable() {
/*  32 */     return this.worldEditAvailable;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Selection getSelection(Player player) {
/*  37 */     if (this.worldEditAvailable) {
/*     */       try {
/*  39 */         Selection weSelection = getWorldEditSelection(player);
/*  40 */         if (weSelection != null) {
/*  41 */           return weSelection;
/*     */         }
/*  43 */       } catch (Exception e) {
/*  44 */         WorldProtect.LOGGER.debug("Error getting WorldEdit selection: {}", e.getMessage());
/*     */       } 
/*     */     }
/*  47 */     return this.fallbackSelections.get(player.m_20148_());
/*     */   }
/*     */   @Nullable
/*     */   private Selection getWorldEditSelection(Player player) {
/*     */     ServerPlayer serverPlayer;
/*  52 */     if (player instanceof ServerPlayer) { serverPlayer = (ServerPlayer)player; }
/*  53 */     else { return null; }
/*     */ 
/*     */     
/*     */     try {
/*  57 */       Class<?> forgeWorldEditClass = Class.forName("com.sk89q.worldedit.forge.ForgeWorldEdit");
/*  58 */       Object forgeWorldEdit = forgeWorldEditClass.getField("inst").get(null);
/*     */       
/*  60 */       Method wrapMethod = forgeWorldEditClass.getMethod("wrap", new Class[] { ServerPlayer.class });
/*  61 */       Object wePlayer = wrapMethod.invoke(forgeWorldEdit, new Object[] { serverPlayer });
/*     */       
/*  63 */       Class<?> worldEditClass = Class.forName("com.sk89q.worldedit.WorldEdit");
/*  64 */       Method getInstanceMethod = worldEditClass.getMethod("getInstance", new Class[0]);
/*  65 */       Object worldEdit = getInstanceMethod.invoke(null, new Object[0]);
/*     */       
/*  67 */       Method getSessionManagerMethod = worldEditClass.getMethod("getSessionManager", new Class[0]);
/*  68 */       Object sessionManager = getSessionManagerMethod.invoke(worldEdit, new Object[0]);
/*     */       
/*  70 */       Class<?> sessionManagerClass = Class.forName("com.sk89q.worldedit.session.SessionManager");
/*  71 */       Class<?> sessionOwnerClass = Class.forName("com.sk89q.worldedit.session.SessionOwner");
/*  72 */       Method getMethod = sessionManagerClass.getMethod("get", new Class[] { sessionOwnerClass });
/*  73 */       Object session = getMethod.invoke(sessionManager, new Object[] { wePlayer });
/*     */       
/*  75 */       Class<?> forgePlayerClass = Class.forName("com.sk89q.worldedit.forge.ForgePlayer");
/*  76 */       Method getWorldMethod = forgePlayerClass.getMethod("getWorld", new Class[0]);
/*  77 */       Object world = getWorldMethod.invoke(wePlayer, new Object[0]);
/*     */       
/*  79 */       Class<?> localSessionClass = Class.forName("com.sk89q.worldedit.LocalSession");
/*  80 */       Class<?> worldClass = Class.forName("com.sk89q.worldedit.world.World");
/*  81 */       Method getSelectionMethod = localSessionClass.getMethod("getSelection", new Class[] { worldClass });
/*     */       
/*  83 */       Object region = getSelectionMethod.invoke(session, new Object[] { world });
/*     */       
/*  85 */       if (region == null) {
/*  86 */         return null;
/*     */       }
/*     */       
/*  89 */       Class<?> cuboidRegionClass = Class.forName("com.sk89q.worldedit.regions.CuboidRegion");
/*  90 */       if (cuboidRegionClass.isInstance(region)) {
/*  91 */         Method getMinMethod = region.getClass().getMethod("getMinimumPoint", new Class[0]);
/*  92 */         Method getMaxMethod = region.getClass().getMethod("getMaximumPoint", new Class[0]);
/*     */         
/*  94 */         Object min = getMinMethod.invoke(region, new Object[0]);
/*  95 */         Object max = getMaxMethod.invoke(region, new Object[0]);
/*     */         
/*  97 */         Class<?> blockVector3Class = Class.forName("com.sk89q.worldedit.math.BlockVector3");
/*  98 */         Method xMethod = blockVector3Class.getMethod("x", new Class[0]);
/*  99 */         Method yMethod = blockVector3Class.getMethod("y", new Class[0]);
/* 100 */         Method zMethod = blockVector3Class.getMethod("z", new Class[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 105 */         BlockPos minPos = new BlockPos(((Integer)xMethod.invoke(min, new Object[0])).intValue(), ((Integer)yMethod.invoke(min, new Object[0])).intValue(), ((Integer)zMethod.invoke(min, new Object[0])).intValue());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 110 */         BlockPos maxPos = new BlockPos(((Integer)xMethod.invoke(max, new Object[0])).intValue(), ((Integer)yMethod.invoke(max, new Object[0])).intValue(), ((Integer)zMethod.invoke(max, new Object[0])).intValue());
/*     */ 
/*     */         
/* 113 */         return new Selection(minPos, maxPos, Selection.Type.CUBOID);
/*     */       } 
/*     */       
/* 116 */       Class<?> polyRegionClass = Class.forName("com.sk89q.worldedit.regions.Polygonal2DRegion");
/* 117 */       if (polyRegionClass.isInstance(region)) {
/* 118 */         Method getPointsMethod = region.getClass().getMethod("getPoints", new Class[0]);
/* 119 */         Method getMinYMethod = region.getClass().getMethod("getMinimumY", new Class[0]);
/* 120 */         Method getMaxYMethod = region.getClass().getMethod("getMaximumY", new Class[0]);
/*     */ 
/*     */         
/* 123 */         List<?> points = (List)getPointsMethod.invoke(region, new Object[0]);
/* 124 */         int minY = ((Integer)getMinYMethod.invoke(region, new Object[0])).intValue();
/* 125 */         int maxY = ((Integer)getMaxYMethod.invoke(region, new Object[0])).intValue();
/*     */         
/* 127 */         List<BlockPos> blockPoints = new ArrayList<>();
/* 128 */         Class<?> blockVector2Class = Class.forName("com.sk89q.worldedit.math.BlockVector2");
/* 129 */         Method bv2XMethod = blockVector2Class.getMethod("x", new Class[0]);
/* 130 */         Method bv2ZMethod = blockVector2Class.getMethod("z", new Class[0]);
/*     */         
/* 132 */         for (Object point : points) {
/* 133 */           blockPoints.add(new BlockPos(((Integer)bv2XMethod
/* 134 */                 .invoke(point, new Object[0])).intValue(), 0, ((Integer)bv2ZMethod
/*     */                 
/* 136 */                 .invoke(point, new Object[0])).intValue()));
/*     */         }
/*     */ 
/*     */         
/* 140 */         return new Selection(blockPoints, minY, maxY);
/*     */       }
/*     */     
/* 143 */     } catch (Exception e) {
/* 144 */       WorldProtect.LOGGER.debug("Error getting WorldEdit selection via reflection: {}", e.getMessage());
/*     */     } 
/*     */     
/* 147 */     return null;
/*     */   }
/*     */   
/*     */   public void setFallbackPos1(Player player, BlockPos pos) {
/* 151 */     UUID uuid = player.m_20148_();
/* 152 */     Selection selection = this.fallbackSelections.computeIfAbsent(uuid, k -> new Selection(null, null, Selection.Type.CUBOID));
/*     */     
/* 154 */     selection.setPos1(pos);
/*     */   }
/*     */   
/*     */   public void setFallbackPos2(Player player, BlockPos pos) {
/* 158 */     UUID uuid = player.m_20148_();
/* 159 */     Selection selection = this.fallbackSelections.computeIfAbsent(uuid, k -> new Selection(null, null, Selection.Type.CUBOID));
/*     */     
/* 161 */     selection.setPos2(pos);
/*     */   }
/*     */   
/*     */   public Selection getFallbackSelection(Player player) {
/* 165 */     return this.fallbackSelections.get(player.m_20148_());
/*     */   }
/*     */   
/*     */   public void clearFallbackSelection(Player player) {
/* 169 */     this.fallbackSelections.remove(player.m_20148_());
/*     */   }
/*     */   public static class Selection { private BlockPos pos1; private BlockPos pos2; private List<BlockPos> polygonPoints; private int minY; private int maxY;
/*     */     private Type type;
/*     */     
/* 174 */     public enum Type { CUBOID,
/* 175 */       POLYGON; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Selection(BlockPos pos1, BlockPos pos2, Type type) {
/* 186 */       this.pos1 = pos1;
/* 187 */       this.pos2 = pos2;
/* 188 */       this.type = type;
/*     */     }
/*     */     
/*     */     public Selection(List<BlockPos> polygonPoints, int minY, int maxY) {
/* 192 */       this.polygonPoints = polygonPoints;
/* 193 */       this.minY = minY;
/* 194 */       this.maxY = maxY;
/* 195 */       this.type = Type.POLYGON;
/*     */     }
/*     */     
/*     */     public BlockPos getPos1() {
/* 199 */       return this.pos1;
/*     */     }
/*     */     
/*     */     public void setPos1(BlockPos pos1) {
/* 203 */       this.pos1 = pos1;
/*     */     }
/*     */     
/*     */     public BlockPos getPos2() {
/* 207 */       return this.pos2;
/*     */     }
/*     */     
/*     */     public void setPos2(BlockPos pos2) {
/* 211 */       this.pos2 = pos2;
/*     */     }
/*     */     
/*     */     public List<BlockPos> getPolygonPoints() {
/* 215 */       return this.polygonPoints;
/*     */     }
/*     */     
/*     */     public int getMinY() {
/* 219 */       return this.minY;
/*     */     }
/*     */     
/*     */     public int getMaxY() {
/* 223 */       return this.maxY;
/*     */     }
/*     */     
/*     */     public Type getType() {
/* 227 */       return this.type;
/*     */     }
/*     */     
/*     */     public boolean isComplete() {
/* 231 */       if (this.type == Type.CUBOID) {
/* 232 */         return (this.pos1 != null && this.pos2 != null);
/*     */       }
/* 234 */       return (this.polygonPoints != null && this.polygonPoints.size() >= 3);
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos getMinimumPoint() {
/* 239 */       if (this.type == Type.CUBOID && this.pos1 != null && this.pos2 != null) {
/* 240 */         return new BlockPos(
/* 241 */             Math.min(this.pos1.m_123341_(), this.pos2.m_123341_()), 
/* 242 */             Math.min(this.pos1.m_123342_(), this.pos2.m_123342_()), 
/* 243 */             Math.min(this.pos1.m_123343_(), this.pos2.m_123343_()));
/*     */       }
/*     */       
/* 246 */       return null;
/*     */     }
/*     */     
/*     */     public BlockPos getMaximumPoint() {
/* 250 */       if (this.type == Type.CUBOID && this.pos1 != null && this.pos2 != null) {
/* 251 */         return new BlockPos(
/* 252 */             Math.max(this.pos1.m_123341_(), this.pos2.m_123341_()), 
/* 253 */             Math.max(this.pos1.m_123342_(), this.pos2.m_123342_()), 
/* 254 */             Math.max(this.pos1.m_123343_(), this.pos2.m_123343_()));
/*     */       }
/*     */       
/* 257 */       return null;
/*     */     } }
/*     */ 
/*     */   
/*     */   public enum Type {
/*     */     CUBOID, POLYGON;
/*     */   }
/*     */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\integration\WorldEditIntegration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */