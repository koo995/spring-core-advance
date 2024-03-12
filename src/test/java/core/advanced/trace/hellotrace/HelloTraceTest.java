package core.advanced.trace.hellotrace;

import core.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;


public class HelloTraceTest {

    @Test
    void begin_end() {
        HelloTrace trace = new HelloTrace();
        TraceStatus status1 = trace.begin("hello");
        TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");
        trace.end(status2);
        trace.end(status1);
    }

    @Test
    void begin_exception() {
        HelloTrace trace = new HelloTrace();
        TraceStatus status1 = trace.begin("hello");
        TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");
        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }

}