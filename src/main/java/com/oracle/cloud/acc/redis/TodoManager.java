package com.oracle.cloud.acc.redis;

import java.util.HashMap;
import java.util.Map;
import redis.clients.jedis.Jedis;

public class TodoManager {
    
    private static final String TODO_KEY_SEQ_NAME = "todo-key-sequence";
    private static final String TODO_TITLE_FIELD = "name";
    private static final String TODO_DESC_FIELD = "detail";
    
    private final Jedis jedis;
    
    public TodoManager(Jedis jedis) {
        this.jedis = jedis;
    }
    
    public String create(Todo todo) {
        
        Map<String, String> fields = new HashMap<>();
       
        String todoID = jedis.incr(TODO_KEY_SEQ_NAME).toString();
        fields.put(TODO_TITLE_FIELD, todo.getName());
        fields.put(TODO_DESC_FIELD, todo.getDetail());
        
        jedis.hmset(todoID, fields);
        System.out.println("Created Todo with ID : "+ todoID);
        
        return todoID;
    }

    public Todo findByID(String id) {
        
        Map<String, String> fields = jedis.hgetAll(id);
        if(fields.isEmpty()){
            return null;
        }
        return new Todo(fields.get(TODO_TITLE_FIELD), fields.get(TODO_DESC_FIELD));        
    }

    
}
