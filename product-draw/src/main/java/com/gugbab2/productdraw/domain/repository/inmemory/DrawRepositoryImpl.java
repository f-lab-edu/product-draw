package com.gugbab2.productdraw.domain.repository.inmemory;

import com.gugbab2.productdraw.domain.entity.Draw;
import com.gugbab2.productdraw.domain.repository.DrawRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DrawRepositoryImpl implements DrawRepository {
    private final Map<String, Draw> drawsDatabase = new HashMap<>();

    @Override
    public Draw save(Draw draw) {
        drawsDatabase.put(draw.getId(), draw);
        return draw;
    }

    @Override
    public Draw findById(String drawId) {
        return drawsDatabase.get(drawId);
    }

    @Override
    public List<Draw> findAllByEntrantId(String entrantId) {
        List<Draw> draws = new ArrayList<>();

        for(Draw draw : drawsDatabase.values()) {
            if (draw.getEntrantId().equals(entrantId)) {
                draws.add(draw);
            }
        }

        return draws;
    }

    @Override
    public List<Draw> findDrawsByProductId(String productId){
        List<Draw> draws = new ArrayList<>();

        for(Draw draw : drawsDatabase.values()){
            if(draw.getProductId().equals(productId)){
                draws.add(draw);
            }
        }

        return draws;
    }

    @Override
    public void updateIsWinnerById(String drawId) {

        for(Draw draw : drawsDatabase.values()){
            if(draw.getId().equals(drawId)){
                Draw selectDraw = drawsDatabase.get(drawId);
                selectDraw.setWinner(true);
            }
        }
    }
}
