package goorm.attendancebook.service;

import goorm.attendancebook.domain.dao.Player;
import goorm.attendancebook.domain.dto.PlayerSessionDto;
import goorm.attendancebook.domain.dto.ResponseDto;
import goorm.attendancebook.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public ResponseDto<List<String>> getAllCourse() {
        List<String> playerCourseList = new ArrayList<String>();

        try {
            playerCourseList = playerRepository.findDistinctPlayerCourses();
        } catch (Exception exception) {
            return ResponseDto.setFailed("Database Error");
        }

        return ResponseDto.setSuccess("Success", playerCourseList);
    }

    public ResponseDto<List<PlayerSessionDto>> getAllPlayerSessionsByCourseAndDate(String playerCourse, LocalDate date) {
        List<PlayerSessionDto> playerSessionList = new ArrayList<>();

        try {
            List<Object[]> sessions = playerRepository.findSessionsByCourseAndDate(playerCourse, date);
            for (Object[] record : sessions) {
                int playerId = (Integer) record[1]; // playerId 추정 위치
                List<Integer> attendance = Arrays.asList(
                        (Integer) record[2], // sessionOne
                        (Integer) record[3], // sessionTwo
                        (Integer) record[4], // sessionThree
                        (Integer) record[5], // sessionFour
                        (Integer) record[6], // sessionFive
                        (Integer) record[7], // sessionSix
                        (Integer) record[8], // sessionSeven
                        (Integer) record[9]  // sessionEight
                );
                playerSessionList.add(new PlayerSessionDto((String) record[0], playerId, attendance));
            }
        } catch (Exception exception) {
            return ResponseDto.setFailed("Database Error");
        }

        return ResponseDto.setSuccess("Success", playerSessionList);
    }


    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }
}