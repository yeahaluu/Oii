package com.ssafy.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.service.MapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

@Api(value = "지도 API", tags = { "Map." })
@RestController
@RequestMapping("/")
public class MapController {
	@Autowired
	MapService service;
	
	@PostMapping("/map")
	public ResponseEntity<?> findMid(
		// 프론트에서 어떻게 받냐 :  userPositionLst = [10, 5];
		@ApiParam(value = "위치정보", required = false)
	
		@RequestBody List<Double> list) throws Exception {
		
		System.out.println("받은 데이터" + list);
		
		double latitude = list.get(0);
		double longitude = list.get(1);
		
		service.findStation(latitude, longitude);
		
//		int size = list.size();
//		System.out.println(size+"명의 데이터");
//
//		for(Map<String, Object> info : list) {
//			
//				latitude += (double)info.get("lat");
//				longitude += (double)info.get("lng");
//			
//		}
//		
//		latitude /= size;
//		longitude /= size;
//
//		System.out.println(latitude + ", "+ longitude);
//		
		
		return ResponseEntity.status(200).body("success");
	}
//
//	@PostMapping("/three")
//	public ResponseEntity<?> findMid(
//			@RequestBody @ApiParam(value = "3명", required = true) ThreeLocPostReq threeLocPostReq) {
//
//
//		float oneLati = threeLocPostReq.getFirstLatitude();
//		float oneLong = threeLocPostReq.getFirstLongitude();
//		
//		float twoLati = threeLocPostReq.getSecondLatitude();
//		float twoLong = threeLocPostReq.getSecondLongitude();
//		
//		float threeLati = threeLocPostReq.getThirdLatitude();
//		float threeLong = threeLocPostReq.getThirdLongitude();
//		
//		latitude =  (oneLati + twoLati + threeLati)/3;
//		longitude =  (oneLong + twoLong + threeLong)/3;
//		
//		service.findStation(latitude, longitude);
//		
//		return ResponseEntity.status(200).body("Hello World");
//	}
//
//	@PostMapping("/four")
//	public ResponseEntity<?> findMid(
//			@RequestBody @ApiParam(value = "4명", required = true) FourLocPostReq fourLocPostReq) {
//		float oneLati = fourLocPostReq.getFirstLatitude();
//		float oneLong = fourLocPostReq.getFirstLongitude();
//		
//		float twoLati = fourLocPostReq.getSecondLatitude();
//		float twoLong = fourLocPostReq.getSecondLongitude();
//		
//		float threeLati = fourLocPostReq.getThirdLatitude();
//		float threeLong = fourLocPostReq.getThirdLongitude();
//		
//		float fourLati = fourLocPostReq.getFourthLatitude();
//		float fourLong = fourLocPostReq.getFourthLongitude();
//		
//		latitude =  (oneLati + twoLati + threeLati + fourLati)/4;
//		longitude =  (oneLong + twoLong + threeLong + fourLong)/4;
//		
//		service.findStation(latitude, longitude);
//		
//		return ResponseEntity.status(200).body("Hello World");
//	}
//
//	@PostMapping("/five")
//	public ResponseEntity<?> findMid(
//			@RequestBody @ApiParam(value = "5명", required = true) FiveLocPostReq fiveLocPostReq) {
//		
//		float oneLati = fiveLocPostReq.getFirstLatitude();
//		float oneLong = fiveLocPostReq.getFirstLongitude();
//		
//		float twoLati = fiveLocPostReq.getSecondLatitude();
//		float twoLong = fiveLocPostReq.getSecondLongitude();
//		
//		float threeLati = fiveLocPostReq.getThirdLatitude();
//		float threeLong = fiveLocPostReq.getThirdLongitude();
//		
//		float fourLati = fiveLocPostReq.getFourthLatitude();
//		float fourLong = fiveLocPostReq.getFourthLongitude();
//		
//		float fiveLati = fiveLocPostReq.getFifthLatitude();
//		float fiveLong = fiveLocPostReq.getFifthLongitude();
//		
//		latitude =  (oneLati + twoLati + threeLati + fourLati + fiveLati)/5;
//		longitude =  (oneLong + twoLong + threeLong + fourLong + fiveLong)/5;
//
//		service.findStation(latitude, longitude);
//		
//		return ResponseEntity.status(200).body("Hello World");
//	}
}
