package com.stackroute.Muzixapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.Muzixapp.MuzixAppApplication;
import com.stackroute.Muzixapp.domain.Track;
import com.stackroute.Muzixapp.exceptions.TrackAlreadyExistsException;
import com.stackroute.Muzixapp.exceptions.TrackNotFoundException;
import com.stackroute.Muzixapp.service.TrackService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = MuzixAppApplication.class)
@WebMvcTest
public class TrackControllerTest{

    private MockMvc mvc;
    @Mock
    private TrackService trackService;
    @InjectMocks
    private TrackController trackController;
    private List<Track> list=null;
    private Track track;
    //write test cases here
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mvc= MockMvcBuilders.standaloneSetup(trackController).build();
        list=new ArrayList<>();
        track=new Track(1,"super machi","s/o sathyamurthy");
        list.add(track);
        track=new Track(2,"ringa ringa","arya2");
        list.add(track);
        track=new Track(3,"my love is gone","arya2");
        list.add(track);
        track=new Track(4,"rowdy baby","maari2");
        list.add(track);
    }
    @Test
    public void getAllTracks() throws Exception{
        when(trackService.getAllTracks()).thenReturn(list);
        mvc.perform(MockMvcRequestBuilders.get("/api/v2/track")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void saveTrack() throws Exception{
        when(trackService.saveTrack(any())).thenReturn(track);
        mvc.perform(MockMvcRequestBuilders.get("/api/v2/track")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void saveTrackFailure() throws Exception {
        when(trackService.saveTrack(any())).thenThrow(TrackAlreadyExistsException.class);
        mvc.perform(MockMvcRequestBuilders.post("/api/v2/track")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void updateUserSuccess() throws Exception
    {
        when(trackService.updateTrack(track)).thenReturn(track);
        mvc.perform(MockMvcRequestBuilders.put("/api/v2/track")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void deleteTrackSuccess() throws Exception
    {
        when(trackService.deleteTrack(track.getId())).thenReturn(true);
        mvc.perform(MockMvcRequestBuilders.delete("/api/v2/track/{id}",track.getId())
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    private static String asJsonString(final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        }
    }
