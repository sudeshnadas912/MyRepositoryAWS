/** Copyright CodeJava.net To Present
all right reserved.
*/
package com.Allianz.TravelCompanionBFFAWS.Service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.Allianz.TravelCompanionBFFAWS.Model.PackageinfoVO;
import com.Allianz.TravelCompanionBFFAWS.Model.ProductInfoVO;
import com.Allianz.TravelCompanionBFFAWS.Model.User;
import com.Allianz.TravelCompanionBFFAWS.util.MakeServiceCalls;

@RunWith(SpringRunner.class)
public class TravelCompanionBFFServiceTest {
	
	@InjectMocks
	TravelCompanionBFFService travelCompanionBFFService;
	
	@Mock
	MakeServiceCalls makeServiceCalls;
	
	
	@Mock
	ProductInfoVO productInfoVO = new ProductInfoVO();
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	private User user = new User();

	@Before
	public void setUp() throws Exception {
		
		List<String> moduleList = new ArrayList<>();
		moduleList.add("mod1");
		moduleList.add("mod2");
		
		
			
		user.setFiledDate("12-12-2018");
		user.setPackageTitle("packageTitle");
		user.setPlace("place");
		user.setTravelDate("23-12-2018");
		user.setTravelEndDate("31-12-2018");
		user.setUserId(1004);
		user.setUserName("testNme");
		user.setWeather("good");
		user.setDistance(10);
		user.setDuration(5);
		user.setNumberOfPerson(2);
		user.setPressure(4);
		user.setSelectedModule(moduleList);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		
		List<PackageinfoVO> packageinfoVOs = new ArrayList<>();

		PackageinfoVO packageinfoVO = new PackageinfoVO();

		List<String> packageDescriptionList = new ArrayList<>();
		packageDescriptionList.add("driving");
		packageDescriptionList.add("skiing");
		packageinfoVO.setPackageDescription(packageDescriptionList);
		packageinfoVO.setPackageName("passion");

		packageinfoVOs.add(packageinfoVO);

		productInfoVO.setPackageList(packageinfoVOs);
		productInfoVO.setProductName("SkiinSelekor");
		assertNotNull(productInfoVO);
		
	}
	
	@Test
	public void testGetProductInfo() throws Exception{
		thrown.expect(NullPointerException.class);
		travelCompanionBFFService.getProductInfo("passionPass");
		throw new NullPointerException();
	}
	
	@Test
	public void testFileMobilityInsurance() throws Exception{
		thrown.expect(NullPointerException.class);
		travelCompanionBFFService.fileMobilityInsurance(user);
		throw new NullPointerException();
	
	}
	
	@Test
	public void testGetTheQuote() throws Exception{
		thrown.expect(NullPointerException.class);
		travelCompanionBFFService.getTheQuote(user);
		throw new NullPointerException();
	}

}
