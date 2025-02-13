package com.umc.meetpick.service.member.factory;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.dto.MemberDetailResponseDto;
import com.umc.meetpick.dto.MyProfileDto;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.WeekPair;
import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.Hobby;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.repository.member.MemberSecondProfileSubMajorRepository;
import com.umc.meetpick.repository.member.MemberSecondProfileTimesRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.umc.meetpick.common.util.MemberSecondProfileUtil.findByMateType;

//TODO 여기 부분 리팩토링!!!

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberDtoFactory {

    //TODO 개 마음에 안든다
    private static MemberSecondProfileSubMajorRepository staticmemberSecondProfileSubMajorRepository;
    private static MemberSecondProfileTimesRepository staticmemberSecondProfileTimesRepository;
    private final MemberSecondProfileSubMajorRepository memberSecondProfileSubMajorRepository;
    private final MemberSecondProfileTimesRepository memberSecondProfileTimesRepository;

    @PostConstruct
    private void init() {
        staticmemberSecondProfileSubMajorRepository = this.memberSecondProfileSubMajorRepository;
        staticmemberSecondProfileTimesRepository = this.memberSecondProfileTimesRepository;
    }

    public static MyProfileDto memberToProfileDto(Member member) {

        MemberProfile memberProfile = validateMemberProfile(member);

        return MyProfileDto.builder()
                .id(member.getId())
                .name(memberProfile.getNickname() + "(" + member.getName() + ")")
                .build();
    }

    public static MemberDetailResponseDto.MemberCommonDetailDto memberToCommonProfileDto(MemberSecondProfile memberSecondProfile) {

        Member member = memberSecondProfile.getMember();
        MemberProfile memberProfile = validateMemberProfile(member);

        return MemberDetailResponseDto.MemberCommonDetailDto.builder()
                .memberId(member.getId())
                .age(member.getAge() + "살")
                .studentNumber(memberProfile.getStudentNumber() + "학번")
                .gender(member.getGender().getKoreanName())
                .mbti(memberProfile.getMBTI())
                .major(memberProfile.getSubMajor().getMajor().getName())
                .subMajor(memberProfile.getSubMajor().getName())
                .hobbies(memberProfile.getHobbies().stream()
                        .map(Hobby::getKoreanName)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static MemberDetailResponseDto.MemberFoodDetailDto memberToFoodProfileDto(MemberSecondProfile memberSecondProfile) {

        // 2. 서브 전공(major) 이름 추출
        Set<String> subMajorNames = staticmemberSecondProfileSubMajorRepository
                .findAllByMemberSecondProfile(memberSecondProfile)
                .stream()
                .map(msps -> msps.getSubMajor().getName())
                .collect(Collectors.toSet());

        // 3. 요일과 시간 정보 매핑
        List<WeekPair<String, String>> weekAndTime = staticmemberSecondProfileTimesRepository
                .findByMemberSecondProfile(memberSecondProfile)
                .stream()
                .map(timeEntity -> {
                    String week = timeEntity.getWeek().toString();
                    String joinedTimes = timeEntity.getTimes()
                            .stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(", "));
                    return new WeekPair<>(week, joinedTimes);
                })
                .toList();

        // 4. 성별 정보 처리: null 이거나 한국어 이름이 없으면 "상관 없어" 처리
        String gender = "상관 없어";
        if (memberSecondProfile.getGender() != null && memberSecondProfile.getGender().getKoreanName() != null) {
            gender = memberSecondProfile.getGender().getKoreanName();
        }

        // 5. 연령 및 학번 정보 처리: 최소 연령이 null이면 "상관 없어"로 처리
        String ageAndPeer = "상관 없어";
        if (memberSecondProfile.getMinAge() != null && memberSecondProfile.getMaxAge() != null && memberSecondProfile.getStudentNumber() != null) {
            ageAndPeer = memberSecondProfile.getMinAge() + "~" + memberSecondProfile.getMaxAge() + "/" + memberSecondProfile.getStudentNumber().getKoreanName();
        }

        // 6. 음식 타입 한국어 이름 추출
        Set<String> foodTypeNames = memberSecondProfile.getFoodTypes()
                .stream()
                .map(FoodType::getKoreanName)
                .collect(Collectors.toSet());

        // 7. 현재 인원/최대 인원 문자열 생성
        String currentPeople = getPeople(memberSecondProfile);

        // 8. 취미 조건 처리 (true면 "같아야 돼!", 아니면 "상관없어")
        String hobby = Boolean.TRUE.equals(memberSecondProfile.getIsHobbySame()) ? "같아야 돼!" : "상관 없어";

        // 9. DTO 빌더에 데이터 세팅 후 반환
        return MemberDetailResponseDto.MemberFoodDetailDto.builder()
                .gender(gender)
                .ageAndPeer(ageAndPeer)
                .foodTypes(foodTypeNames)
                .currentPeople(currentPeople)
                .major(subMajorNames)
                .MBTI(memberSecondProfile.getMbti())
                .hobby(hobby)
                .weekAndTime(weekAndTime)
                .comment(memberSecondProfile.getComment())
                .build();
    }

    public static MemberDetailResponseDto.MemberExerciseDetailDto memberExerciseDetailDto(MemberSecondProfile memberSecondProfile) {

        String exercise = memberSecondProfile.getExerciseType().getDisplayName();

        String currentPeople = getPeople(memberSecondProfile);

        // TODO 중복되는거 함수 처리
        String gender = "상관 없어";
        if (memberSecondProfile.getGender() != null && memberSecondProfile.getGender().getKoreanName() != null) {
            gender = memberSecondProfile.getGender().getKoreanName();
        }

        // 5. 연령 및 학번 정보 처리: 최소 연령이 null이면 "상관 없어"로 처리
        String ageAndPeer = "상관 없어";
        if (memberSecondProfile.getMinAge() != null && memberSecondProfile.getMaxAge() != null && memberSecondProfile.getStudentNumber() != null) {
            ageAndPeer = memberSecondProfile.getMinAge() + "~" + memberSecondProfile.getMaxAge() + "/" + memberSecondProfile.getStudentNumber().getKoreanName();
        }

        Set<String> subMajorNames = staticmemberSecondProfileSubMajorRepository
                .findAllByMemberSecondProfile(memberSecondProfile)
                .stream()
                .map(msps -> msps.getSubMajor().getName())
                .collect(Collectors.toSet());

        String MBTI = memberSecondProfile.getMbti();

        String hobby = Boolean.TRUE.equals(memberSecondProfile.getIsHobbySame()) ? "같아야 돼!" : "상관 없어";

        List<WeekPair<String, String>> weekAndTime = staticmemberSecondProfileTimesRepository
                .findByMemberSecondProfile(memberSecondProfile)
                .stream()
                .map(timeEntity -> {
                    String week = timeEntity.getWeek().toString();
                    String joinedTimes = timeEntity.getTimes()
                            .stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(", "));
                    return new WeekPair<>(week, joinedTimes);
                })
                .toList();

        String place = "상관 없어";
        if(memberSecondProfile.getPlace() != null) {
            place = memberSecondProfile.getPlace();
        }

        String comment = memberSecondProfile.getComment();

        return MemberDetailResponseDto.MemberExerciseDetailDto
                .builder()
                .gender(gender)
                .currentPeople(currentPeople)
                .ageAndPeer(ageAndPeer)
                .exercise(exercise)
                .ageAndPeer(ageAndPeer)
                .weekAndTime(weekAndTime)
                .place(place)
                .comment(comment)
                .currentPeople(currentPeople)
                .hobby(hobby)
                .build();
    }

    public static MemberDetailResponseDto.MemberStudyDetailDto memberStudyDetailDto (MemberSecondProfile memberSecondProfile){

        String study = memberSecondProfile.getMateType().getKoreanName();

        Set<String> subMajorNames = staticmemberSecondProfileSubMajorRepository
                .findAllByMemberSecondProfile(memberSecondProfile)
                .stream()
                .map(msps -> msps.getSubMajor().getName())
                .collect(Collectors.toSet());

        String courseName = "상관 없어";
        if(memberSecondProfile.getMajorName() != null){
            courseName = memberSecondProfile.getMajorName();
        }

        String professorName = "상관 없어";
        if(memberSecondProfile.getProfessorName() != null){
            professorName = memberSecondProfile.getProfessorName();
        }

        String studyTimes = memberSecondProfile.getStudyTimes() + "-" + memberSecondProfile.getStudyTimes() + 1 + "회";

        String isOnline;
        if(memberSecondProfile.getIsOnline()){
            isOnline = "온라인";
        } else {
            isOnline = "오프라인";
        }

        String gender = "상관 없어";
        if (memberSecondProfile.getGender() != null && memberSecondProfile.getGender().getKoreanName() != null) {
            gender = memberSecondProfile.getGender().getKoreanName();
        }

        String ageAndPeer = "";
        if (memberSecondProfile.getMinAge() != null && memberSecondProfile.getMaxAge() != null && memberSecondProfile.getStudentNumber() != null) {
            ageAndPeer = memberSecondProfile.getMinAge() + "~" + memberSecondProfile.getMaxAge() + "/" + memberSecondProfile.getStudentNumber().getKoreanName();
        }

        String currentPeople = getPeople(memberSecondProfile);

        String MBTI = memberSecondProfile.getMbti();

        String place = "상관 없어";
        if(memberSecondProfile.getPlace() != null){
            place = memberSecondProfile.getPlace();
        }


        List<WeekPair<String, String>> weekAndTime = staticmemberSecondProfileTimesRepository
                .findByMemberSecondProfile(memberSecondProfile)
                .stream()
                .map(timeEntity -> {
                    String week = timeEntity.getWeek().toString();
                    String joinedTimes = timeEntity.getTimes()
                            .stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(", "));
                    return new WeekPair<>(week, joinedTimes);
                })
                .toList();

        String hobby = Boolean.TRUE.equals(memberSecondProfile.getIsHobbySame()) ? "같아야 돼!" : "상관 없어";

        String comment = memberSecondProfile.getComment();

        return MemberDetailResponseDto.MemberStudyDetailDto.builder()
                .study(study)
                .major(subMajorNames)
                .courseName(courseName)
                .professorName(professorName)
                .studyTimes(studyTimes)
                .isOnline(isOnline)
                .gender(gender)
                .ageAndPeer(ageAndPeer)
                .currentPeople(currentPeople)
                .MBTI(MBTI)
                .place(place)
                .weekAndTime(weekAndTime)
                .hobby(hobby)
                .comment(comment)
                .build();
    }



    private static String getPeople(MemberSecondProfile memberSecondProfile){
        return memberSecondProfile.getCurrentPeople() + "/" + memberSecondProfile.getMaxPeople();
    }


    public static MemberProfile validateMemberProfile(Member member){

        MemberProfile memberProfile = member.getMemberProfile();

        if (memberProfile == null) {
            log.info("멤버의 1차 프로필 정보 없음");
            throw new GeneralHandler(ErrorCode.PROFILE_NOT_FOUND);
        }
        return memberProfile;
    }
}
