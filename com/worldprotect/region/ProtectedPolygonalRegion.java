/*     */ package com.worldprotect.region;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ 
/*     */ public class ProtectedPolygonalRegion
/*     */   extends ProtectedRegion {
/*     */   private final List<BlockPos> points;
/*     */   private final int minY;
/*     */   private final int maxY;
/*     */   private final BlockPos min;
/*     */   private final BlockPos max;
/*     */   
/*     */   public ProtectedPolygonalRegion(String id, List<BlockPos> points, int minY, int maxY) {
/*  16 */     super(id);
/*  17 */     this.points = new ArrayList<>(points);
/*  18 */     this.minY = Math.min(minY, maxY);
/*  19 */     this.maxY = Math.max(minY, maxY);
/*     */     
/*  21 */     int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
/*  22 */     int minZ = Integer.MAX_VALUE, maxZ = Integer.MIN_VALUE;
/*     */     
/*  24 */     for (BlockPos point : points) {
/*  25 */       minX = Math.min(minX, point.m_123341_());
/*  26 */       maxX = Math.max(maxX, point.m_123341_());
/*  27 */       minZ = Math.min(minZ, point.m_123343_());
/*  28 */       maxZ = Math.max(maxZ, point.m_123343_());
/*     */     } 
/*     */     
/*  31 */     this.min = new BlockPos(minX, this.minY, minZ);
/*  32 */     this.max = new BlockPos(maxX, this.maxY, maxZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public ProtectedRegion.RegionType getType() {
/*  37 */     return ProtectedRegion.RegionType.POLYGON;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(BlockPos pos) {
/*  42 */     return contains(pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(int x, int y, int z) {
/*  47 */     if (y < this.minY || y > this.maxY) {
/*  48 */       return false;
/*     */     }
/*     */     
/*  51 */     if (x < this.min.m_123341_() || x > this.max.m_123341_() || z < this.min.m_123343_() || z > this.max.m_123343_()) {
/*  52 */       return false;
/*     */     }
/*     */     
/*  55 */     return isInsidePolygon(x, z);
/*     */   }
/*     */   
/*     */   private boolean isInsidePolygon(int x, int z) {
/*  59 */     int n = this.points.size();
/*  60 */     boolean inside = false;
/*     */     int j;
/*  62 */     for (int i = 0; i < n; j = i++) {
/*  63 */       int xi = ((BlockPos)this.points.get(i)).m_123341_();
/*  64 */       int zi = ((BlockPos)this.points.get(i)).m_123343_();
/*  65 */       int xj = ((BlockPos)this.points.get(j)).m_123341_();
/*  66 */       int zj = ((BlockPos)this.points.get(j)).m_123343_();
/*     */       
/*  68 */       if (((zi > z) ? true : false) != ((zj > z) ? true : false) && x < (xj - xi) * (z - zi) / (zj - zi) + xi)
/*     */       {
/*  70 */         inside = !inside;
/*     */       }
/*     */     } 
/*     */     
/*  74 */     return inside;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getMinimumPoint() {
/*  79 */     return this.min;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getMaximumPoint() {
/*  84 */     return this.max;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVolume() {
/*  89 */     double area = 0.0D;
/*  90 */     int n = this.points.size();
/*     */     
/*  92 */     for (int i = 0; i < n; i++) {
/*  93 */       int j = (i + 1) % n;
/*  94 */       area += (((BlockPos)this.points.get(i)).m_123341_() * ((BlockPos)this.points.get(j)).m_123343_());
/*  95 */       area -= (((BlockPos)this.points.get(j)).m_123341_() * ((BlockPos)this.points.get(i)).m_123343_());
/*     */     } 
/*  97 */     area = Math.abs(area) / 2.0D;
/*     */     
/*  99 */     return (int)(area * (this.maxY - this.minY + 1));
/*     */   }
/*     */   
/*     */   public List<BlockPos> getPoints() {
/* 103 */     return new ArrayList<>(this.points);
/*     */   }
/*     */   
/*     */   public int getMinY() {
/* 107 */     return this.minY;
/*     */   }
/*     */   
/*     */   public int getMaxY() {
/* 111 */     return this.maxY;
/*     */   }
/*     */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\region\ProtectedPolygonalRegion.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */