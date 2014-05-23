package org.openmrs.module.freeshr.terminology.feed;

import org.ict4h.atomfeed.jdbc.JdbcConnectionProvider;
import org.ict4h.atomfeed.server.repository.jdbc.AllEventRecordsJdbcImpl;
import org.ict4h.atomfeed.server.repository.jdbc.AllEventRecordsOffsetMarkersJdbcImpl;
import org.ict4h.atomfeed.server.repository.jdbc.ChunkingEntriesJdbcImpl;
import org.ict4h.atomfeed.server.service.EventFeedService;
import org.ict4h.atomfeed.server.service.EventFeedServiceImpl;
import org.ict4h.atomfeed.server.service.feedgenerator.FeedGeneratorFactory;
import org.ict4h.atomfeed.server.service.helper.ResourceHelper;

public class OpenMRSFeedClientFactory {

    private EventFeedService getEventFeedService(JdbcConnectionProvider connectionProvider) {
        return new EventFeedServiceImpl(new FeedGeneratorFactory().getFeedGenerator(
                new AllEventRecordsJdbcImpl(connectionProvider),
                new AllEventRecordsOffsetMarkersJdbcImpl(connectionProvider),
                new ChunkingEntriesJdbcImpl(connectionProvider),
                new ResourceHelper()));
    }
}
