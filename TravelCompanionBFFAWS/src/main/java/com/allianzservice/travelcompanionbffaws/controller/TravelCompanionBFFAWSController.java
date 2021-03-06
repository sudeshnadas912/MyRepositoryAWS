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
package com.allianzservice.travelcompanionbffaws.controller;

import java.time.LocalDate;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.allianzservice.travelcompanionbffaws.model.User;
import com.allianzservice.travelcompanionbffaws.service.TravelCompanionBFFService;
import com.allianzservice.travelcompanionbffaws.util.MakeServiceCalls;
import com.google.gson.JsonSyntaxException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Travel Companion Backend Service Outer Layer", description = "backend Service calls for TravelCompanion Application")
public class TravelCompanionBFFAWSController {

	@Autowired
	private TravelCompanionBFFService productService;

	@Autowired
	private MakeServiceCalls makeServiceCalls;

	private static final Logger LOGGER = Logger.getLogger(TravelCompanionBFFAWSController.class.getName());
	
	

	public TravelCompanionBFFAWSController(TravelCompanionBFFService productService,
			MakeServiceCalls makeServiceCalls) 
	{
		this.productService = productService;
		this.makeServiceCalls = makeServiceCalls;
	}

	// To get the product info from inner layer
	@ApiOperation("To get Product and components from APL Service")
	@GetMapping("/Product/{productName}")
	public String getProductInfo(@PathVariable String productName)
	{	String response = "";
		LOGGER.debug("in get Product Info call");

		 
		try {
			response = makeServiceCalls.convertObjectToJSON(productService.getProductInfo(productName)).toString();
		} catch (JSONException e) {
			LOGGER.info(e);
			response = e.getMessage() + " AWS layer";
		}
	
		return response;

	}

	// create an policy in CISL from Inner Layer
	@ApiOperation("To file the insurance policy")
	@PostMapping(value = "/fileMobilityInsurance")
	public boolean fileMobilityInsurance(@RequestBody User user) 
	{	user.setFiledDate(LocalDate.now().toString());
		try 
		{ return productService.fileMobilityInsurance(user);
		} catch (Exception ex) 
		{	LOGGER.info(ex);
			return false;
		}
	}

	// To get the quote by sending the configuration
	@ApiOperation("To file the insurance policy")
	@PostMapping(value = "/getTheQuote")
	public String getTheQuote(@RequestBody User user) 
	{	user.setFiledDate(LocalDate.now().toString());
		try 
		{return productService.getTheQuote(user);
		} catch (Exception ex) 
		{	LOGGER.info(ex);
			return "error Ocuured while getting quote from inner layer";
		}
	}

}
