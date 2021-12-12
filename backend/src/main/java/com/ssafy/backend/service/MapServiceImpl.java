package com.ssafy.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.backend.db.Subway;
import com.ssafy.backend.repository.SubwayRepository;

@Service("MapService")
public class MapServiceImpl implements MapService{

	@Autowired
	SubwayRepository subwayRepository;
	
	@Override
	public List<Subway> findStation(double latitude, double longitude) {
		
		List<Subway> list = new ArrayList<>();

		double startLat = latitude - 0.01;
		double endLat = latitude + 0.01;
		
		double startLng = longitude - 0.01;
		double endLng = longitude + 0.01;
		

		list = subwayRepository.findSubway(latitude, longitude);
		
		System.out.println(list);
		
		return list;
	}
}
