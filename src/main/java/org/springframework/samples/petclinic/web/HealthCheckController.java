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

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Health check controller for load balancer integration.
 * Provides a simple endpoint to check application health and database connectivity.
 *
 * @author Spring PetClinic Team
 */
@Controller
public class HealthCheckController {

    private final ClinicService clinicService;

    @Autowired
    public HealthCheckController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    /**
     * Health check endpoint for load balancers.
     * Returns a simple JSON response indicating the application status.
     *
     * @return Map containing health status information
     */
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> healthCheck() {
        Map<String, Object> healthStatus = new HashMap<>();
        
        try {
            // Verify database connectivity by performing a simple query
            clinicService.findVets();
            
            healthStatus.put("status", "UP");
            healthStatus.put("message", "PetClinic application is running");
            healthStatus.put("database", "CONNECTED");
        } catch (Exception e) {
            healthStatus.put("status", "DOWN");
            healthStatus.put("message", "PetClinic application is unavailable");
            healthStatus.put("database", "DISCONNECTED");
            healthStatus.put("error", e.getMessage());
        }
        
        return healthStatus;
    }

    /**
     * Simplified health check endpoint for load balancers that expect plain text response.
     * Returns a simple "OK" status.
     *
     * @return HTTP 200 response with "OK" text
     */
    @RequestMapping(value = "/healthz", method = RequestMethod.GET)
    @ResponseBody
    public String simpleHealthCheck() {
        try {
            // Verify database connectivity by performing a simple query
            clinicService.findVets();
            return "OK";
        } catch (Exception e) {
            // Return empty string which results in HTTP 500
            throw new RuntimeException("Health check failed: " + e.getMessage());
        }
    }
}
