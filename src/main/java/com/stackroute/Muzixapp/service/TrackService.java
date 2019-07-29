package com.stackroute.Muzixapp.service;

import com.stackroute.Muzixapp.domain.Track;
import com.stackroute.Muzixapp.exceptions.TrackAlreadyExistsException;
import com.stackroute.Muzixapp.exceptions.TrackNotFoundException;
//import com.stackroute.Muzixapp.exceptions.TrackAlreadyExistsException;

import java.util.List;

public interface TrackService {
    public  Track saveTrack(Track track) throws TrackAlreadyExistsException; //throws TrackAlreadyExistsException;
    public List<Track> getAllTracks();
    public Track getTrackById(int id) throws TrackNotFoundException;
    public boolean deleteTrack(int id) throws TrackNotFoundException;
    public Track updateTrack(Track track);
    public List<Track> findByName(String name);

}
