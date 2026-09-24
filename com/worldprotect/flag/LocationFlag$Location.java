/*    */ package com.worldprotect.flag;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Location
/*    */ {
/*    */   private final String world;
/*    */   private final double x;
/*    */   private final double y;
/*    */   private final double z;
/*    */   private final float yaw;
/*    */   private final float pitch;
/*    */   
/*    */   public Location(String world, double x, double y, double z, float yaw, float pitch) {
/* 21 */     this.world = world;
/* 22 */     this.x = x;
/* 23 */     this.y = y;
/* 24 */     this.z = z;
/* 25 */     this.yaw = yaw;
/* 26 */     this.pitch = pitch;
/*    */   }
/*    */   
/*    */   public Location(String world, double x, double y, double z) {
/* 30 */     this(world, x, y, z, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public Location(String world, BlockPos pos) {
/* 34 */     this(world, pos.m_123341_() + 0.5D, pos.m_123342_(), pos.m_123343_() + 0.5D, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public String getWorld() {
/* 38 */     return this.world;
/*    */   }
/*    */   
/*    */   public double getX() {
/* 42 */     return this.x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 46 */     return this.y;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 50 */     return this.z;
/*    */   }
/*    */   
/*    */   public float getYaw() {
/* 54 */     return this.yaw;
/*    */   }
/*    */   
/*    */   public float getPitch() {
/* 58 */     return this.pitch;
/*    */   }
/*    */   
/*    */   public BlockPos toBlockPos() {
/* 62 */     return new BlockPos((int)this.x, (int)this.y, (int)this.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 67 */     return String.format("%s: %.2f, %.2f, %.2f", new Object[] { this.world, Double.valueOf(this.x), Double.valueOf(this.y), Double.valueOf(this.z) });
/*    */   }
/*    */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\flag\LocationFlag$Location.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */