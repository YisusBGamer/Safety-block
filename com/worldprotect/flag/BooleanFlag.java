/*    */ package com.worldprotect.flag;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class BooleanFlag
/*    */   extends Flag<Boolean> {
/*    */   private final Boolean defaultValue;
/*    */   
/*    */   public BooleanFlag(String name) {
/* 12 */     super(name);
/* 13 */     this.defaultValue = null;
/*    */   }
/*    */   
/*    */   public BooleanFlag(String name, Boolean defaultValue) {
/* 17 */     super(name);
/* 18 */     this.defaultValue = defaultValue;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Boolean getDefault() {
/* 24 */     return this.defaultValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public Boolean parseInput(String input) throws IllegalArgumentException {
/* 29 */     if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("on"))
/* 30 */       return Boolean.valueOf(true); 
/* 31 */     if (input.equalsIgnoreCase("false") || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("off")) {
/* 32 */       return Boolean.valueOf(false);
/*    */     }
/* 34 */     throw new IllegalArgumentException("Invalid boolean: " + input + ". Use 'true', 'false', 'yes', 'no', 'on', or 'off'");
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement serialize(Boolean value) {
/* 39 */     return (JsonElement)new JsonPrimitive(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public Boolean deserialize(JsonElement element) {
/* 44 */     return Boolean.valueOf(element.getAsBoolean());
/*    */   }
/*    */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\flag\BooleanFlag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */