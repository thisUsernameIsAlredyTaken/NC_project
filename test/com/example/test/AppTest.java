package com.example.test;

import com.example.project.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class AppTest {

    private static final String adminAuth = "Basic X19BRE1JTjoxMjM=";
    private static final String userAuth = "Basic dXNlcjoxMjM=";

    @Autowired
    private MockMvc mockMvc;
//
//    @LocalServerPort
//    int randomServerPort;
//
//    @Autowired
//    private TestRestTemplate restTemplate;

    @Test
    public void testGetMovie() throws Exception {
        mockMvc.perform(get("/movie/p/m0000000001")).andExpect(status().is(200));
    }

    @Test
    public void testGetUserUnauthorized() throws Exception {
        mockMvc.perform(get("/user/p/u0000000001")).andExpect(status().is(401));
    }

    @Test
    public void testFindUserUnauthorized() throws Exception {
        mockMvc.perform(get("/user/find?username=user")).andExpect(status().is(401));
    }

    @Test
    public void testGetUserAuthorizedAdmin() throws Exception {
        mockMvc.perform(get("/user/p/u0000000001").header("Authorization", adminAuth))
                .andExpect(status().is(200));
    }

    @Test
    public void testGetFindAuthorizedAdmin() throws Exception {
        mockMvc.perform(get("/user/find?username=user").header("Authorization", adminAuth))
                .andExpect(status().is(200));
    }

    @Test
    public void testGetUserAuthorizedUser() throws Exception {
        mockMvc.perform(get("/user/p/u0000000001").header("Authorization", userAuth))
                .andExpect(status().is(403));
    }

    @Test
    public void testGetFindUserAuthorizedUser() throws Exception {
        mockMvc.perform(get("/user/find?username=user").header("Authorization", userAuth))
                .andExpect(status().is(403));
    }

    @Test
    public void testGetMeUnauthorized() throws Exception {
        mockMvc.perform(get("/user/me")).andExpect(status().is(401));
    }

    @Test
    public void testGetMeAuthorizedUser() throws Exception {
        mockMvc.perform(get("/user/me").header("Authorization", userAuth))
                .andExpect(status().is(200));
    }

    @Test
    public void testGetMeAuthorizedAdmin() throws Exception {
        mockMvc.perform(get("/user/me").header("Authorization", adminAuth))
                .andExpect(status().is(200));
    }

    @Test
    public void testGetMeWatchedAuthorized() throws Exception {
        mockMvc.perform(get("/user/me/watched").header("Authorization", userAuth))
                .andExpect(status().is(200));
    }

    @Test
    public void testGetMePlannedAuthorized() throws Exception {
        mockMvc.perform(get("/user/me/planned").header("Authorization", userAuth))
                .andExpect(status().is(200));
    }

    @Test
    public void testGetMePlannedUnauthorized() throws Exception {
        mockMvc.perform(get("/user/me/planned"))
                .andExpect(status().is(401));
    }

    @Test
    public void testGetMeWatchedUnauthorized() throws Exception {
        mockMvc.perform(get("/user/me/planned"))
                .andExpect(status().is(401));
    }
}
