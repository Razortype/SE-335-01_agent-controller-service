package com.razortype.cyberproject.api.state;

import com.razortype.cyberproject.core.enums.ServerState;
import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.core.results.SuccessDataResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/server")
public class ServerStateController {

    @GetMapping("/state")
    public ResponseEntity<DataResult<State>> getServerState() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessDataResult<>(
                        State.builder()
                                .serverState(ServerState.WORKING)
                                .build(),
                        "Server status fetched"
                ));
    }


}
