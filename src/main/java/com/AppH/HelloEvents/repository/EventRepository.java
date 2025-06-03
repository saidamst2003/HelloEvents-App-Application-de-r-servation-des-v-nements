package com.AppH.HelloEvents.repository;
import com.AppH.HelloEvents.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}



