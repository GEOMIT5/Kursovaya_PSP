package org.work.domen.utilit.builder;

import org.work.domen.entity.Segment;


public interface SegmentBuilder {

    SegmentBuilder withName(String name);

    Segment build();

}
