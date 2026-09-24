/*    */ package com.worldprotect.flag;
/*    */ 
/*    */ public enum RegionGroup {
/*  4 */   ALL("all"),
/*  5 */   OWNERS("owners"),
/*  6 */   MEMBERS("members"),
/*  7 */   NON_OWNERS("nonowners"),
/*  8 */   NON_MEMBERS("nonmembers"),
/*  9 */   NONE("none");
/*    */   
/*    */   private final String name;
/*    */   
/*    */   RegionGroup(String name) {
/* 14 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 18 */     return this.name;
/*    */   }
/*    */   
/*    */   public static RegionGroup fromString(String name) {
/* 22 */     for (RegionGroup group : values()) {
/* 23 */       if (group.name.equalsIgnoreCase(name)) {
/* 24 */         return group;
/*    */       }
/*    */     } 
/* 27 */     return ALL;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 32 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\flag\RegionGroup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */