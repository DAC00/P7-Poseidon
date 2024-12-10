package com.opcr.poseidon.services;

import com.opcr.poseidon.domain.BidList;
import com.opcr.poseidon.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    public List<BidList> getBidLists() {
        return bidListRepository.findAll();
    }

    public Optional<BidList> getBidListById(Integer bidListId) {
        return bidListRepository.findById(bidListId);
    }

    public void saveBidList(BidList bidListToSave) {
        bidListRepository.save(bidListToSave);
    }

    public void updateBidListById(Integer bidListId, BidList bidListUpdated) {
        Optional<BidList> oldBidList = getBidListById(bidListId);
        if (oldBidList.isPresent() && bidListId.equals(bidListUpdated.getId())) {
            bidListRepository.save(bidListUpdated);
        }
    }

    public void deleteBidListById(Integer bidListId) {
        bidListRepository.deleteById(bidListId);
    }
}
