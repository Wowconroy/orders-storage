package com.example.storage;

import com.example.storage.model.BuildingStorage;
import com.example.storage.repository.BuildingStorageRepository;
import com.example.storage.service.BuildingStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class StorageApplicationTests {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BuildingStorageService buildingStorageService;

    @Autowired
    private BuildingStorageRepository buildingStorageRepository;

    @Test
    void contextLoads() throws Exception {
        this.mockMvc.perform(get("/api/v1/orders/getAll?item=item"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void checkReturnItem() {
        BuildingStorage firstOrder = new BuildingStorage();
        firstOrder.setItem("Hammer");
        buildingStorageService.makeOrder(firstOrder);

        BuildingStorage secondOrder = new BuildingStorage();
        secondOrder.setItem("Screwdriver");
        buildingStorageService.makeOrder(secondOrder);

        BuildingStorage thirdOrder = new BuildingStorage();
        thirdOrder.setItem("Drill");
        buildingStorageService.makeOrder(thirdOrder);

        BuildingStorage firstOrderGet = buildingStorageRepository.findById(1L).get();
        BuildingStorage secondOrderGet = buildingStorageRepository.findById(2L).get();
        BuildingStorage thirdOrderGet = buildingStorageRepository.findById(3L).get();

        assertThat(firstOrderGet.getItem()).isEqualTo(firstOrder.getItem());
        assertThat(secondOrderGet.getItem()).isEqualTo(secondOrder.getItem());
        assertThat(thirdOrderGet.getItem()).isEqualTo(thirdOrder.getItem());
    }

    @Test
    public void makeOrder() throws Exception {
        String uri = "/api/v1/orders/create?price=50&quantity=1&item=Hammer";
        BuildingStorage order = new BuildingStorage();
        order.setPrice(new BigDecimal(50));
        order.setQuantity(1);
        order.setItem("Hammer");

        String inputJson = mapToJson(order);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }

//    @Test
//    public void deleteAfter() throws NoSuchElementException {
//        long tenMin = System.currentTimeMillis();
//        BuildingStorage order = new BuildingStorage();
//        order.setTimeStamp(tenMin);
//        buildingStorageService.makeOrder(order);
//        tenMin += 601;
//        long diff = tenMin - buildingStorageRepository.findById(1L).get().getTimeStamp();
//        if (diff > 600) {
//            buildingStorageRepository.delete(order);
//        }
//        try {
//            buildingStorageRepository.findById(1L).get();
//        } catch (NoSuchElementException ex) {
//            exception.expect(NoSuchElementException.class);
//        }
//    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

}
