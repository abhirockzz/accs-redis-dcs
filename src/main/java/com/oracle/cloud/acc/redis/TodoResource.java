package com.oracle.cloud.acc.redis;

import java.net.URI;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import redis.clients.jedis.Jedis;

@Path("todos")
public class TodoResource {

    static String REDIS_HOST = System.getenv().getOrDefault("REDIS_HOST", "192.168.99.100");
    static String REDIS_PORT = System.getenv().getOrDefault("REDIS_PORT", "6379");

    @GET
    @Path("{id}")
    public Response getByID(@PathParam("id") String id) {
        Response resp = null;
        Todo todo = null;
        try (Jedis client = new Jedis(REDIS_HOST, Integer.valueOf(REDIS_PORT))) {
            todo = new TodoManager(client).findByID(id);
            resp = Response.ok(todo).build();
        } catch (Exception e) {
            resp = Response.serverError().entity("Could not find ToDo due to " + e.getMessage()).build();
        }

        return resp;
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response createTodo(String todoInfo) {

        Response resp = null;
        String todoID = null;
        try (Jedis client = new Jedis(REDIS_HOST, Integer.valueOf(REDIS_PORT))) {
            String name = todoInfo.split(":")[0];
            String detail = todoInfo.split(":")[1];
            todoID = new TodoManager(client).create(new Todo(name, detail));
            resp = Response.created(URI.create("todos/" + todoID)).build();
        } catch (Exception e) {
            resp = Response.serverError().entity("Could not create ToDo due to " + e.getMessage()).build();
        }

        return resp;

    }
}
