package org.work.domen.com.impl.segment;

import org.work.domen.com.Com;
import org.work.domen.com.Coms;
import org.work.domen.connector.Connect;
import org.work.domen.entity.Segment;
import org.work.domen.message.Message;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class GetSegmentCommand implements Com {

    private Connect connection;

    public GetSegmentCommand(Connect connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {
        request.setCommand(Coms.GET_ALL_SEGMENT);

        Message response = connection.makeDialog(request);
        List<Segment> segments = (List<Segment>) response.getByKey("segments");

        ObservableList<Segment> segmentObservableList = FXCollections.observableList(segments);

        response.add("segments", segmentObservableList);

        return response;
    }

}
