package com.opcr.poseidon.services;

import com.opcr.poseidon.domain.Trade;
import com.opcr.poseidon.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public List<Trade> getTrades() {
        return tradeRepository.findAll();
    }

    public Optional<Trade> getTradeById(Integer tradeId) {
        return tradeRepository.findById(tradeId);
    }

    public void saveTrade(Trade tradeToSave) {
        tradeRepository.save(tradeToSave);
    }

    public void updateTradeById(Integer tradeId, Trade tradeUpdated) {
        Optional<Trade> oldTrade = getTradeById(tradeId);
        if (oldTrade.isPresent() && tradeId.equals(tradeUpdated.getId())) {
            tradeRepository.save(tradeUpdated);
        }
    }

    public void deleteTradeById(Integer tradeId) {
        tradeRepository.deleteById(tradeId);
    }
}
