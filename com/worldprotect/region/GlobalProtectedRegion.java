/*    */ package com.worldprotect.region;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ 
/*    */ public class GlobalProtectedRegion
/*    */   extends ProtectedRegion {
/*    */   public GlobalProtectedRegion(String id) {
/*  8 */     super(id);
/*  9 */     this.priority = -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public ProtectedRegion.RegionType getType() {
/* 14 */     return ProtectedRegion.RegionType.GLOBAL;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(BlockPos pos) {
/* 19 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(int x, int y, int z) {
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getMinimumPoint() {
/* 29 */     return new BlockPos(-2147483648, -2147483648, -2147483648);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getMaximumPoint() {
/* 34 */     return new BlockPos(2147483647, 2147483647, 2147483647);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getVolume() {
/* 39 */     return Integer.MAX_VALUE;
/*    */   }
/*    */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\region\GlobalProtectedRegion.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */