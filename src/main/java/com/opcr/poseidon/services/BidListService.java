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

    /**
     * Get all the BidList from de database.
     *
     * @return List of all the BidList.
     */
    public List<BidList> getBidLists() {
        return bidListRepository.findAll();
    }

    /**
     * Get the BidList with the id bidListId.
     *
     * @param bidListId of the BidList to find.
     * @return BidList with the id bidListId.
     */
    public Optional<BidList> getBidListById(Integer bidListId) {
        return bidListRepository.findById(bidListId);
    }

    /**
     * Save the BidList in the database.
     *
     * @param bidListToSave is the BidList to save.
     */
    public void saveBidList(BidList bidListToSave) {
        bidListRepository.save(bidListToSave);
    }

    /**
     * Update the BidList with id bidListId.
     *
     * @param bidListId      of the BidList to update.
     * @param bidListUpdated is the BidList with updated information.
     */
    public void updateBidListById(Integer bidListId, BidList bidListUpdated) {
        Optional<BidList> oldBidList = getBidListById(bidListId);
        if (oldBidList.isPresent() && bidListId.equals(bidListUpdated.getId())) {
            bidListRepository.save(bidListUpdated);
        }
    }

    /**
     * Delete the BidList with id bidListId.
     *
     * @param bidListId of the User to delete.
     */
    public void deleteBidListById(Integer bidListId) {
        bidListRepository.deleteById(bidListId);
    }
}
