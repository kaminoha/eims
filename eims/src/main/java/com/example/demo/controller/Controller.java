package com.example.demo.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.Customer;
import com.example.demo.model.Files;
import com.example.demo.model.Employee;
import com.example.demo.model.Finance;
import com.example.demo.model.LoginResponse;
import com.example.demo.model.RatioResponse;
import com.example.demo.model.Users;
import com.example.demo.model.Work;
import com.example.demo.payload.UploadFileResponse;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.DBFileRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.FinanceRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WorkRepository;
import com.example.demo.service.DBFileStorageService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class Controller {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private FinanceRepository financeRepository;
	//private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    @Autowired
    private DBFileStorageService dbFileStorageService;
    @Autowired
    private DBFileRepository dbFileRepository;
    @Autowired
    private WorkRepository workRepository;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody Users user){
		String jwtToken = "";
		
		LoginResponse response = new LoginResponse();
		if(user.getUsername() == null || user.getPassword() == null) {
			response.setMessage("Please enter your credentials");
			response.setStatus(false);
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
		
		String username = user.getUsername();
		String password = user.getPassword();
		
		List<Users> reg = userRepository.findByUserNameAndPassword(username, password);
		
		jwtToken = Jwts.builder().setSubject(username).claim("user", user)
				.setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secretKey")
				.compact();
		
		if(Objects.isNull(reg)|| reg.isEmpty()) {
			response.setStatus(false);
			response.setMessage("User doesn't exist");
		}
		else {
			response.setStatus(true);
			response.setMessage("Welcome");
			response.setToken(jwtToken);
		}
		
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getEmp")
	public List<Work> getEmployee(){
		return workRepository.findAll();
	}
	
	@GetMapping("getCustomer")
	public List<Finance> getCustomer(){
		return financeRepository.findAll();
	}
	@GetMapping("getFinance")
	public List<Finance> getFinance(){
		return financeRepository.findAll();
	}
	
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        Files dbFile = dbFileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }
    
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        Files dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
    @GetMapping("/displayFile")
    public List<Files> getFile(){
    	return dbFileRepository.findFiles();
    }
    @GetMapping("/genderRatio")
    public ResponseEntity<RatioResponse> getRatio(){
    	RatioResponse response = new RatioResponse(); 
    	
    	long male = employeeRepository.countByGender("Male");
    	long female = employeeRepository.countByGender("Female");
    	
    	response.setFemale(female);
    	response.setMale(male);
    	
    	return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/topEmp")
    public List<Employee> getTopEmp(){
    	return employeeRepository.findTop5ByOrderByFinance_RevenueDesc();
    }
    @GetMapping("/topLocation")
    public ResponseEntity<List<Object>> getTopLocation(){
    	List<Object[]> results = customerRepository.findByAddress();
    	List<Object> locationList = new ArrayList<>();
    	String[] regionOne = {"Ilocos Norte", "Ilocos Sur", "La Union", "Pangasinan"};
    	String[] regionTwo = {"Batanes", "Cagayan", "Isabela", "Nueva Vizcaya", "Quirino", "Tuguegarao"};
    	String[] regionThree = {"Aurora", "Bataan", "Bulacan", "Nueva Ecija", "Pampanga", 
    			"Tarlac", "Zambales"};
    	String[] cal = {"Batangas", "Cavite", "Laguna", "Quezon", "Rizal"};
    	String[] mima = {"Marinduque", "Occidental Mindoro", "Palawan", "Romblon"};
    	String[] regionFive = {"Albay", "Camarines Norte", "Camarines Sur", "Catanduanes", 
    			"Masbate", "Sorsogon"};
    	String[] regionSix = {"Aklan", "Antique", "Capiz", "Guimaras", "Iloilo", "Negros Occidental"};
    	String[] regionSeven = {"Bohol", "Cebu", "Negros Oriental", "Siquijor"};
    	String[] regionEight = {"Biliran", "Eastern Samar", "Leyte", "Northern Samar", "Samar",
    			"Southern Leyte"};
    	String[] ncr = {"Caloocan", "Las Pinas", "Makati", "Malabon", "Mandaluyong", "Manila", "Marikina",
    			"Muntinlupa", "Navotas", "Paranaque", "Pasay", "Pasig", "Pateros", "Quezon City",
    			"San Juan", "Taguig", "Valenzuela"};
    	String[] regionNine = {"Zamboanga del Norte", "Zamboanga del Sur", "Zamboanga Sibugay"};
    	String[] regionTen = {"Bukidnon", "Camiguin", "Lanao del Norte", 
    			"Misamis Occidental", "Misamis Oriental", "Cagayan de Oro"};
    	String[] regionEleven = {"Compostela Valley", "Davao del Norte", "Davao del Sur",
    			"Davao Occidental", "Davao Oriental", "Davao"};
    	String[] regionTwelve = {"Cotabato", "Saragani", "South Cotabato", "Sultan Kudarat", "Koronadal"};
    	String[] regionThirteen = {"Agusan del Norte", "Agusan del Sur", "Dinagat Islands",
    			"Surigao del Norte", "Surigao del Sur", "Butuan"};
    	String[] car = {"Abra", "Apayao", "Benguet", "Ifugao", "Kalinga", "Mountain Province", "Baguio"};
    	String[] armm = {"Basilan", "Lanao del Sur", "Maguindanao", "Tawi-Tawi", "Cotabato"};
    	
    	for(Object[] object : results) {
    		Map<Object, Object> location = new LinkedHashMap<>();
    		location.put("Region", object[0]);
    		location.put("Tally", object[1]);
    		for(String obj : regionOne) {
    			if(object[0].equals(obj)) {
    				location.replace("Region", "Region I");
    				if(location.containsValue("Region I")) {
    					location.compute("Tally", (k, oldVal) -> (oldVal == null ? 0: 
    						((BigInteger) oldVal).add((BigInteger) object[1])));
    					location.merge("Region", "Region I", (prev, newVal) -> newVal);
    				}
    			}
    		}
    		for(String obj : regionTwo) {
    			if(object[0].equals(obj)) {
    				location.replace("Region", "Region II");
    			}
    		}
    		for(String obj : regionThree) {
    			if(object[0].equals(obj)) {
    				location.replace("Region", "Region III");
    			}
    		}
    		for(String obj : cal) {
    			if(object[0].equals(obj)) {
    				location.replace("Region", "Calabarzon");
    			}
    		}
    		for(String obj : mima) {
    			if(object[0].equals(obj)) {
    				location.replace("Region", "MIMAROPA");
    			}
    		}
    		for(String obj : regionFive) {
    			if(object[0].equals(obj)) {
    				location.replace("Region", "Region V");
    			}
    		}
    		for(String obj : regionSix) {
    			if(object[0].equals(obj)) {
    				location.replace("Region", "Region VI");
    			}
    		}
    		for(String obj : regionSeven) {
    			if(object[0].equals(obj)) {
    				location.replace("Region", "Region VII");
    			}
    		}
    		for(String obj : regionEight) {
    			if(object[0].equals(obj)) {
    				location.replace("Region", "Region VIII");
    			}
    		}
    		for(String obj : ncr) {
    			if(object[0].equals(obj)) {
    				location.replace("Region", "NCR");
    			}
    		}
    		for(String obj : regionNine) {
    			if(object[0].equals(obj)) {
    				location.replace("Region", "Region IX");
    			}
    		}
    		for(String obj : regionTen) {
    			if(object[0].equals(obj)) {
    				location.replace("Region", "Region X");
    			}
    		}
    		for(String obj : regionEleven) {
    			if(object[0].equals(obj)) {
    				location.replace("Region", "Region XI");
    			}
    		}
    		for(String obj : regionTwelve) {
    			if(object[0].equals(obj)) {
    				location.replace("Region", "Region XII");
    			}
    		}
    		for(String obj : regionThirteen) {
    			if(object[0].equals(obj)) {
    				location.replace("Region", "Region XIII");
    			}
    		}
    		for(String obj : car) {
    			if(object[0].equals(obj)) {
    				location.replace("Region", "CAR");
    			}
    		}
    		for(String obj : armm) {
    			if(object[0].equals(obj)) {
    				location.replace("Region", "ARMM");
    			}
    		}
    		locationList.add(location);
    	}
    	return new ResponseEntity<>(locationList, HttpStatus.OK);
    }
    @GetMapping("/topCustomer")
    public List<Customer> getTopCustomer(){
    	return customerRepository.findTop10ByOrderByFinance_ProfitDesc();
    }
    @GetMapping("/topFinancial")
    private List<Customer> getTopFinancial(){
    	return customerRepository.findTop5ByOrderByFinance_RevenueDesc();
    }
    @GetMapping("/topCurrency")
    private ResponseEntity<List<Object>> getTopCurrency(){
    	List<Object[]> results = financeRepository.findByCurrency();
    	List<Object> currencyList = new ArrayList<>();
    	
    	for(Object[] object : results) {
    		Map<Object, Object> currencies = new LinkedHashMap<>();
    		currencies.put("Currency", object[0]);
    		currencies.put("Tally", object[1]);
    		
    		currencyList.add(currencies);
    	}
    	return new ResponseEntity<>(currencyList, HttpStatus.OK);
    }
    @GetMapping("/salesTrend")
    private ResponseEntity<List<Object>> salesTrend(){
    	//int[] profit = {1,2,3,4,5,6,7,8,9,10,11,12,13};
    	List<Object> profitList = new ArrayList<>();
    	
    	//for(int i = 1; i < profit.length; i++) {
    		List<Object[]> results = customerRepository.findByProfit();
    		for(Object[] obj : results) {
    			Map<Object, Object> profits = new LinkedHashMap<>();
    			profits.put("Month", obj[0]);
    			profits.put("Profit", obj[1]);
    			
    			profitList.add(profits);
    		}
    	//}
    	return new ResponseEntity<>(profitList, HttpStatus.OK);
    }
}
