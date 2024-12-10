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

    public List<CurvePoint> getCurvePoints() {
        return curvePointRepository.findAll();
    }

    public Optional<CurvePoint> getCurvePointById(Integer curvePointId) {
        return curvePointRepository.findById(curvePointId);
    }

    public void saveCurvePoint(CurvePoint curvePointToSave) {
        curvePointRepository.save(curvePointToSave);
    }

    public void updateCurvePointById(Integer curvePointId, CurvePoint curvePointUpdated) {
        Optional<CurvePoint> oldCurvePoint = getCurvePointById(curvePointId);
        if (oldCurvePoint.isPresent() && curvePointId.equals(curvePointUpdated.getCurveId())) {
            curvePointRepository.save(curvePointUpdated);
        }
    }

    public void deleteCurvePointById(Integer curvePointId) {
        curvePointRepository.deleteById(curvePointId);
    }
}
