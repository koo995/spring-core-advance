package core.advanced.app.v1;

import org.springframework.web.bind.annotation.*;

/**
 * 컨트롤러를 인터페이스로 할때 아래의 애너테이션을 달아줘야 한다.
 */
@RestController
public interface OrderControllerV1 {

    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/v1/no-log")
    String noLog();

}
