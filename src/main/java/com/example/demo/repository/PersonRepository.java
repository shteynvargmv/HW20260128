package com.example.demo.repository;

import com.example.demo.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

    List<Person> findByProfessionId(Long id);
    
    List<Person> findByNameContains(String filterValue);

    List<Person> findBySurnameContains(String filterValue);

    @Query(value = "SELECT new com.example.demo.entity.Person(p.id, p.name, p.surname, p.email, p.age, pr) " +
            "FROM Person p " +
            "INNER JOIN p.profession pr " +
            "WHERE CAST(p.id AS string) LIKE %:filterValue%")
    List<Person> findValueId(@Param("filterValue") String filterValue);

    @Query(value = "SELECT new com.example.demo.entity.Person(p.id, p.name, p.surname, p.email, p.age, pr) " +
            "FROM Person p " +
            "INNER JOIN p.profession pr " +
            "WHERE CAST(p.age AS string) LIKE %:filterValue%")
    List<Person> findValueAge(@Param("filterValue") String filterValue);

    List<Person> findByEmailContains(String filterValue);

    @Query(value = "SELECT new com.example.demo.entity.Person(p.id, p.name, p.surname, p.email, p.age, pr) " +
            "FROM Person p " +
            "INNER JOIN p.profession pr " +
            "WHERE CAST(p.id as string) LIKE %:filterValue%" +
            "   OR LOWER(p.name) LIKE LOWER(CONCAT('%', :filterValue, '%'))" +
            "   OR LOWER(p.surname) LIKE LOWER(CONCAT('%', :filterValue, '%'))" +
            "   OR CAST(p.age AS string) LIKE %:filterValue%" +
            "   OR LOWER(p.email) LIKE LOWER(CONCAT('%', :filterValue, '%'))" +
            "   OR LOWER(pr.name) LIKE LOWER(CONCAT('%', :filterValue, '%'))")
    List<Person> findValueJoin(@Param("filterValue") String filterValue);

    @Query(value = "SELECT new com.example.demo.entity.Person(p.id, p.name, p.surname, p.email, p.age, pr) " +
            "FROM Person p " +
            "INNER JOIN p.profession pr " +
            "WHERE LOWER(pr.name) LIKE LOWER(CONCAT('%', :filterValue, '%'))")
    List<Person> findByProfessionName(@Param("filterValue") String filterValue);
}










