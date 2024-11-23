package ru.solonchev.backend.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solonchev.backend.persistence.entity.TitleTypeEntity;


@Repository
public interface TypeTitleRepository extends JpaRepository<TitleTypeEntity, Integer> {
}
