package com.umc.meetpick.service.request;

import com.umc.meetpick.dto.MatchResponseDto;
import com.umc.meetpick.dto.RequestDTO;
import com.umc.meetpick.entity.*;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileLikes;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.entity.mapping.MemberSecondProfileSubMajor;
import com.umc.meetpick.entity.mapping.MemberSecondProfileTimes;

//import com.umc.meetpick.enums.PersonalityEnum;

import com.umc.meetpick.enums.*;

import com.umc.meetpick.repository.*;
import com.umc.meetpick.repository.member.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.umc.meetpick.enums.StudentNumber.*;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final NewRequestRepository newRequestRepository;
    private final MemberRepository memberRepository;
    private final SubMajorRepository subMajorRepository;
    private final MemberMappingRepository memberMappingRepository;
    private final MemberLikesRepository memberLikesRepository;
    private final MemberSecondProfileRepository memberSecondProfileRepository;
    private final MemberSecondProfileTimesRepository memberSecondProfileTimesRepository;
    //private final PersonalityRepository personalityRepository;
    private final MemberSecondProfileSubMajorRepository memberSecondProfileSubMajorRepository;

    @Override
    public RequestDTO.NewRequestDTO createNewRequest(RequestDTO.NewRequestDTO newRequest) {
        // 작성자가 실제 존재하는지 검증
        Member writer = memberRepository.findById(newRequest.getWriterId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다." + newRequest.getWriterId()));
        // 프론트로부터 받은 전공이 전공 테이블에 존재하는지 검증
//        Major major = majorRepository.findByName(newRequest.getMajorName())
//                .orElseThrow(()-> new EntityNotFoundException("등록된 전공이 아닙니다." + newRequest.getMajorName()));

        // 나이 범위 검증
        if (newRequest.getMinAge() >= newRequest.getMaxAge()) {
            throw new IllegalArgumentException("나이 범위 에러");
        }

        // 동일 작성자 동일 타입 중복 요청 체크
        if (memberSecondProfileRepository.existsByMemberIdAndMateType(writer.getId(), newRequest.getType())) {
            throw new IllegalArgumentException("이미 요청이 존재");
        }

        // 동일 작성자인 경우
        if (memberSecondProfileRepository.existsByMemberId(writer.getId())){
            throw new IllegalArgumentException("하나의 요청만 가능합니다.");
        }

        // Personality 변환 및 저장
//        if (newRequest.getPersonality().size() != 4) {
//            throw new IllegalArgumentException("personality는 4개가 입력되어야 함");
//        }
//
//        Personality personality = new Personality(
//                PersonalityEnum.valueOf(newRequest.getPersonality().get(0)), // groupA
//                PersonalityEnum.valueOf(newRequest.getPersonality().get(1)), // groupB
//                PersonalityEnum.valueOf(newRequest.getPersonality().get(2)), // groupC
//                PersonalityEnum.valueOf(newRequest.getPersonality().get(3))  // groupD
//        );
//
//        Personality savedPersonality = personalityRepository.save(personality);


        // studentNumber 변환 -> 프론트에서 받은 string을 enum으로
        StudentNumber studentNumberEnum = StudentNumber.fromString(newRequest.getStudentNumber());

        // 운동 타입 변환
        Set<ExerciseType> exerciseTypes = Arrays.stream(newRequest.getExerciseTypes().split(","))
                .map(String::trim)  // 앞뒤 공백 제거
                .map(ExerciseType::fromString) // ExerciseType으로 변환
                .collect(Collectors.toSet());

        // 음식 타입 변환
        Set<FoodType> foodTypes = Arrays.stream(newRequest.getFood().split(","))
                .map(String::trim)
                .map(FoodType::fromString)
                .collect(Collectors.toSet());

        // 새로운 MemberSecondProfile 생성
        MemberSecondProfile newMemberSecondProfile = MemberSecondProfile.builder()
                .member(writer)
                .gender(newRequest.getGender())
                .studentNumber(studentNumberEnum)
                .mbti(newRequest.getMbti())
                .minAge(newRequest.getMinAge())
                .maxAge(newRequest.getMaxAge())
                .maxPeople(newRequest.getMaxPeople())
                .currentPeople(0)
                //.personality(savedPersonality)
                .isHobbySame(newRequest.getIsHobbySame())
                .comment(newRequest.getComment())
                .mateType(newRequest.getType())
                .foodTypes(foodTypes)
                .exerciseTypes(exerciseTypes)
                .isSchool(newRequest.getIsSchool())
                .build();

        MemberSecondProfile savedProfile = memberSecondProfileRepository.save(newMemberSecondProfile);

        // membersecondprofiletimesd dto를 entity로 변환
        List<MemberSecondProfileTimes> timesList = newRequest.getMemberSecondProfileTimes().stream()
                .map(dto -> {
                    Week weekEnum = Week.fromString(dto.getWeek()); // 각 DTO의 week 변환
                    return MemberSecondProfileTimes.builder()
                            .week(weekEnum) // 변환된 Week enum 저장
                            .times(dto.getTimes()) // times 그대로 매핑
                            .memberSecondProfile(savedProfile) // 프로필 연결
                            .build();
                })
                .toList();

        memberSecondProfileTimesRepository.saveAll(timesList);

        // List<String>으로 받은 세부전공을 엔티티도 변환
        List<MemberSecondProfileSubMajor> subMajorList = newRequest.getSubMajorName().stream()
                .map(name -> {
                    // 프론트에서 받은 subMajorName으로 SubMajor entity 찾기
                    SubMajor subMajor = subMajorRepository.findByName(name)
                            .orElseThrow(()-> new EntityNotFoundException("등록 전공 아님"));

                    // MemberSecondProfileSubMajor 생성
                    return MemberSecondProfileSubMajor.builder()
                            .memberSecondProfile(savedProfile)
                            .subMajor(subMajor)
                            .build();
                })
                        .toList();

        memberSecondProfileSubMajorRepository.saveAll(subMajorList);

        return RequestDTO.NewRequestDTO.builder()
                .writerId(savedProfile.getMember().getId())
                .studentNumber(savedProfile.getStudentNumber().name())
                .mbti(savedProfile.getMbti())
                .minAge(savedProfile.getMinAge())
                .maxAge(savedProfile.getMaxAge())
                .maxPeople(savedProfile.getMaxPeople())
                .type(savedProfile.getMateType())
                .build();

    }

    // 매칭에 참가하기 api
    @Override
    public RequestDTO.JoinRequestDTO createJoinRequest(RequestDTO.JoinRequestDTO newJoinRequest) {
        // requestId가 존재하는지 판단
        MemberSecondProfile request = memberSecondProfileRepository.findById(newJoinRequest.getRequestId())
                .orElseThrow(()-> new EntityNotFoundException("잘못된 매칭에 대한 요청" + newJoinRequest.getRequestId()));

        request.addPerson();

        // 유저 join으로 찾기 - 매칭 신청한 유저
        Member joinMember = memberRepository.findById(newJoinRequest.getPostUserId())
                .orElseThrow(() -> new EntityNotFoundException("매칭 신청 유저 오류" + newJoinRequest.getPostUserId()));

        MemberProfile joinMemberProfile = joinMember.getMemberProfile();

        // 매칭 등록 유저 찾아오기
        Member requestOwnerMember = memberRepository.findById(request.getMember().getId())
                .orElseThrow(()-> new EntityNotFoundException("매칭 등록 유저 오류"));

        MemberProfile requestOwnerMemberProfile = requestOwnerMember.getMemberProfile();

        // 조건에 맞는지 판단 - 성별
        if(!(request.getGender() == null) && joinMember.getGender() != request.getGender()){
            throw new IllegalArgumentException("성별 조건 안 맞음");
        }

        // 조건에 맞는지 판단 - 나이 범위
        if(!(request.getMinAge() == null)){
            if (request.getMinAge() > joinMember.getAge() || request.getMaxAge() < joinMember.getAge()) {
                throw new IllegalArgumentException("나이 조건 안 맞음");
            }
        }

        // 조건에 맞는지 판단 - 학번
        if(request.getStudentNumber() == PEER){
            if (!(joinMemberProfile.getStudentNumber() == requestOwnerMemberProfile.getStudentNumber())){
                throw new IllegalArgumentException("학번 조건 안 맞음");
            }
        } else if(request.getStudentNumber() == SENIOR ){
            if (!(joinMemberProfile.getStudentNumber() < requestOwnerMemberProfile.getStudentNumber())){
                throw new IllegalArgumentException("학번 조건 안 맞음");
            }
        } else if(request.getStudentNumber() == JUNIOR){
            if (!(joinMemberProfile.getStudentNumber() > requestOwnerMemberProfile.getStudentNumber())){
                throw new IllegalArgumentException("학번 조건 안 맞음");
            }
        }

        // 조건에 맞는지 판단 - submajor - 나중에

        // 취미 동일 여부 - 하나라도 같으면 통과
        if(request.getIsHobbySame()){
            if(!(Collections.disjoint(joinMemberProfile.getHobbies(),requestOwnerMemberProfile.getHobbies()))){
                throw new IllegalArgumentException("취미 조건 안 맞음");
            }
        }

        // 성격 판단 - 입력받은 mbti 가져와서 판단
        String joinMemberMBTI = joinMemberProfile.getMBTI().name();
        String requestMBTI = request.getMbti().stream().map(Enum::name).collect(Collectors.joining());
        for (int i = 0; i < 4; i++){
            if(!(requestMBTI.charAt(i) == joinMemberMBTI.charAt(i)) && !(requestMBTI.charAt(i) == 'X')){
                throw new IllegalArgumentException("성격 조건 안 맞음");
            }
        }

//        if (!((request.getPersonality().getGroupA() == PersonalityEnum.CHEERFUL && joinMemberMBTI.charAt(0) == 'E') ||
//                (request.getPersonality().getGroupA() == PersonalityEnum.QUIET && joinMemberMBTI.charAt(0) == 'I'))) {
//            throw new IllegalArgumentException("성격 조건 안 맞음");
//        }else if (!((request.getPersonality().getGroupB() == PersonalityEnum.REALISTIC && joinMemberMBTI.charAt(1) == 'S') ||
//                (request.getPersonality().getGroupB() == PersonalityEnum.CREATIVE && joinMemberMBTI.charAt(1) == 'N'))) {
//            throw new IllegalArgumentException("성격 조건 안 맞음");
//        }else if (!((request.getPersonality().getGroupC() == PersonalityEnum.OBJECTIVE && joinMemberMBTI.charAt(2) == 'T') ||
//                (request.getPersonality().getGroupC() == PersonalityEnum.SUBJECTIVE && joinMemberMBTI.charAt(2) == 'F'))) {
//            throw new IllegalArgumentException("성격 조건 안 맞음");
//        }else if (!((request.getPersonality().getGroupD() == PersonalityEnum.SYSTEMATIC && joinMemberMBTI.charAt(3) == 'J') ||
//                (request.getPersonality().getGroupD() == PersonalityEnum.FLEXIBLE && joinMemberMBTI.charAt(3) == 'P'))) {
//            throw new IllegalArgumentException("성격 조건 안 맞음");
//        }

        // 중복 신청 방지
        if(memberMappingRepository.existsByMemberSecondProfileAndMember(request, joinMember)){
            throw new IllegalArgumentException("중복신청 불가");
        }

        // MemberMapping 생성
        MemberSecondProfileMapping memberSecondProfileMapping = MemberSecondProfileMapping.builder()
                .member(joinMember)
                .memberSecondProfile(request)
                .status(false)
                .build();
        MemberSecondProfileMapping savedMapping = memberMappingRepository.save(memberSecondProfileMapping);

        return RequestDTO.JoinRequestDTO.builder()
                .requestId(savedMapping.getMemberSecondProfile().getId())
                .postUserId(savedMapping.getMember().getId())
                //.status(savedMapping.getStatus())
                .build();

    }

    @Override
    public void deleteRequest(Long requestId, Long userId){
        MemberSecondProfile request = memberSecondProfileRepository.findById(requestId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 매칭에 대한 요청"));

        if (!request.getMember().getId().equals(userId)) {
            throw new IllegalArgumentException("삭제 권한 없음");
        }
        memberSecondProfileRepository.delete(request);
    }

    // 매칭에 좋아요 등록
    @Override
    public RequestDTO.LikeRequestDTO likeRequest(Long requestId, Long userId) {
        MemberSecondProfile request = memberSecondProfileRepository.findById(requestId)
                .orElseThrow(()->new EntityNotFoundException("존재하지 않는 매칭에 대한 요청"));

        Member member = memberRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("존재하지 않는 유저의 신청"));

        MemberSecondProfileLikes memberLikes = MemberSecondProfileLikes.builder()
                .member(member)
                .memberSecondProfile(request)
                .build();

        MemberSecondProfileLikes savedMemberLikes = memberLikesRepository.save(memberLikes);

        return RequestDTO.LikeRequestDTO.builder()
                .requestId(savedMemberLikes.getMemberSecondProfile().getId())
                .postUserId(savedMemberLikes.getMember().getId())
                .build();
    }

    @Override
    public void deleteLikeRequest(Long requestId, Long userId){

        MemberSecondProfileLikes memberLikes = memberLikesRepository.findByMemberSecondProfileId(requestId)
                .orElseThrow(()->new IllegalArgumentException("찜하기 찾을 수 없음"));

        if (!memberLikes.getMember().getId().equals(userId)) {
            throw new IllegalArgumentException("취소 권한 없음");
        }
        memberLikesRepository.delete(memberLikes);
    }

    // 매칭 요청에 대한 수락 or 거절
    @Override
    public RequestDTO.isAcceptedDTO acceptRequest(Long matchingRequestId, Long userId, Boolean isAccepted) {

//        MemberSecondProfile request = memberSecondProfileRepository.findById(requestId)
//                .orElseThrow(()->new EntityNotFoundException("존재하지 않는 매칭"));

        MemberSecondProfileMapping memberSecondProfileMapping = memberMappingRepository.findById(matchingRequestId)
                .orElseThrow(()-> new EntityNotFoundException("신청을 찾을 수 없음"));

        if(!memberSecondProfileMapping.getMemberSecondProfile().getMember().getId().equals(userId)) {
            throw new IllegalArgumentException("권한 없음");
        }

        if(memberSecondProfileMapping.getStatus()){
            throw new IllegalArgumentException("이미 수락 or 거절됨");
        }

        memberSecondProfileMapping.setStatus(true);
        memberSecondProfileMapping.setIsAccepted(isAccepted);

        MemberSecondProfileMapping updatedMapping = memberMappingRepository.save(memberSecondProfileMapping);

        return RequestDTO.isAcceptedDTO.builder()
                .matchingRequestId(updatedMapping.getId())
                .isAccepted(updatedMapping.getIsAccepted())
                .status(updatedMapping.getStatus())
                .build();
    }

    //TODO 다시 코딩
    @Override
    public List<MatchResponseDto> getLikes(Long memberId, MateType mateType) {
        List<MemberSecondProfileLikes> memberSecondProfileLikes =
                memberLikesRepository.findAllByMember(memberRepository.findMemberById(memberId));

        return memberSecondProfileLikes.stream()
                .map(like -> {
                    MemberSecondProfile memberSecondProfile = like.getMemberSecondProfile();
                    Member member = memberSecondProfile.getMember();
                    MemberProfile memberProfile = member.getMemberProfile();

                    return MatchResponseDto.builder()
                            .memberId(member.getId())
                            .requestId(memberSecondProfile.getId())
                            .memberNumber(memberProfile.getStudentNumber())
                            .gender(member.getGender().getKoreanName())
                            .foodType(memberSecondProfile.getFoodTypes().stream()
                                    .map(FoodType::getKoreanName)
                                    .collect(Collectors.toSet()))
                            .hobby(memberProfile.getHobbies().stream()
                                    .map(Hobby::getKoreanName)
                                    .collect(Collectors.toSet()))
                            .mateType(memberSecondProfile.getMateType())
                            .build();
                })
                .collect(Collectors.toList());
    }


}
