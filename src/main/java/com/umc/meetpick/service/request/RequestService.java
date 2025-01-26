package com.umc.meetpick.service.request;

import com.umc.meetpick.dto.RequestDTO;
import com.umc.meetpick.entity.Request;

public interface RequestService {
    RequestDTO.NewRequestDTO createNewRequest(RequestDTO.NewRequestDTO request);
    RequestDTO.JoinRequestDTO createJoinRequest(RequestDTO.JoinRequestDTO request);
    void deleteRequest(Long requestId, Long userId);
    RequestDTO.LikeRequestDTO likeRequest(Long requestId, Long userId);
    RequestDTO.isAcceptedDTO acceptRequest(Long requestId, Long userId, Boolean isAccepted);
}
