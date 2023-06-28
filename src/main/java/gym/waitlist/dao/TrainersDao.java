package gym.waitlist.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gym.waitlist.entity.Trainers;

public interface TrainersDao extends JpaRepository<Trainers, Long> {

	Optional<Trainers> findByTrainerId(Long trainerId);
}
