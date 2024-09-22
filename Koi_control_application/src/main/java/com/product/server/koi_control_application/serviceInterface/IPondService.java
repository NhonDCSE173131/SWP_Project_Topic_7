package com.product.server.koi_control_application.serviceInterface;

import com.product.server.koi_control_application.dto.PondCreationRequest;
import com.product.server.koi_control_application.dto.PondUpdateRequest;
import com.product.server.koi_control_application.model.Pond;
import org.springframework.data.domain.Page;

public interface IPondService {
    Pond addPond(Pond pond);

    Pond getPond(int id);
    Page<Pond> getPonds(int page, int size);

    Page<Pond> getAllPondByUserId(int userId, int page, int size);
    void deletePond(int id);

    Pond updatePond(int id, PondUpdateRequest request);


}