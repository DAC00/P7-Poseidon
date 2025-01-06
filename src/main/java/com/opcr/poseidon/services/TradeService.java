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

    /**
     * Get all the Trade from de database.
     *
     * @return List of all the Trade.
     */
    public List<Trade> getTrades() {
        return tradeRepository.findAll();
    }

    /**
     * Get the Trade with the id tradeId.
     *
     * @param tradeId of the Trade to find.
     * @return Trade with the id tradeId.
     */
    public Optional<Trade> getTradeById(Integer tradeId) {
        return tradeRepository.findById(tradeId);
    }

    /**
     * Save the Trade in the database.
     *
     * @param tradeToSave is the Trade to save.
     */
    public void saveTrade(Trade tradeToSave) {
        tradeRepository.save(tradeToSave);
    }

    /**
     * Update the Trade with id tradeId.
     *
     * @param tradeId      of the Trade to update.
     * @param tradeUpdated is the Trade with updated information.
     */
    public void updateTradeById(Integer tradeId, Trade tradeUpdated) {
        Optional<Trade> oldTrade = getTradeById(tradeId);
        if (oldTrade.isPresent() && tradeId.equals(tradeUpdated.getId())) {
            tradeRepository.save(tradeUpdated);
        }
    }

    /**
     * Delete the Trade with id tradeId.
     *
     * @param tradeId of the Trade to delete.
     */
    public void deleteTradeById(Integer tradeId) {
        tradeRepository.deleteById(tradeId);
    }
}
