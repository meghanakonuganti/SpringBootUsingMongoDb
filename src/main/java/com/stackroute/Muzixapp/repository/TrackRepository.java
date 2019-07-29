package com.stackroute.Muzixapp.repository;

import com.stackroute.Muzixapp.domain.Track;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends MongoRepository<Track, Integer> {

    @Query("{ 'trackName' : ?0 }")
    List<Track> findByName(String name);
}
