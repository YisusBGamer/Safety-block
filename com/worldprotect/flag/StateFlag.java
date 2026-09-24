/*    */ package com.worldprotect.flag;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class StateFlag
/*    */   extends Flag<StateFlag.State> {
/*    */   private final State defaultValue;
/*    */   
/*    */   public enum State {
/* 12 */     ALLOW("allow"),
/* 13 */     DENY("deny");
/*    */     
/*    */     private final String name;
/*    */     
/*    */     State(String name) {
/* 18 */       this.name = name;
/*    */     }
/*    */     
/*    */     public String getName() {
/* 22 */       return this.name;
/*    */     }
/*    */     
/*    */     public static State fromString(String name) {
/* 26 */       for (State state : values()) {
/* 27 */         if (state.name.equalsIgnoreCase(name)) {
/* 28 */           return state;
/*    */         }
/*    */       } 
/* 31 */       throw new IllegalArgumentException("Invalid state: " + name + ". Use 'allow' or 'deny'");
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 36 */       return this.name;
/*    */     }
/*    */   }
/*    */   
/*    */   public StateFlag(String name, boolean defaultAllow) {
/* 41 */     super(name);
/* 42 */     this.defaultValue = defaultAllow ? State.ALLOW : State.DENY;
/*    */   }
/*    */   
/*    */   public StateFlag(String name, boolean defaultAllow, RegionGroup defaultGroup) {
/* 46 */     super(name, defaultGroup);
/* 47 */     this.defaultValue = defaultAllow ? State.ALLOW : State.DENY;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public State getDefault() {
/* 53 */     return this.defaultValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public State parseInput(String input) throws IllegalArgumentException {
/* 58 */     return State.fromString(input);
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement serialize(State value) {
/* 63 */     return (JsonElement)new JsonPrimitive(value.getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public State deserialize(JsonElement element) {
/* 68 */     return State.fromString(element.getAsString());
/*    */   }
/*    */   
/*    */   public static State combine(State first, State second) {
/* 72 */     if (first == State.DENY || second == State.DENY) {
/* 73 */       return State.DENY;
/*    */     }
/* 75 */     if (first == State.ALLOW || second == State.ALLOW) {
/* 76 */       return State.ALLOW;
/*    */     }
/* 78 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\flag\StateFlag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */