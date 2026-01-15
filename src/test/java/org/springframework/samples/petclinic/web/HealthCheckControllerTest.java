/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.ClinicService;

/**
 * Unit tests for HealthCheckController
 */
public class HealthCheckControllerTest {

    private HealthCheckController controller;

    @Mock
    private ClinicService clinicService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new HealthCheckController(clinicService);
    }

    @Test
    public void testHealthCheckSuccess() {
        // Arrange
        when(clinicService.findVets()).thenReturn(new ArrayList<Vet>());

        // Act
        Map<String, Object> result = controller.healthCheck();

        // Assert
        assertNotNull(result);
        assertEquals("UP", result.get("status"));
        assertEquals("CONNECTED", result.get("database"));
        assertEquals("PetClinic application is running", result.get("message"));
    }

    @Test
    public void testHealthCheckDatabaseFailure() {
        // Arrange
        when(clinicService.findVets()).thenThrow(new RuntimeException("Database connection failed"));

        // Act
        Map<String, Object> result = controller.healthCheck();

        // Assert
        assertNotNull(result);
        assertEquals("DOWN", result.get("status"));
        assertEquals("DISCONNECTED", result.get("database"));
        assertEquals("PetClinic application is unavailable", result.get("message"));
    }

    @Test
    public void testSimpleHealthCheckSuccess() {
        // Arrange
        when(clinicService.findVets()).thenReturn(new ArrayList<Vet>());

        // Act
        String result = controller.simpleHealthCheck();

        // Assert
        assertNotNull(result);
        assertEquals("OK", result);
    }

    @Test(expected = RuntimeException.class)
    public void testSimpleHealthCheckFailure() {
        // Arrange
        when(clinicService.findVets()).thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert
        controller.simpleHealthCheck();
    }
}
