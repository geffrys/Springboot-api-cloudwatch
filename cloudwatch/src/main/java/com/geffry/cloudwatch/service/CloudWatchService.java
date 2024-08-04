package com.geffry.cloudwatch.service;


import com.amazonaws.services.logs.AWSLogs;
import com.amazonaws.services.logs.model.*;
import com.geffry.cloudwatch.service.config.CloudWatchConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * REF:
 * https://github.com/makhijanaresh/aws-integration-with-spring-boot/blob/main/CloudWatchExample/src/main/java/com/example/aws/cloud/watch/controller/CloudWatchController.java
 *
 */

@Service
@AllArgsConstructor
public class CloudWatchService {

    private final CloudWatchConfiguration cloudWatchConfiguration;
    private final AWSLogs cloudWatchLog;

    public void logMessageToCloudWatch(String message) {

        List<InputLogEvent> logEvents = new ArrayList<>();
        InputLogEvent log = new InputLogEvent();
        Calendar calendar = Calendar.getInstance();

        log.setTimestamp(calendar.getTimeInMillis());
        log.setMessage(message);
        logEvents.add(log);

        String token = null;
        DescribeLogStreamsRequest logStreamsRequest = new DescribeLogStreamsRequest(cloudWatchConfiguration.getLogGroupName());

        logStreamsRequest.withLimit(5);
        List<LogStream> logStreamList;
        logStreamList = cloudWatchLog.describeLogStreams(logStreamsRequest).getLogStreams();

        for (LogStream logStream : logStreamList) {
            if (logStream.getLogStreamName().equals(cloudWatchConfiguration.getLogStreamName()))
                token = logStream.getUploadSequenceToken();
        }

        PutLogEventsRequest putLogEventsRequest = new PutLogEventsRequest();
        PutLogEventsResult putLogEventsResult = new PutLogEventsResult();
        if (token != null) {

            appendLogsToCloudWatchWithToken(putLogEventsRequest, putLogEventsResult, token, logEvents);
        } else {
            firstHitToCloudWatch(putLogEventsRequest, logEvents, putLogEventsResult);
        }

    }


    private void appendLogsToCloudWatchWithToken(PutLogEventsRequest putLogEventsRequest,
                                                 PutLogEventsResult putLogEventsResult, String token, List<InputLogEvent> logEvents) {
        putLogEventsRequest.setLogGroupName(cloudWatchConfiguration.getLogGroupName());
        putLogEventsRequest.setLogStreamName(cloudWatchConfiguration.getLogStreamName());
        putLogEventsRequest.setLogEvents(logEvents);

        putLogEventsRequest.setSequenceToken(token);

        cloudWatchLog.putLogEvents(putLogEventsRequest);

    }


    private void firstHitToCloudWatch(PutLogEventsRequest putLogEventsRequest, List<InputLogEvent> logEvents,
                                      PutLogEventsResult putLogEventsResult) {
        putLogEventsRequest.setLogGroupName(cloudWatchConfiguration.getLogGroupName());
        putLogEventsRequest.setLogStreamName(cloudWatchConfiguration.getLogStreamName());
        putLogEventsRequest.setLogEvents(logEvents);
        cloudWatchLog.putLogEvents(putLogEventsRequest);
    }
}
