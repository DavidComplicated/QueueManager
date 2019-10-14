package com.itg.programmerexercises.controller;

import com.itg.programmerexercises.objects.aircraft.enums.AircraftSize;
import com.itg.programmerexercises.objects.aircraft.enums.AircraftType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AircraftApiControllerTest extends AbstractControllerTest {

    private static final String baseEndpoint = "/aqmRequestProcess";
    private static final String bootEndpoint = baseEndpoint + "/boot";
    private static final String enqueueEndpoint = baseEndpoint + "/enqueue";
    private static final String dequeueEndpoint = baseEndpoint + "/dequeue";
    private static final String nextToDequeueEndpoint = baseEndpoint + "/nexttodequeue";
    private static final String aircraftJson = "{\"aircraftType\":\"%s\",\"aircraftSize\":\"%s\"}";

    @Before
    public void systemBoot() throws Exception {
        mockMvc.perform(post(bootEndpoint));
    }

    @Test
    public void testService() throws Exception {
        mockMvc.perform(get(baseEndpoint))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void enqueue() throws Exception {
        String type = AircraftType.PASSENGER.toString();
        String size = AircraftSize.LARGE.toString();
        mockMvc.perform(
                post(enqueueEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(aircraftJson, type, size)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.aircraftType", is(type)))
                .andExpect(jsonPath("$.aircraftSize", is(size)));
        mockMvc.perform(get(nextToDequeueEndpoint))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.aircraftType", is(type)))
                .andExpect(jsonPath("$.aircraftSize", is(size)));
    }

    @Test
    public void enqueueWithBadData() throws Exception {
        String type = AircraftType.PASSENGER.toString();
        mockMvc.perform(
                post(enqueueEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(aircraftJson, type, "NA")))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void enqueueTestPriority() throws Exception {
        String type = AircraftType.CARGO.toString();
        String size = AircraftSize.LARGE.toString();
        String type2 = AircraftType.PASSENGER.toString();
        String size2 = AircraftSize.LARGE.toString();
        mockMvc.perform(
                post(enqueueEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(aircraftJson, type, size)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.aircraftType", is(type)))
                .andExpect(jsonPath("$.aircraftSize", is(size)));
        mockMvc.perform(
                post(enqueueEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(aircraftJson, type2, size2)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.aircraftType", is(type2)))
                .andExpect(jsonPath("$.aircraftSize", is(size2)));
        mockMvc.perform(get(nextToDequeueEndpoint))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.aircraftType", is(type2)))
                .andExpect(jsonPath("$.aircraftSize", is(size2)));
    }

    @Test
    public void dequeue() throws Exception {
        String type = AircraftType.PASSENGER.toString();
        String size = AircraftSize.LARGE.toString();
        mockMvc.perform(
                post(enqueueEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(aircraftJson, type, size)));
        mockMvc.perform(
                post(dequeueEndpoint))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.aircraftType", is(type)))
                .andExpect(jsonPath("$.aircraftSize", is(size)));
        mockMvc.perform(
                post(dequeueEndpoint))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void dequeuePriority() throws Exception {
        String type = AircraftType.PASSENGER.toString();
        String size = AircraftSize.LARGE.toString();
        String type2 = AircraftType.CARGO.toString();
        String size2 = AircraftSize.SMALL.toString();
        String type3 = AircraftType.CARGO.toString();
        String size3 = AircraftSize.LARGE.toString();
        mockMvc.perform(
                post(enqueueEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(aircraftJson, type2, size2)));
        mockMvc.perform(
                post(enqueueEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(aircraftJson, type3, size3)));
        mockMvc.perform(
                post(enqueueEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(aircraftJson, type, size)));
        mockMvc.perform(
                post(dequeueEndpoint))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.aircraftType", is(type)))
                .andExpect(jsonPath("$.aircraftSize", is(size)));
        mockMvc.perform(
                post(dequeueEndpoint))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.aircraftType", is(type3)))
                .andExpect(jsonPath("$.aircraftSize", is(size3)));
        mockMvc.perform(
                post(dequeueEndpoint))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.aircraftType", is(type2)))
                .andExpect(jsonPath("$.aircraftSize", is(size2)));
        mockMvc.perform(
                post(dequeueEndpoint))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
