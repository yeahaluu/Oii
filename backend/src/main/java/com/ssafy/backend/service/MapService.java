package com.ssafy.backend.service;

import java.util.List;

import com.ssafy.backend.db.Subway;

public interface MapService {
	List<Subway> findStation(double latitude, double longitude);
}
