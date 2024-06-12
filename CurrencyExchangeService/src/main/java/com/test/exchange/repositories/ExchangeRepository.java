package com.test.exchange.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.exchange.dto.Exchange;


@Repository
public interface ExchangeRepository extends JpaRepository<Exchange,Long> {
	
	 List<Exchange> findBySourceCurrencyAndTargetCurrencyAndRate(String SourceCurrency,String TargetCurrency,Double rate);
	 
}