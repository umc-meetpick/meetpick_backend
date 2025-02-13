package com.umc.meetpick.service.request;

import com.umc.meetpick.dto.MatchResponseDto;
import com.umc.meetpick.dto.RequestDTO;
import com.umc.meetpick.enums.MateType;

import java.util.List;

public interface RequestService {
    RequestDTO.NewRequestDTO createNewRequest(Long memberId ,RequestDTO.NewRequestDTO request);
    RequestDTO.JoinRequestDTO createJoinRequest(RequestDTO.JoinRequestDTO request);
    void deleteRequest(Long requestId, Long userId);
    RequestDTO.LikeRequestDTO likeRequest(Long requestId, Long userId);
    void deleteLikeRequest(Long requestId, Long userId);
    RequestDTO.isAcceptedDTO acceptRequest(Long requestId, Long userId, Boolean isAccepted);
    List<MatchResponseDto> getLikes(Long memberId, MateType mateType);

}
