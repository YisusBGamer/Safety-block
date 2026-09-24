/*    */ package com.worldprotect.region;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ 
/*    */ public class ProtectedCuboidRegion extends ProtectedRegion {
/*    */   private final BlockPos min;
/*    */   private final BlockPos max;
/*    */   
/*    */   public ProtectedCuboidRegion(String id, BlockPos pos1, BlockPos pos2) {
/* 10 */     super(id);
/* 11 */     this
/*    */ 
/*    */       
/* 14 */       .min = new BlockPos(Math.min(pos1.m_123341_(), pos2.m_123341_()), Math.min(pos1.m_123342_(), pos2.m_123342_()), Math.min(pos1.m_123343_(), pos2.m_123343_()));
/*    */     
/* 16 */     this
/*    */ 
/*    */       
/* 19 */       .max = new BlockPos(Math.max(pos1.m_123341_(), pos2.m_123341_()), Math.max(pos1.m_123342_(), pos2.m_123342_()), Math.max(pos1.m_123343_(), pos2.m_123343_()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ProtectedRegion.RegionType getType() {
/* 25 */     return ProtectedRegion.RegionType.CUBOID;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(BlockPos pos) {
/* 30 */     return contains(pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(int x, int y, int z) {
/* 35 */     return (x >= this.min.m_123341_() && x <= this.max.m_123341_() && y >= this.min
/* 36 */       .m_123342_() && y <= this.max.m_123342_() && z >= this.min
/* 37 */       .m_123343_() && z <= this.max.m_123343_());
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getMinimumPoint() {
/* 42 */     return this.min;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getMaximumPoint() {
/* 47 */     return this.max;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getVolume() {
/* 52 */     int width = this.max.m_123341_() - this.min.m_123341_() + 1;
/* 53 */     int height = this.max.m_123342_() - this.min.m_123342_() + 1;
/* 54 */     int length = this.max.m_123343_() - this.min.m_123343_() + 1;
/* 55 */     return width * height * length;
/*    */   }
/*    */   
/*    */   public boolean intersects(ProtectedCuboidRegion other) {
/* 59 */     return (this.min.m_123341_() <= other.max.m_123341_() && this.max.m_123341_() >= other.min.m_123341_() && this.min
/* 60 */       .m_123342_() <= other.max.m_123342_() && this.max.m_123342_() >= other.min.m_123342_() && this.min
/* 61 */       .m_123343_() <= other.max.m_123343_() && this.max.m_123343_() >= other.min.m_123343_());
/*    */   }
/*    */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\region\ProtectedCuboidRegion.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */