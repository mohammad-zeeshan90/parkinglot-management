package com.example.parkinglot.repository;


import com.example.parkinglot.model.EntryGate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EntryGateRepository extends JpaRepository<EntryGate, Long> {
}