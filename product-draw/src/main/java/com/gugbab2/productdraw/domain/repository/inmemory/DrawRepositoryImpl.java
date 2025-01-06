package com.gugbab2.productdraw.domain.repository.inmemory;

import com.gugbab2.productdraw.domain.entity.Draw;
import com.gugbab2.productdraw.domain.entity.Entrant;
import com.gugbab2.productdraw.domain.repository.DrawRepository;
import com.gugbab2.productdraw.domain.vo.WinnerStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DrawRepositoryImpl implements DrawRepository {
    private final Map<String, Draw> entryDatabase = new HashMap<>();

    @Override
    public Draw save(Draw draw) {
        entryDatabase.put(draw.getId(), draw);
        return draw;
    }

    @Override
    public Draw findById(String drawId) {
        return entryDatabase.get(drawId);
    }

    @Override
    public List<Draw> findByProductId(String productId) {
        List<Draw> result = new ArrayList<>();
        for (Draw draw : entryDatabase.values()) {
            if (productId.equals(draw.getProductId())) {
                result.add(draw);
            }
        }
        return result;
    }

    @Override
    public List<Draw> findByEntrantId(String entrantId) {
        List<Draw> result = new ArrayList<>();
        for (Draw draw : entryDatabase.values()) {
            if (entrantId.equals(draw.getEntrantId())) {
                result.add(draw);
            }
        }
        return result;
    }

    @Override
    public void updateById(Draw draw) {
        Draw existingDraw = entryDatabase.get(draw.getId());

        if (existingDraw != null) {
            existingDraw.setWinnerStatus(WinnerStatus.WINNER);

            entryDatabase.put(draw.getId(), existingDraw);
        } else {
            throw new IllegalArgumentException("Draw not found with id: " + draw.getId());
        }
    }
}
