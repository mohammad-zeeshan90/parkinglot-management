package com.example.parkinglot.repository;


import com.example.parkinglot.model.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {
}