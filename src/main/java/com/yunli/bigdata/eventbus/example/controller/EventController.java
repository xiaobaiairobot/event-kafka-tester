package com.yunli.bigdata.eventbus.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yunli.bigdata.common.EventResponseCode;
import com.yunli.bigdata.dsep.foundation.Result;
import com.yunli.bigdata.dsep.service.common.constant.CommonMessageCode;
import com.yunli.bigdata.eventbus.example.service.EventService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author david
 * @date 2020/7/28 7:37 下午
 */
@Api(tags = "event-kafka-sample", value = "消息测试")
@ApiResponses(value = {
    @ApiResponse(code = EventResponseCode.SUCCESS_CODE, message = EventResponseCode.SUCCESS_MSG),
    @ApiResponse(code = EventResponseCode.BAD_REQUEST_CODE, message = EventResponseCode.BAD_REQUEST_MSG),
    @ApiResponse(code = EventResponseCode.UNAUTHORIZED_CODE, message = EventResponseCode.UNAUTHORIZED_MSG),
    @ApiResponse(code = EventResponseCode.FORBIDDEN_CODE, message = EventResponseCode.FORBIDDEN_MSG),
    @ApiResponse(code = EventResponseCode.NOT_FOUND_CODE, message = EventResponseCode.NOT_FOUND_MSG),
    @ApiResponse(code = EventResponseCode.EXISTS_CODE, message = EventResponseCode.EXISTS_MSG),
    @ApiResponse(code = EventResponseCode.INTERNAL_SERVER_ERROR_CODE, message = EventResponseCode.INTERNAL_SERVER_ERROR_MSG),
})
@RequestMapping(value = "/kafka/test")
@RestController
@CrossOrigin
public class EventController {

  private final EventService eventService;

  @Autowired
  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @ApiOperation(value = "发送测试消息", notes = "发送测试消息", nickname = "sendKafkaMessage")
  @GetMapping(value = "")
  public ResponseEntity<Result> sendKafkaMessage() {
    try {
      eventService.sendMessage();
      return ResponseEntity.ok().body(Result.success("success"));
    } catch (Exception ex) {
      ex.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Result.error(CommonMessageCode.ERROR_500.getCode(), ex.getMessage()));
    }
  }
}
