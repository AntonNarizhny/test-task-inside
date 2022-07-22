package ru.narizhny.test_task_inside.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.narizhny.test_task_inside.domain.entity.Client;
import ru.narizhny.test_task_inside.domain.entity.Message;
import ru.narizhny.test_task_inside.domain.projections.MessageFromDb;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    @EntityGraph(attributePaths = {"messages"})
    @Query(value = "select c from Client c where c.name like :name")
    Optional<Client> findByName(@Param("name")String name);

    @Query(
            nativeQuery = true,
            value = "SELECT m.id AS id, " +
                    "       m.message AS message " +
                    "FROM message m " +
                    "JOIN client c on c.id = m.client_id " +
                    "WHERE c.name ILIKE :name " +
                    "ORDER BY m.id DESC " +
                    "LIMIT :limit"
    )
    List<MessageFromDb> findAllByHistory(@Param("name") String name,
                                         @Param("limit") Long limit);
}
