package com.test.exchange.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.exchange.dto.Exchange;
import com.test.exchange.repositories.ExchangeRepository;

@Service
public class ExchangeService {

	@Autowired
	private ExchangeRepository exchangeRepository;

	public Exchange createExchange(Exchange exchange) {

		return exchangeRepository.save(exchange);
	}

	public Exchange getExchangeById(Long id) {
		Optional<Exchange> optionalExchange = exchangeRepository.findById(id);
		return optionalExchange.get();
	}

	public Exchange updateExchange(Exchange updatedExchange) {
			return exchangeRepository.save(updatedExchange);
	}

	public List<Exchange> findExchangeBySourceCurrencyAndTargetCurrencyAndRate(String sourceCurrency,
			String targetCurrency, Double rate) {
		return exchangeRepository.findBySourceCurrencyAndTargetCurrencyAndRate(sourceCurrency, targetCurrency, rate);
	}

}
