package com.test.exchange.controllers;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.exchange.dto.Exchange;
import com.test.exchange.models.ExchangeRq;
import com.test.exchange.services.ExchangeService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

	 @Autowired
     private ExchangeService exchangeService;
	 
	@PostMapping("/create")
	public Mono<ResponseEntity<Exchange>>createExchange( @RequestBody ExchangeRq exchangeRq) {
		
		Exchange nuevoExchange = new Exchange();
		nuevoExchange.setSourceCurrency(exchangeRq.getSourceCurrency());
		nuevoExchange.setSourceAmount(exchangeRq.getSourceAmount());
		nuevoExchange.setTargetCurrency(exchangeRq.getTargetCurrency());
		nuevoExchange.setRate(exchangeRq.getRate());
		nuevoExchange.setConvertedAmount(exchangeRq.getRate() * exchangeRq.getSourceAmount());
		nuevoExchange.setCreatedByUser("kuku");
		nuevoExchange.setCreatedAt(LocalDateTime.now());
		Exchange exchange = exchangeService.createExchange(nuevoExchange);
		return Mono.just(ResponseEntity.ok(exchange));
       
    }
	
	@PutMapping("/update/{id}")
    public Mono<ResponseEntity<Exchange>> updateExchange(@PathVariable("id") Long id, @RequestBody Exchange exchangeDTO) {
		Optional<Exchange> exchangeFounded = Optional.ofNullable(exchangeService.getExchangeById(id));
		Exchange exchangeUpdated  = null;
		if (exchangeFounded.isPresent())
		{
			exchangeDTO.setCreatedAt(exchangeFounded.get().getCreatedAt());
			exchangeDTO.setCreatedByUser(exchangeFounded.get().getCreatedByUser());
			exchangeDTO.setConvertedAmount(exchangeDTO.getRate() * exchangeDTO.getSourceAmount());
			exchangeDTO.setUpdatedAt(LocalDateTime.now());
			exchangeDTO.setUpdatedByUser("kuku1");
			exchangeUpdated = exchangeService.updateExchange(exchangeDTO);
		}
        return Mono.just(ResponseEntity.ok(exchangeUpdated));
    }
	
	@PostMapping("/list")
	    public Mono<ResponseEntity<List<Exchange>>> findExchanges(@RequestBody ExchangeRq exchangeRq) {
	        
	        List<Exchange>  exchanges = exchangeService.findExchangeBySourceCurrencyAndTargetCurrencyAndRate(exchangeRq.getSourceCurrency(), exchangeRq.getTargetCurrency(), exchangeRq.getRate());
	        	        
            return Mono.just(ResponseEntity.ok(exchanges));
	    }
	
	
	
}
