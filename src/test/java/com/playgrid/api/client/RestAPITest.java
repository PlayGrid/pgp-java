package com.playgrid.api.client;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;



@RunWith(JUnit4.class)
public class RestAPITest {

	RestAPI apiclient;
	
	@Before
	public void setUp() {
		apiclient = new RestAPI("05e7234457ccfa5ea0839ec6b38b5b2b05f822e4", 
								"http://local.playgrid.com:8000"
								);
	}
	
	
	
	@Test
    public void testRestAPI() {
        Object obj = apiclient.getGames();
//        Object obj = response.getEntity();
        System.out.println(obj);
//        System.out.println(response.getEntity(GameListResponse.class));
//        System.out.println(response.getStatus());
    }
}
