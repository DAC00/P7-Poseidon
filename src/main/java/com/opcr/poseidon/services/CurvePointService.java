package com.opcr.poseidon.services;

import com.opcr.poseidon.domain.CurvePoint;
import com.opcr.poseidon.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Get all the CurvePoint from de database.
     *
     * @return List of all the CurvePoint.
     */
    public List<CurvePoint> getCurvePoints() {
        return curvePointRepository.findAll();
    }

    /**
     * Get the CurvePoint with the id curvePointId.
     *
     * @param curvePointId of the CurvePoint to find.
     * @return CurvePoint with the id curvePointId.
     */
    public Optional<CurvePoint> getCurvePointById(Integer curvePointId) {
        return curvePointRepository.findById(curvePointId);
    }

    /**
     * Save the CurvePoint in the database.
     *
     * @param curvePointToSave is the CurvePoint to save.
     */
    public void saveCurvePoint(CurvePoint curvePointToSave) {
        curvePointRepository.save(curvePointToSave);
    }

    /**
     * Update the CurvePoint with id curvePointId.
     *
     * @param curvePointId      of the CurvePoint to update.
     * @param curvePointUpdated is the CurvePoint with updated information.
     */
    public void updateCurvePointById(Integer curvePointId, CurvePoint curvePointUpdated) {
        Optional<CurvePoint> oldCurvePoint = getCurvePointById(curvePointId);
        if (oldCurvePoint.isPresent() && curvePointId.equals(curvePointUpdated.getId())) {
            curvePointRepository.save(curvePointUpdated);
        }
    }

    /**
     * Delete the CurvePoint with id curvePointId.
     *
     * @param curvePointId of the CurvePoint to delete.
     */
    public void deleteCurvePointById(Integer curvePointId) {
        curvePointRepository.deleteById(curvePointId);
    }
}
