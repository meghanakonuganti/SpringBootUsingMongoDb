package com.stackroute.Muzixapp.service;
import com.stackroute.Muzixapp.domain.Track;
import com.stackroute.Muzixapp.exceptions.TrackAlreadyExistsException;
import com.stackroute.Muzixapp.exceptions.TrackNotFoundException;
import com.stackroute.Muzixapp.repository.TrackRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
public class TrackServiceTest {
    Track track;
    @Mock
    TrackRepository trackRepository;
    @InjectMocks
    TrackServiceImpl trackService;
    List<Track> list= null;
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
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
    public void saveTrackTestSuccess()  throws TrackAlreadyExistsException
    {
        when(trackRepository.save((Track) any())).thenReturn(track);
        Track savedTrack=trackService.saveTrack(track);
        Assert.assertEquals(track,savedTrack);
        Mockito.verify(trackRepository,times(1)).save(track);
    }
    @Test(expected =TrackAlreadyExistsException.class)
    public void saveTrackTestFailure() throws TrackAlreadyExistsException
    {
        when(trackRepository.save((Track) any())).thenReturn(null);
        Track savedTrack = trackService.saveTrack(track);
        System.out.println("savedTrack" + savedTrack);
        Assert.assertEquals(track,savedTrack);
    }
    @Test
    public void getAllTracks()
    {
        trackRepository.save(track);
        when(trackRepository.findAll()).thenReturn(list);
        List<Track> muzixlist=trackService.getAllTracks();
        Assert.assertEquals(list,muzixlist);
    }
    @Test
   public void updateTrackTestSuccess() throws TrackNotFoundException
    {
        when(trackRepository.save(any())).thenReturn(track);
      Track updateTrack=trackService.updateTrack(track);
        Assert.assertEquals(track,updateTrack);
       verify(trackRepository,times(1)).save(track);
    }
    @Test
    public void deleteTrackTestSuccess() throws TrackNotFoundException
    {
        when(trackRepository.findById(any())).thenReturn(Optional.of(track));
       boolean deleteTrack=trackService.deleteTrack(track.getId());
       Assert.assertEquals(deleteTrack,true);
       verify(trackRepository,times(1)).delete(track);
      /*doNothing().when(trackRepository).deleteById(2);
      trackService.deleteTrack(track.getId());
      Mockito.verify(trackRepository,times((1)));
*/
    }

}
