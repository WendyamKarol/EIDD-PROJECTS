package com.eidd.robot.rest.controller;

import com.eidd.exceptions.RobotException;
import com.eidd.robot.rest.api.RobotApi;
import com.eidd.robot.db.entity.Robot;
import com.eidd.robot.db.service.StdRobotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-01-28T16:42:24.909806251Z[GMT]")
@RestController
public class RobotApiController implements RobotApi {

    private static final Logger log = LoggerFactory.getLogger(RobotApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    @Qualifier("robot_data_service")
    StdRobotService robotService;


    @org.springframework.beans.factory.annotation.Autowired
    public RobotApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Robot>> robotGet() {
        String accept = request.getHeader("Accept");
        return ResponseEntity.ok(robotService.getAll());
    }

    public ResponseEntity<Void> robotIdDelete(@Parameter(in = ParameterIn.PATH, description = "ID of the robot", required = true, schema = @Schema()) @PathVariable("id") Integer id) {
        String accept = request.getHeader("Accept");
        try {
            robotService.delete(id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (RobotException ae) {
            // no use to encapsulate
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Robot> robotIdGet(@Parameter(in = ParameterIn.PATH, description = "ID of the robot", required = true, schema = @Schema()) @PathVariable("id") Integer id) {
        String accept = request.getHeader("Accept");
        Robot robot = robotService.find(id).orElse(null);

        if (robot != null) {
            return ResponseEntity.ok(robot);
        }
        return new ResponseEntity<Robot>(HttpStatus.NOT_FOUND);
    }

    //UPDATE
    public ResponseEntity<Robot> robotIdPut(@Parameter(in = ParameterIn.PATH, description = "ID of the robot", required = true, schema = @Schema()) @PathVariable("id") Integer id, @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody Robot body) {
        String accept = request.getHeader("Accept");
        try {
            Robot _robot = new Robot(body.getId(), body.getX(), body.getY(), body.getTheta(), body.getV(), body.getUltraSound());
            return ResponseEntity.ok(robotService.update(_robot));
        } catch (RobotException ae) {
            // no use to encapsulate
            return new ResponseEntity<Robot>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Robot> robotPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody Robot body) {
        String accept = request.getHeader("Accept");
        return ResponseEntity.ok(robotService.create(new Robot(body.getId(), body.getX(), body.getY(), body.getTheta(), body.getV(), body.getUltraSound())));

    }

}
