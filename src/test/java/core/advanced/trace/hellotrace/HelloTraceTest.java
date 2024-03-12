package core.advanced.trace.hellotrace;

import core.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;


public class HelloTraceTest {

    @Test
    void begin_end() {
        HelloTrace trace = new HelloTrace();
        TraceStatus status = trace.begin("hello");
        trace.end(status);
    }

    @Test
    void begin_exception() {
        HelloTrace trace = new HelloTrace();
        TraceStatus status = trace.begin("hello");
        trace.exception(status, new IllegalStateException());
    }

}