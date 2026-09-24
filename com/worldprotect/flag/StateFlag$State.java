/*    */ package com.worldprotect.flag;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum State
/*    */ {
/* 12 */   ALLOW("allow"),
/* 13 */   DENY("deny");
/*    */   
/*    */   private final String name;
/*    */   
/*    */   State(String name) {
/* 18 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 22 */     return this.name;
/*    */   }
/*    */   
/*    */   public static State fromString(String name) {
/* 26 */     for (State state : values()) {
/* 27 */       if (state.name.equalsIgnoreCase(name)) {
/* 28 */         return state;
/*    */       }
/*    */     } 
/* 31 */     throw new IllegalArgumentException("Invalid state: " + name + ". Use 'allow' or 'deny'");
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 36 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\flag\StateFlag$State.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */