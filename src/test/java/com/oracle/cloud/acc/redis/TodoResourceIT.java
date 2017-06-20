package com.oracle.cloud.acc.redis;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Rule;
import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import static com.lordofthejars.nosqlunit.redis.RemoteRedisConfigurationBuilder.newRemoteRedisConfiguration;
import com.lordofthejars.nosqlunit.redis.RedisRule;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;

public class TodoResourceIT {

    static String REDIS_HOST = System.getenv().getOrDefault("REDIS_HOST", "192.168.99.100");
    static String REDIS_PORT = System.getenv().getOrDefault("REDIS_PORT", "6379");

    @Rule
    public RedisRule redisRule = new RedisRule(newRemoteRedisConfiguration().host(REDIS_HOST)
                                                                            .port(Integer.valueOf(REDIS_PORT)).build());
    
    static String accsAppBaseURL;
    static String root = "todos";

    @BeforeClass
    public static void setUpClass() {
        accsAppBaseURL = System.getenv().getOrDefault("ACCS_APP_BASE_URL","http://localhost:8080");
        System.out.println("Base REST URL "+ accsAppBaseURL);
    }

    @AfterClass
    public static void tearDownClass() {
        accsAppBaseURL = null;
    }

    Client client;
    WebTarget target;

    @Before
    public void setUp() {
        client = ClientBuilder.newClient();
        target = client
                .register(MoxyJsonFeature.class)
                .target(accsAppBaseURL);
        
        System.out.println("JAX-RS client setup");
    }

    @After
    public void tearDown() {
        client.close();
        System.out.println("JAX-RS client closed");
    }

    @Test
    public void createTodo() {
        System.out.println("Connecting to "+ REDIS_HOST);
        Response response = target.path(root)
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.text("hello:todo"));
        
        Assert.assertEquals("ToDo creation failed", 201, response.getStatus());

    }

    @Test
    @UsingDataSet(locations = "todo.json", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
    public void findTodoById() {
        System.out.println("Connecting to "+ REDIS_HOST);
        String todoID = "99";

        Response response = target.path(root)
                .path(todoID)
                .request().get();

        System.out.println("Response status " + response.getStatus());
        assertEquals(String.format("Could not ToDo with id %s", todoID), 200, response.getStatus());

        Todo todo = response.readEntity(Todo.class);
        System.out.println("Found ToDo " + todo);

        String expectedName = "write code";
        String expectedDetail = "always be coding!";

        assertEquals("ToDo name must be " + expectedName, expectedName, todo.getName());
        assertEquals("ToDo detail must be " + expectedDetail, expectedDetail, todo.getDetail());
        

    }

}
