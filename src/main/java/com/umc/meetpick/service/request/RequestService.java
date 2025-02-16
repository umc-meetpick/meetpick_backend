package com.umc.meetpick.service.request;

import com.umc.meetpick.dto.MatchResponseDto;
import com.umc.meetpick.dto.RequestDTO;
import com.umc.meetpick.enums.MateType;

import java.util.List;

public interface RequestService {
    RequestDTO.NewRequestDTO createNewRequest(Long memberId ,RequestDTO.NewRequestDTO request);
    RequestDTO.JoinRequestDTO createJoinRequest(Long memberId,RequestDTO.JoinRequestDTO request);
    void deleteRequest(Long requestId, Long userId);
    RequestDTO.LikeRequestDTO likeRequest(Long memberId ,Long requestId);
    void deleteLikeRequest(Long memberId,Long requestId);
    RequestDTO.isAcceptedDTO acceptRequest(Long memberId, Long requestId, Boolean isAccepted);
    List<MatchResponseDto> getLikes(Long memberId, MateType mateType);

}
