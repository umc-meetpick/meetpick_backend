package com.umc.meetpick.service.request;

import com.umc.meetpick.dto.RequestDTO;
import com.umc.meetpick.entity.Request;

public interface RequestService {
    Request createNewRequest(RequestDTO.NewRequestDTO request);
}
