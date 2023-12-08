package goorm.attendancemanagement.service;

import goorm.attendancemanagement.domain.dao.Course;
import goorm.attendancemanagement.domain.dao.Player;
import goorm.attendancemanagement.domain.dto.GetPlayersByCourseDto;
import goorm.attendancemanagement.domain.dto.CreatePlayerDto;
import goorm.attendancemanagement.repository.CourseRepository;
import goorm.attendancemanagement.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final CourseRepository courseRepository;
    private final PlayerRepository playerRepository;
    public List<GetPlayersByCourseDto> getPlayersByCourse(Integer courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        return course.getPlayers().stream()
                .map(player -> new GetPlayersByCourseDto(
                        course.getCourseName(),
                        player.getPlayerName(),
                        player.getPlayerEmail()
                ))
                .collect(Collectors.toList());
    }

    public Player createPlayer(CreatePlayerDto player) {
        boolean exists = playerRepository.existsByPlayerEmail(player.getPlayerEmail());

        if (exists) {
            throw new IllegalArgumentException("플레이어의 Email '" + player.getPlayerEmail() + "'가 이미 존재합니다.");
        }

        Course findCourse = courseRepository.findById(player.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + player.getCourseId()));

        Player newPlayer = new Player(findCourse, player.getPlayerName(), player.getPlayerEmail(), player.getPlayerPassword());
        return playerRepository.save(newPlayer);
    }
}
