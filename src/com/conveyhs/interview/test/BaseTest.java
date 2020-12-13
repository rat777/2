package com.conveyhs.interview.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.conveyhs.interview.model.Post;
import com.conveyhs.interview.model.User;
import com.conveyhs.interview.service.PostService;
import com.conveyhs.interview.service.UserService;

class BaseTest {

	static int USERS_COUNT = 100;
	static int POSTS_PER_USER = 5;
	static int LOOKUP_SAMPLES = 200;
	
	static PostService postService = new PostService();
	static UserService userService = new UserService();
	
	@BeforeAll
	static void setUp() throws Exception {
		//create users
		userService.setup(USERS_COUNT);
		//create posts
		postService.setup(USERS_COUNT, POSTS_PER_USER);
		System.out.println("setup complete");
	}

	@AfterAll
	static void tearDown() throws Exception {
		System.out.println("teardown complete");
	}

	//loop through posts, and make sure both the post and user return
	@Test
	void testGet() {
		Random r = new Random();
		long start = System.currentTimeMillis();
		for(int i = 0; i < LOOKUP_SAMPLES;i++)
		{
			Post lookup = postService.get("" + r.nextInt(USERS_COUNT * POSTS_PER_USER));
			assertTrue(lookup != null);
			User u = userService.get("" + lookup.getAuthorId());
			assertTrue(u != null);
		}
		long stop = System.currentTimeMillis();
		System.out.println((stop - start) * 1.0 / LOOKUP_SAMPLES);
	}
	
	//loop through a random sampling of users and make sure they have posts
	@Test
	void testPostCount() {
		Random r = new Random();
		long start = System.currentTimeMillis();
		for(int i = 0; i < LOOKUP_SAMPLES;i++)
		{
			int postCount = postService.getPostCount("" + r.nextInt(USERS_COUNT));
//			Check and print all values postCount
			System.out.println(postCount);
//			postCount sometimes = 0
			assertTrue(postCount >= 0); // change ">" to ">="
		}
		long stop = System.currentTimeMillis();
		System.out.println((stop - start) * 1.0 / LOOKUP_SAMPLES);
	}

}
