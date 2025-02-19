package com.umc.meetpick.service.matching.factory;

import com.umc.meetpick.common.exception.handler.GeneralHandler;
import com.umc.meetpick.common.response.status.ErrorCode;
import com.umc.meetpick.dto.AlarmDto;
import com.umc.meetpick.dto.MatchPageDto;
import com.umc.meetpick.dto.MatchRequestDto;
import com.umc.meetpick.dto.RecommendDto;
import com.umc.meetpick.entity.Member;
import com.umc.meetpick.entity.MemberProfiles.MemberProfile;
import com.umc.meetpick.entity.MemberProfiles.MemberSecondProfile;
import com.umc.meetpick.entity.mapping.MemberSecondProfileMapping;
import com.umc.meetpick.enums.MateType;
import com.umc.meetpick.entity.matchingdata.exercise.MemberDataExercise;
import com.umc.meetpick.entity.matchingdata.food.MemberDataFood;
import com.umc.meetpick.entity.matchingdata.study.MemberDataStudy;
import com.umc.meetpick.enums.FoodType;
import com.umc.meetpick.enums.MateType;
import org.springframework.data.domain.Page;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.umc.meetpick.common.util.DateTimeUtil.getTime;
import static com.umc.meetpick.common.util.MemberSecondProfileUtil.findByMateType;

public class MatchingDtoFactory {

    // MemberSecondProfile 객체를 AlarmResponseDto로 변환하는 메서드
    public static AlarmDto.AlarmResponseDto memberSecondProfileToAlarmDto(MemberSecondProfileMapping mapping) {

        MemberSecondProfile memberSecondProfile = findByMateType(mapping.getMember().getMemberSecondProfiles(), mapping.getMemberSecondProfile().getMateType()).orElseThrow(()-> new GeneralHandler(ErrorCode.PROFILE2_NOT_FOUND));

        return AlarmDto.AlarmResponseDto.builder()
                .mateType(memberSecondProfile.getMateType().getKoreanName())
                .content("새로운 알림을 확인해보세요!")
                .createdAt(getTime(mapping.getCreatedAt()))
                .memberSecondProfileId(memberSecondProfile.getId())
                .mappingId(mapping.getId())
                .build();
    }

    // MemberSecondProfileMapping 리스트를 AlarmResponseDto 리스트로 변환하는 메서드
    public static AlarmDto.AlarmPageResponseDto memberSecondProfileToAlarmDtoList(Page<MemberSecondProfileMapping> mappingList) {

        List<AlarmDto.AlarmResponseDto> alarmResponseDtoList = mappingList.stream()
                .map(MatchingDtoFactory::memberSecondProfileToAlarmDto)
                .toList();

        return   AlarmDto.AlarmPageResponseDto.builder()
                .alarms(alarmResponseDtoList)
                .currentPage(mappingList.getNumber())
                .hasNextPage(mappingList.hasNext())
                .build();
    }


    // TODo 이름 수정하기
    public static MatchRequestDto memberSecondProfileToMatchRequestDto(MemberSecondProfileMapping mapping) {

        //TODO 고치기
        Member member = mapping.getMember();
        MemberProfile memberProfile = member.getMemberProfile();
        MemberSecondProfile memberSecondProfile = findByMateType(member.getMemberSecondProfiles(), mapping.getMemberSecondProfile().getMateType()).orElseThrow(()-> new GeneralHandler(ErrorCode.PROFILE2_NOT_FOUND));

        // 날짜 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String formattedDate = memberSecondProfile.getModifiedAt().format(formatter);

        return MatchRequestDto.builder()
                .mappingId(mapping.getId())
                .memberSecondProfileId(memberSecondProfile.getId())
                .studentNumber(memberProfile.getStudentNumber() + "학번")
                .major(memberProfile.getSubMajor().getMajor().getName())
                .age(member.getAge())
                .mateType(memberSecondProfile.getMateType().getKoreanName())
                .createdAt(formattedDate)
                .build();
    }


    public static MatchPageDto memberSecondProfileToMatchPageDto(Page<MemberSecondProfileMapping> mappingList) {

        List<MatchRequestDto> matchRequestDtoList = mappingList.stream()
                .map(MatchingDtoFactory::memberSecondProfileToMatchRequestDto)
                .toList();

        return   MatchPageDto.builder()
                .matchRequestDtoList(matchRequestDtoList)
                .currentPage(mappingList.getNumber())
                .hasNextPage(mappingList.hasNext())
                .build();
    }

    public static RecommendDto.FoodRecommendPageDto memberSecondProfileToFoodRecommendtDto(List<MemberDataFood> memberList) {

        List<MemberSecondProfile> memberSecondProfiles = memberList.stream()
                .map(memberData -> findByMateType(memberData.getMember().getMemberSecondProfiles(), MateType.MEAL).orElseThrow(()-> new GeneralHandler(ErrorCode.PROFILE2_NOT_FOUND)))
                .toList();


        List<RecommendDto.FoodRecommendDto> foodRecommendDtos = memberSecondProfiles.stream().map(
                memberSecondProfile -> {

                    Member member = memberSecondProfile.getMember();
                    MemberProfile memberProfile = member.getMemberProfile();

                    return RecommendDto.FoodRecommendDto.builder()
                            .requestId(memberSecondProfile.getId())
                            .studentNumber(memberProfile.getStudentNumber() + "학번")
                            .foodTypes(memberSecondProfile.getFoodTypes().stream().map(FoodType::getKoreanName).collect(Collectors.toSet()))
                            .gender(member.getGender().getKoreanName())
                            .mbti(memberProfile.getMBTI())
                            .nickName(memberProfile.getNickname())
                            .build();
                }
        ).toList();

        return RecommendDto.FoodRecommendPageDto.builder()
                .foodRecommendDtos(foodRecommendDtos)
                .hasNextPage(false)
                .currentPage(0)
                .build();
    }

        public static RecommendDto.ExerciseRecommendPageDto memberSecondProfileToExerciseRecommendDto(List<MemberDataExercise> memberList) {
            List<MemberSecondProfile> memberSecondProfiles = memberList.stream()
                    .map(memberData -> findByMateType(memberData.getMember().getMemberSecondProfiles(), MateType.EXERCISE)
                            .orElseThrow(() -> new GeneralHandler(ErrorCode.PROFILE2_NOT_FOUND)))
                    .toList();

            List<RecommendDto.ExerciseRecommendDto> exerciseRecommendDtos = memberSecondProfiles.stream().map(
                    memberSecondProfile -> {
                        Member member = memberSecondProfile.getMember();
                        MemberProfile memberProfile = member.getMemberProfile();

                        return RecommendDto.ExerciseRecommendDto.builder()
                                .requestId(memberSecondProfile.getId())
                                .studentNumber(memberProfile.getStudentNumber() + "학번")
                                .exerciseType(memberSecondProfile.getExerciseType().getDisplayName())
                                .gender(member.getGender().getKoreanName())
                                .mbti(memberProfile.getMBTI())
                                .nickName(memberProfile.getNickname())
                                .imageUrl(memberProfile.getProfileImage())
                                .exerciseType(memberSecondProfile.getExerciseType().getDisplayName())
                                .build();
                    }
            ).toList();

            return RecommendDto.ExerciseRecommendPageDto.builder()
                    .exerciseRecommendDtos(exerciseRecommendDtos)
                    .hasNextPage(false)
                    .currentPage(0)
                    .build();
        }

        public static RecommendDto.StudyRecommendPageDto memberSecondProfileToStudyRecommendDto(List<MemberDataStudy> memberList) {
            List<MemberSecondProfile> memberSecondProfiles = memberList.stream()
                    .map(memberData -> findByMateType(memberData.getMember().getMemberSecondProfiles(), MateType.STUDY)
                            .orElseThrow(() -> new GeneralHandler(ErrorCode.PROFILE2_NOT_FOUND)))
                    .toList();

            List<RecommendDto.StudyRecommendDto> studyRecommendDtos = memberSecondProfiles.stream().map(
                    memberSecondProfile -> {
                        Member member = memberSecondProfile.getMember();
                        MemberProfile memberProfile = member.getMemberProfile();

                        return RecommendDto.StudyRecommendDto.builder()
                                .requestId(memberSecondProfile.getId())
                                .studentNumber(memberProfile.getStudentNumber() + "학번")
                                .gender(member.getGender().getKoreanName())
                                .mbti(memberProfile.getMBTI())
                                .nickName(memberProfile.getNickname())
                                .studyType(memberSecondProfile.getStudyType().getKoreanName())
                                .imageUrl(memberProfile.getProfileImage())
                                .build();
                    }
            ).toList();

            return RecommendDto.StudyRecommendPageDto.builder()
                    .studyRecommendDtos(studyRecommendDtos)
                    .hasNextPage(false)
                    .currentPage(0)
                    .build();
        }
    }

