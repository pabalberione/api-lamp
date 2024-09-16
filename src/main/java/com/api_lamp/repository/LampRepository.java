package com.api_lamp.repository;

import com.api_lamp.entity.Lamp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LampRepository extends JpaRepository<Lamp, Long> {
}
