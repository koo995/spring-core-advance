package core.advanced.app.v0;

import core.advanced.trace.TraceId;
import core.advanced.trace.TraceStatus;
import core.advanced.trace.hellotrace.HelloTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final HelloTrace trace;

    public void save(TraceId traceId, String itemId) {

        TraceStatus status = null;
        try {
            status = trace.beginSync(traceId, "OrderRepository.save()");
            // 저장 로직
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생");
            }
            sleep(1000);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
