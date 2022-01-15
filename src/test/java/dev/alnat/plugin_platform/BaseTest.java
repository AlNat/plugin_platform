package dev.alnat.plugin_platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.alnat.plugin_platform.core.model.PluginUsage;
import dev.alnat.plugin_platform.plugin.model.PluginRequest;
import dev.alnat.plugin_platform.plugin.model.PluginResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Набор общих методов
 *
 * Created by @author AlNat on 16.01.2022.
 * Licensed by Apache License, Version 2.0
 */
@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class BaseTest {

    static {
        System.setProperty("java.net.preferIPv4Stack", "true");
        System.setProperty("isLoggingToConsoleEnabled", "true");
    }

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper mapper;

    protected static final String FIRST_PLUGIN_NAME = "first";
    protected static final String SECOND_PLUGIN_NAME = "second";
    protected static final String THIRD_PLUGIN_NAME = "third";


    /////////////////////////
    // Набор общих методов //
    /////////////////////////

    @SneakyThrows
    protected void deletePlugin(String plugin) {
        this.mvc.perform(
                        delete("/api/platform/plugin/" + plugin))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @SneakyThrows
    protected int deleteNotExistsPlugin(String plugin) {
        return this.mvc.perform(
                        delete("/api/platform/plugin/" + plugin))
                .andDo(print())
                .andReturn().getResponse().getStatus();
    }


    @SneakyThrows
    protected Long getStatistics(String plugin) {
        String responseAsString = this.mvc.perform(
                        get("/api/platform/statistics/" + plugin))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse().getContentAsString();

        PluginUsage usage = mapper.readValue(responseAsString, PluginUsage.class);
        return usage.getCallCount();
    }

    @SneakyThrows
    protected int getStatisticsOfNoCachedPlugin(String plugin) {
        return this.mvc.perform(
                        get("/api/platform/statistics/" + plugin))
                .andDo(print())
                .andReturn().getResponse().getStatus();
    }

    @SneakyThrows
    protected String getResponseToPluginRequest(String plugin, String request) {
        PluginRequest req = new PluginRequest();
        req.setRequest(request);

        String reqAsString = mapper.writeValueAsString(req);

        String responseAsString = this.mvc.perform(
                        post("/api/platform/plugin/" + plugin)
                                .content(reqAsString)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse().getContentAsString();

        PluginResponse response = mapper.readValue(responseAsString, PluginResponse.class);
        return response.getResponse();
    }

    @SneakyThrows
    protected int getFailureResponseToPluginRequest(String plugin, String request) {
        PluginRequest req = new PluginRequest();
        req.setRequest(request);

        String reqAsString = mapper.writeValueAsString(req);

        return this.mvc.perform(
                        post("/api/platform/plugin/" + plugin)
                                .content(reqAsString)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andReturn().getResponse().getStatus();
    }


}
