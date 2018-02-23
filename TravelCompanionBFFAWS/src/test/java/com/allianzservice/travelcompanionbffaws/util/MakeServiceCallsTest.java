/** Copyright CodeJava.net To Present
all right reserved.
*/
package com.allianzservice.travelcompanionbffaws.util;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.allianzservice.travelcompanionbffaws.model.User;

public class MakeServiceCallsTest {
	
	 @Mock
	    HttpURLConnection mockHttpConnection;
	 

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMakeGetCall() throws Exception {
		String url = "/contracts";
		URI uri = new URI(url);
		final Builder builderMock = Mockito.mock(Builder.class);
		Response mockResponse = Mockito.mock(Response.class);
		Mockito.when(builderMock.get()).thenReturn(mockResponse);
		WebTarget webTargetMock = mock(WebTarget.class);
		Mockito.when(webTargetMock.request(MediaType.APPLICATION_JSON_TYPE)).thenReturn(builderMock);
		final WebTarget mockWebTarget = Mockito.mock(WebTarget.class);
		Mockito.when(mockWebTarget.path(Matchers.anyString())).thenReturn(mockWebTarget);
		Mockito.when(mockWebTarget.getUri()).thenReturn(uri);
		Mockito.when(mockWebTarget.request()).thenReturn(builderMock);
		MakeServiceCalls makeServiceCalls = new MakeServiceCalls();
		Response response = makeServiceCalls.makeGetCall(mockWebTarget);
		assertNotNull(response);
	}

	@Test
	public void testMakeHttpRestCallNegative() throws Exception {
		MakeServiceCalls makeServiceCalls = new MakeServiceCalls();
		User user = new User();
		user.setDistance(5);
		user.setDuration(4);
		JSONObject jsonObject = makeServiceCalls.convertObjectToJSON(user);
		thrown.expect(IOException.class);
		makeServiceCalls.makeHttpRestCall(jsonObject, "/offerings", "POST");
		throw new IOException();
	}

	@Test
	public void testMakeHttpRestCallPositive() throws Exception {
		MakeServiceCalls makeServiceCalls = new MakeServiceCalls();
		User user = new User();
		user.setDistance(5);
		user.setDuration(4);
		JSONObject jsonObject = makeServiceCalls.convertObjectToJSON(user);
		String responseStr = makeServiceCalls.makeHttpRestCall(jsonObject, "https://www.google.com", "GET");
		assertNotNull(responseStr);
	}

	@Test
	public void testConvertObjectToJSON() throws JSONException {
		User user = new User();
		user.setDistance(6);
		MakeServiceCalls makeServiceCalls = new MakeServiceCalls();
		assertNotNull(makeServiceCalls.convertObjectToJSON(user));
	}

}
