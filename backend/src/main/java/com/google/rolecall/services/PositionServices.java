package com.google.rolecall.services;

import com.google.rolecall.models.Position;
import com.google.rolecall.repos.PositionRepository;
import com.google.rolecall.restcontrollers.exceptionhandling.RequestExceptions.EntityNotFoundException;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service("positionServices")
public class PositionServices {

  private final PositionRepository positionRepo;

  public Position getPosition(int id) throws EntityNotFoundException {
    Optional<Position> query = positionRepo.findById(id);

    if(query.isEmpty()) {
      throw new EntityNotFoundException(String.format("No position with id %d", id));
    }

    return query.get();
  }

  void updatePosition(Position position) {
    positionRepo.save(position);
  }

  public PositionServices(PositionRepository positionRepo) {
    this.positionRepo = positionRepo;
  }
}
