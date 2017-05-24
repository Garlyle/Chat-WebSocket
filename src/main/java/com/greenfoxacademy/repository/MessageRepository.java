package com.greenfoxacademy.repository;

import com.greenfoxacademy.model.OutputMessage;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<OutputMessage, Long>{
}
