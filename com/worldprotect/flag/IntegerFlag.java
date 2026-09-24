/*    */ package com.worldprotect.flag;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class IntegerFlag
/*    */   extends Flag<Integer> {
/*    */   private final Integer defaultValue;
/*    */   
/*    */   public IntegerFlag(String name) {
/* 12 */     super(name);
/* 13 */     this.defaultValue = null;
/*    */   }
/*    */   
/*    */   public IntegerFlag(String name, Integer defaultValue) {
/* 17 */     super(name);
/* 18 */     this.defaultValue = defaultValue;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Integer getDefault() {
/* 24 */     return this.defaultValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public Integer parseInput(String input) throws IllegalArgumentException {
/*    */     try {
/* 30 */       return Integer.valueOf(Integer.parseInt(input));
/* 31 */     } catch (NumberFormatException e) {
/* 32 */       throw new IllegalArgumentException("Invalid integer: " + input);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement serialize(Integer value) {
/* 38 */     return (JsonElement)new JsonPrimitive(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public Integer deserialize(JsonElement element) {
/* 43 */     return Integer.valueOf(element.getAsInt());
/*    */   }
/*    */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\flag\IntegerFlag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */