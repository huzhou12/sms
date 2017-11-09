package com.tess.checkin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tess.checkin.entity.CheckInRecord;

public interface CheckinRepository extends JpaRepository<CheckInRecord,Long> {

}
