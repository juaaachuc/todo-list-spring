package com.juaachuc.todo_list.repository;

import com.juaachuc.todo_list.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {}