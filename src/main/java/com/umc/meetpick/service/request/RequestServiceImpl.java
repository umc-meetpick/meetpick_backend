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

import java.util.*;
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
    private final MemberSecondProfileSubMajorRepository memberSecondProfileSubMajorRepository;

    @Override
    public RequestDTO.NewRequestDTO createNewRequest(Long memberId, RequestDTO.NewRequestDTO newRequest) {
        // 작성자가 실제 존재하는지 검증
        Member writer = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다." + memberId));
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

         //동일 작성자인 경우
//        if (memberSecondProfileRepository.existsByMemberId(writer.getId())){
//            throw new IllegalArgumentException("하나의 요청만 가능합니다.");
//        }

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

        // 운동 타입 변환 (nullable 처리)
//        Set<ExerciseType> exerciseTypes = Optional.ofNullable(newRequest.getExerciseTypes())
//                .map(types -> Arrays.stream(types.split(","))
//                        .map(String::trim)
//                        .map(ExerciseType::fromString)
//                        .collect(Collectors.toSet()))
//                .orElse(Collections.emptySet());
        ExerciseType exerciseTypes = null;
        if (newRequest.getExerciseTypes() != null) {
            exerciseTypes = ExerciseType.fromString(newRequest.getExerciseTypes());
        }

        // 음식 타입 변환 (nullable 처리)
        Set<FoodType> foodTypes = Optional.ofNullable(newRequest.getFood())
                .orElse(Collections.emptyList())  // food가 null이면 빈 리스트 처리
                .stream()
                .map(FoodType::fromString)
                .collect(Collectors.toSet());

        // 스터디 타입 변환
        StudyType studyType = null;
        if (newRequest.getStudyType() != null) {
            studyType = StudyType.fromString(newRequest.getStudyType());
        }

        // 과목/교수 파싱 후 각각 넣기
        String majorName = null;
        String professorName = null;
        if(newRequest.getMajorNameAndProfessorName() != null) {
            String[] parts = newRequest.getMajorNameAndProfessorName().split("-");
            majorName = parts[0];
            professorName = parts[1];
        }

        // 공부 관련 온라인 여부
        Boolean isOnline = null;
        if(newRequest.getIsOnline() != null) {
            if(newRequest.getIsOnline() == "오프라인"){
                isOnline = false;
            } else {
                isOnline = true;
            }
        }


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
                .exerciseType(exerciseTypes)
                .isSchool(newRequest.getIsSchool())
                .studyType(studyType)
                .majorName(majorName)
                .professorName(professorName)
                .isOnline(isOnline)
                .studyTimes(newRequest.getStudyTimes())
                .place(newRequest.getPlace())
                .build();

        MemberSecondProfile savedProfile = memberSecondProfileRepository.save(newMemberSecondProfile);

        // memberSecondProfileTimes 변환 및 저장
        List<MemberSecondProfileTimes> timesList = newRequest.getMemberSecondProfileTimes().stream()
                .map(dto -> MemberSecondProfileTimes.builder()
                        .week(Week.fromString(dto.getWeek()))  // ✅ week 변환
                        .times(dto.getTimes())
                        .memberSecondProfile(savedProfile)
                        .build())
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
                //.writerId(memberId)
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
    public RequestDTO.JoinRequestDTO createJoinRequest(Long memberId,RequestDTO.JoinRequestDTO newJoinRequest) {
        // requestId가 존재하는지 판단
        MemberSecondProfile request = memberSecondProfileRepository.findById(newJoinRequest.getRequestId())
                .orElseThrow(()-> new EntityNotFoundException("잘못된 매칭에 대한 요청" + newJoinRequest.getRequestId()));

        request.addPerson();

        // 유저 join으로 찾기 - 매칭 신청한 유저
        Member joinMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("매칭 신청 유저 오류"));

        MemberProfile joinMemberProfile = joinMember.getMemberProfile();

        // 매칭 등록 유저 찾아오기
        Member requestOwnerMember = memberRepository.findById(request.getMember().getId())
                .orElseThrow(()-> new EntityNotFoundException("매칭 등록 유저 오류"));

        MemberProfile requestOwnerMemberProfile = requestOwnerMember.getMemberProfile();

//        // 조건에 맞는지 판단 - 성별
//        if(!(request.getGender() == null) && joinMember.getGender() != request.getGender()){
//            throw new IllegalArgumentException("성별 조건 안 맞음");
//        }
//
//        // 조건에 맞는지 판단 - 나이 범위
//        if(!(request.getMinAge() == null)){
//            if (request.getMinAge() > joinMember.getAge() || request.getMaxAge() < joinMember.getAge()) {
//                throw new IllegalArgumentException("나이 조건 안 맞음");
//            }
//        }

//        // 조건에 맞는지 판단 - 학번
//        if(request.getStudentNumber() == PEER){
//            if (!(joinMemberProfile.getStudentNumber() == requestOwnerMemberProfile.getStudentNumber())){
//                throw new IllegalArgumentException("학번 조건 안 맞음");
//            }
//        } else if(request.getStudentNumber() == SENIOR ){
//            if (!(joinMemberProfile.getStudentNumber() < requestOwnerMemberProfile.getStudentNumber())){
//                throw new IllegalArgumentException("학번 조건 안 맞음");
//            }
//        } else if(request.getStudentNumber() == JUNIOR){
//            if (!(joinMemberProfile.getStudentNumber() > requestOwnerMemberProfile.getStudentNumber())){
//                throw new IllegalArgumentException("학번 조건 안 맞음");
//            }
//        }

        // 조건에 맞는지 판단 - submajor - 나중에

//        // 취미 동일 여부 - 하나라도 같으면 통과
//        if(request.getIsHobbySame()){
//            if(!(Collections.disjoint(joinMemberProfile.getHobbies(),requestOwnerMemberProfile.getHobbies()))){
//                throw new IllegalArgumentException("취미 조건 안 맞음");
//            }
//        }
//
//        // 성격 판단 - 입력받은 mbti 가져와서 판단
//        String joinMemberMBTI = joinMemberProfile.getMBTI().name();
//        String requestMBTI = request.getMbti();
//        for (int i = 0; i < 4; i++){
//            if(!(requestMBTI.charAt(i) == joinMemberMBTI.charAt(i)) && !(requestMBTI.charAt(i) == 'x')){
//                throw new IllegalArgumentException("성격 조건 안 맞음");
//            }
//        }

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
                //.postUserId(savedMapping.getMember().getId())
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
    public RequestDTO.LikeRequestDTO likeRequest(Long memberId,Long requestId) {

        MemberSecondProfile request = memberSecondProfileRepository.findById(requestId)
                .orElseThrow(()->new EntityNotFoundException("존재하지 않는 매칭에 대한 요청"));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new EntityNotFoundException("존재하지 않는 유저의 신청"));

        if(memberLikesRepository.existsByMemberAndMemberSecondProfile(member, request)){
            throw new IllegalArgumentException("이미 좋아요 누르셨습니다.");
        }

        MemberSecondProfileLikes memberLikes = MemberSecondProfileLikes.builder()
                .member(member)
                .memberSecondProfile(request)
                .build();

        MemberSecondProfileLikes savedMemberLikes = memberLikesRepository.save(memberLikes);

        return RequestDTO.LikeRequestDTO.builder()
                .requestId(savedMemberLikes.getMemberSecondProfile().getId())
                //.postUserId(savedMemberLikes.getMember().getId())
                .build();
    }

    @Override
    public void deleteLikeRequest(Long memberId, Long requestId){

        MemberSecondProfileLikes memberLikes = memberLikesRepository.findByMemberSecondProfileId(requestId)
                .orElseThrow(()->new IllegalArgumentException("찜하기 찾을 수 없음"));

        if (!memberLikes.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("취소 권한 없음");
        }
        memberLikesRepository.delete(memberLikes);
    }

    // 매칭 요청에 대한 수락 or 거절
    @Override
    public RequestDTO.isAcceptedDTO acceptRequest(Long memberId, Long matchingRequestId, Boolean isAccepted) {

//        MemberSecondProfile request = memberSecondProfileRepository.findById(requestId)
//                .orElseThrow(()->new EntityNotFoundException("존재하지 않는 매칭"));

        MemberSecondProfileMapping memberSecondProfileMapping = memberMappingRepository.findById(matchingRequestId)
                .orElseThrow(()-> new EntityNotFoundException("신청을 찾을 수 없음"));

        if(!memberSecondProfileMapping.getMemberSecondProfile().getMember().getId().equals(memberId)) {
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
