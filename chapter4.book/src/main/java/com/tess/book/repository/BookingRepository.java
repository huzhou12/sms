package com.tess.book.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.tess.book.entity.BookingRecord;

public interface BookingRepository extends JpaRepository<BookingRecord, Long> {
	
}
