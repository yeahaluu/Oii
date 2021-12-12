package com.ssafy.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.backend.db.Subway;

public interface SubwayRepository extends JpaRepository<Subway, Integer>{
	@Query(value = "SELECT *,(6371*acos(cos(radians(:lat))*cos(radians(s.latitude))*cos(radians(s.longitude) -radians(:lng))+sin(radians(:lat))*sin(radians(s.latitude)))) AS distance FROM subway s HAVING distance <= 1.5 ORDER BY distance;", nativeQuery = true)
	List<Subway> findSubway(@Param("lat") double lat, @Param("lng") double lng);
    
}