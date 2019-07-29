package com.stackroute.Muzixapp.service;

import com.stackroute.Muzixapp.domain.Track;
import com.stackroute.Muzixapp.exceptions.TrackAlreadyExistsException;
import com.stackroute.Muzixapp.exceptions.TrackNotFoundException;
import com.stackroute.Muzixapp.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
/*
@PropertySource("classpath:application.properties")
*/
public class TrackServiceImpl implements TrackService {
   /* @Value("${Track.trackId}")
    int trackId;
    @Value("${Track.trackName}")
    String trackName;
    @Value("${Track.trackComments}")
    String trackComments;*/

    private TrackRepository trackRepository;

    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistsException {
        if (trackRepository.findById(track.getId()).isPresent()) {
            throw new TrackAlreadyExistsException("track already exists");
        }
        Track savedTrack = trackRepository.save(track);
        if (savedTrack == null) {
            throw new TrackAlreadyExistsException("Track already exists");
        }
        return savedTrack;
    }

    @Override
    public List<Track> getAllTracks() {

        return trackRepository.findAll();
    }

    @Override
    public Track getTrackById(int id) throws TrackNotFoundException {
        if (!trackRepository.findById(id).isPresent()) {
            throw new TrackNotFoundException("Does not exist");
        }
        return trackRepository.findById(id).get();
    }

    @Override
    public boolean deleteTrack(int id) throws TrackNotFoundException {
        trackRepository.delete(getTrackById(id));
        return true;
    }
    @Override
    public Track updateTrack(Track track){
        return trackRepository.save(track);
    }

    @Override
    public List<Track> findByName(String name) {
        return trackRepository.findByName(name);
    }
}
