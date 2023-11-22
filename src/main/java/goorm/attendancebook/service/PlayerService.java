package goorm.attendancebook.service;

import goorm.attendancebook.domain.dao.Attendance;
import goorm.attendancebook.domain.dao.Player;
import goorm.attendancebook.domain.dto.SearchAttendanceDetail;
import goorm.attendancebook.domain.dto.SearchAttendanceRequestDto;
import goorm.attendancebook.domain.dto.SearchAttendanceResponseDto;
import goorm.attendancebook.repository.AttendanceRepository;
import goorm.attendancebook.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final AttendanceRepository attendanceRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Player savePlayer(Player player) {
        String hashedPassword = passwordEncoder.encode(player.getPlayerPw());
        player.setPlayerPw(hashedPassword);
        return playerRepository.save(player);
    }

    public SearchAttendanceResponseDto searchPlayer(SearchAttendanceRequestDto request){
        Player player = playerRepository.findByPlayerNameAndPlayerCourseAndPlayerPw(request.getName(), request.getCourse(), request.getPw());

        LocalDate startDate = LocalDate.parse(request.getStartDate());
        LocalDate endDate = LocalDate.parse(request.getEndDate());
        List<Attendance> dateList =
                attendanceRepository.findByPlayerIdAndAttendanceDateBetween(player.getPlayerId(), startDate, endDate);

        // 출석: 1, 지각: 2, 조퇴:3, 외출:4, 결석:5, 공결: 6
        int state1 = 0;
        int state2 = 0;
        int state3 = 0;
        int state4 = 0;
        int state5 = 0;
        int state6 = 0;

        for(Attendance date : dateList) {
            List<Integer> state = Arrays.asList(
                    date.getSessionOne(), date.getSessionTwo(), date.getSessionThree(), date.getSessionFour(),
                    date.getSessionFive(), date.getSessionSix(), date.getSessionSeven(), date.getSessionEight());
            Map<Integer, Integer> map = new HashMap<>() {{
                put(1, 0);
                put(2, 0);
                put(3, 0);
                put(4, 0);
                put(5, 0);
                put(6, 0);
            }};
            for(int i=0;i<8;i++){
                int key = state.get(i);
                map.replace(key, map.get(key)+1);
            }
            if(map.get(1)==8) state1++;
            else if(map.get(2)>0) state2++;
            else if(map.get(3)>0) state3++;
            else if(map.get(4)>0) state4++;
            else if(map.get(5)>0) state5++;
            else if(map.get(6)>0) state6++;
        }

        int[] stateArr = new int[dateList.size()];
        for(int i=0;i<stateArr.length;i++){
            stateArr[i] = dateList.get(i).getSessionState();
        }

        return SearchAttendanceResponseDto.builder()
                .name(player.getPlayerName())
                .playerId(player.getPlayerId())
                .course(player.getPlayerCourse())
                .total(dateList.size())
                .state1(state1)
                .state2(state2)
                .state3(state3)
                .state4(state4)
                .state5(state5)
                .state6(state6)
                .stateArr(stateArr)
                .build();
    }

    public SearchAttendanceDetail searchAttendanceDetail(int playerId, String date){
        LocalDate localDate = LocalDate.parse(date);
        Attendance attendance = attendanceRepository.findByPlayerIdAndAttendanceDate(playerId, localDate);

        return SearchAttendanceDetail.builder()
                .attendanceId(attendance.getAttendanceId())
                .sessionOne(attendance.getSessionOne())
                .sessionTwo(attendance.getSessionTwo())
                .sessionThree(attendance.getSessionThree())
                .sessionFour(attendance.getSessionFour())
                .sessionFive(attendance.getSessionFive())
                .sessionSix(attendance.getSessionSix())
                .sessionSeven(attendance.getSessionSeven())
                .sessionEight(attendance.getSessionEight())
                .build();
    }

}