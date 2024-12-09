package com.opcr.poseidon.repositories;

import com.opcr.poseidon.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
