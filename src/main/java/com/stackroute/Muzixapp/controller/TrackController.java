package com.stackroute.Muzixapp.controller;

import com.stackroute.Muzixapp.domain.Track;
//import com.stackroute.Muzixapp.exceptions.TrackAlreadyExistsException;
import com.stackroute.Muzixapp.exceptions.TrackAlreadyExistsException;
import com.stackroute.Muzixapp.exceptions.TrackNotFoundException;
import com.stackroute.Muzixapp.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/v2")
public class TrackController {
    @Autowired
    TrackService trackService;

    public TrackController(TrackService trackService)
    {
        this.trackService=trackService;
    }

    @PostMapping("track")
    public ResponseEntity<?> saveTrack(@RequestBody Track track) throws TrackAlreadyExistsException{
        ResponseEntity responseEntity;
        try{
            trackService.saveTrack(track);
            responseEntity=new ResponseEntity<String>("successfully created", HttpStatus.CREATED);
        }catch(TrackAlreadyExistsException ex){
            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("track")
    public ResponseEntity<?> getTracks(){
        return new ResponseEntity<List<Track>>(trackService.getAllTracks(),HttpStatus.OK);
    }

    @PostMapping("tracks")
    public ResponseEntity<?> saveuser1(@RequestBody List<Track> track)throws TrackAlreadyExistsException{
        ResponseEntity responseEntity;
        for (Track t:track
        ) {
            trackService.saveTrack(t);
        }
        responseEntity=new ResponseEntity<List<Track>>(trackService.getAllTracks(), HttpStatus.CREATED);
        return responseEntity;
    }


    @GetMapping("track/{id}")
    public ResponseEntity<?> getTrackById(@PathVariable(value="id") int id) throws TrackAlreadyExistsException {
        ResponseEntity responseEntity;
        try{
            trackService.getTrackById(id);
            responseEntity=new ResponseEntity<String>("successfully created", HttpStatus.CREATED);
        }catch(TrackNotFoundException ex){
            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.OK);
        }
        return responseEntity;
    }

    @GetMapping("track/name/{name}")
    public ResponseEntity<?> getTrackByName(@PathVariable(value="name") String name) {
        ResponseEntity responseEntity;
        responseEntity=new ResponseEntity<List<Track>>(trackService.findByName(name), HttpStatus.CREATED);
        return responseEntity;
    }

    @DeleteMapping("track/{id}")
    public ResponseEntity<?> deleteTrack(@PathVariable(value="id") int id) throws TrackNotFoundException {
        trackService.deleteTrack(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("track")
    public ResponseEntity<Track> updateUser(@RequestBody Track track){
        trackService.updateTrack(track);
        return new ResponseEntity<Track>(track,HttpStatus.OK);
    }

}
