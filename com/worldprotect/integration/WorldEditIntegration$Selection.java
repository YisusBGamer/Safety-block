/*     */ package com.worldprotect.integration;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Selection
/*     */ {
/*     */   private BlockPos pos1;
/*     */   private BlockPos pos2;
/*     */   private List<BlockPos> polygonPoints;
/*     */   private int minY;
/*     */   private int maxY;
/*     */   private Type type;
/*     */   
/*     */   public enum Type
/*     */   {
/* 174 */     CUBOID,
/* 175 */     POLYGON;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Selection(BlockPos pos1, BlockPos pos2, Type type) {
/* 186 */     this.pos1 = pos1;
/* 187 */     this.pos2 = pos2;
/* 188 */     this.type = type;
/*     */   }
/*     */   
/*     */   public Selection(List<BlockPos> polygonPoints, int minY, int maxY) {
/* 192 */     this.polygonPoints = polygonPoints;
/* 193 */     this.minY = minY;
/* 194 */     this.maxY = maxY;
/* 195 */     this.type = Type.POLYGON;
/*     */   }
/*     */   
/*     */   public BlockPos getPos1() {
/* 199 */     return this.pos1;
/*     */   }
/*     */   
/*     */   public void setPos1(BlockPos pos1) {
/* 203 */     this.pos1 = pos1;
/*     */   }
/*     */   
/*     */   public BlockPos getPos2() {
/* 207 */     return this.pos2;
/*     */   }
/*     */   
/*     */   public void setPos2(BlockPos pos2) {
/* 211 */     this.pos2 = pos2;
/*     */   }
/*     */   
/*     */   public List<BlockPos> getPolygonPoints() {
/* 215 */     return this.polygonPoints;
/*     */   }
/*     */   
/*     */   public int getMinY() {
/* 219 */     return this.minY;
/*     */   }
/*     */   
/*     */   public int getMaxY() {
/* 223 */     return this.maxY;
/*     */   }
/*     */   
/*     */   public Type getType() {
/* 227 */     return this.type;
/*     */   }
/*     */   
/*     */   public boolean isComplete() {
/* 231 */     if (this.type == Type.CUBOID) {
/* 232 */       return (this.pos1 != null && this.pos2 != null);
/*     */     }
/* 234 */     return (this.polygonPoints != null && this.polygonPoints.size() >= 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getMinimumPoint() {
/* 239 */     if (this.type == Type.CUBOID && this.pos1 != null && this.pos2 != null) {
/* 240 */       return new BlockPos(
/* 241 */           Math.min(this.pos1.m_123341_(), this.pos2.m_123341_()), 
/* 242 */           Math.min(this.pos1.m_123342_(), this.pos2.m_123342_()), 
/* 243 */           Math.min(this.pos1.m_123343_(), this.pos2.m_123343_()));
/*     */     }
/*     */     
/* 246 */     return null;
/*     */   }
/*     */   
/*     */   public BlockPos getMaximumPoint() {
/* 250 */     if (this.type == Type.CUBOID && this.pos1 != null && this.pos2 != null) {
/* 251 */       return new BlockPos(
/* 252 */           Math.max(this.pos1.m_123341_(), this.pos2.m_123341_()), 
/* 253 */           Math.max(this.pos1.m_123342_(), this.pos2.m_123342_()), 
/* 254 */           Math.max(this.pos1.m_123343_(), this.pos2.m_123343_()));
/*     */     }
/*     */     
/* 257 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\integration\WorldEditIntegration$Selection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */