/*******************************************************************************
 * Copyright 2018 TCS
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.allianzservice.travelcompanionbffaws.service;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allianzservice.travelcompanionbffaws.model.ProductInfoVO;
import com.allianzservice.travelcompanionbffaws.model.User;
import com.allianzservice.travelcompanionbffaws.util.MakeServiceCalls;

@Service
public class TravelCompanionBFFService 
{

	

	@Autowired
	private MakeServiceCalls makeServiceCalls;

	@Autowired
	private ProductInfoVO productInfoVO;

	private static final String OUTERLAYERBASEURL = "https://groupreferenceplatform-dev.allianz.de";
	private static final String FILEINSURANCEURL = "/fileMobilityInsurance";
	private static final String FETCHPRODUCTURL = "/Product/";
	private static final String FETCHQUOTEURL = "/getTheQuote";
	private static final Logger LOGGER = Logger.getLogger(TravelCompanionBFFService.class.getName());

	public TravelCompanionBFFService() {
		// Do Nothing
	}

	// To get the Product structure from APL
	public ProductInfoVO getProductInfo(String productName) 
	{	// Set the product name for the ProductVO
		Client client = ClientBuilder.newClient();
		String url = OUTERLAYERBASEURL + FETCHPRODUCTURL + productName;
		try {
			Response response = makeServiceCalls.makeGetCall(client.target(url));
			productInfoVO = response.readEntity(ProductInfoVO.class);
		} catch (Exception e) {
			LOGGER.info(e);
		}

		return productInfoVO;
	}

	// To make fetch the contract details and file a policy for that contract
	public boolean fileMobilityInsurance(User user) 
	{	boolean status = true;
		String urlToBePassed = OUTERLAYERBASEURL + FILEINSURANCEURL;
		try {
			JSONObject jsonObject = new JSONObject(makeServiceCalls
					.makeHttpRestCall(makeServiceCalls.convertObjectToJSON(user), urlToBePassed, HttpMethod.POST));
			status = jsonObject.getBoolean("status");
		} catch (JSONException e) {
			LOGGER.info(e);
		}

		return status;
	}

	// To get the quote from the inner Layer
	public String getTheQuote(User user) 
	{	String response = "";
		String urlToBePassed = OUTERLAYERBASEURL + FETCHQUOTEURL;
		try {
			response = makeServiceCalls.makeHttpRestCall(makeServiceCalls.convertObjectToJSON(user), urlToBePassed,
					HttpMethod.POST);
			JSONObject jsonObject = new JSONObject(response);
			response = jsonObject.toString();
		} catch (JSONException e) {
			LOGGER.info(e);
		}
		return response;
	}

}
