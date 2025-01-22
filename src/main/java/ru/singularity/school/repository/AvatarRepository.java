package ru.singularity.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.singularity.school.model.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

}
