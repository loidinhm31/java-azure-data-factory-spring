package com.example.demo.service;

import com.azure.core.http.rest.Response;
import com.azure.resourcemanager.datafactory.DataFactoryManager;
import com.azure.resourcemanager.datafactory.models.CreateRunResponse;
import com.azure.resourcemanager.datafactory.models.PipelineRun;
import com.example.demo.model.ADFResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PipelineServiceImpl {
    @Value("${azure.resourceGroup}")
    private String azureResourceGroup;

    @Value("${azure.factory}")
    private String azureFactory;

    @Value("${azure.pipeline}")
    private String azurePipeline;

    private final DataFactoryManager dataFactoryManager;

    @Autowired
    public PipelineServiceImpl(DataFactoryManager dataFactoryManager) {
        this.dataFactoryManager = dataFactoryManager;
    }

    public ADFResponse pipelinesCreateRun() {
        Response<CreateRunResponse> responseCreateRun = dataFactoryManager
                .pipelines()
                .createRunWithResponse(
                        azureResourceGroup,
                        azureFactory,
                        azurePipeline,
                        null,
                        null,
                        null,
                        null,
                        null,
                        com.azure.core.util.Context.NONE
                );
        return ADFResponse.builder()
                .runId(responseCreateRun.getValue().innerModel().runId())
                .build();
    }

    public ADFResponse pipelinesRunGet(String runId) {
        Response<PipelineRun> responsePipelineRun = dataFactoryManager
                .pipelineRuns()
                .getWithResponse(
                        azureResourceGroup,
                        azureFactory,
                        runId,
                        com.azure.core.util.Context.NONE
                );
        return ADFResponse.builder()
                .runId(runId)
                .status(responsePipelineRun.getValue().innerModel().status())
                .build();
    }

    @SuppressWarnings("unchecked")
    private <T> Map<String, T> mapOf(Object... inputs) {
        Map<String, T> map = new HashMap<>();
        for (int i = 0; i < inputs.length; i += 2) {
            String key = (String) inputs[i];
            T value = (T) inputs[i + 1];
            map.put(key, value);
        }
        return map;
    }
}
