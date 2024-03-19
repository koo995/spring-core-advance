package core.advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate {

    public void execute() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        /**
         * 아래의 부분만 해결하면 된다... 어떻게 해결하나? 상속으로 해결한다. 자식클래스에 따라서 이 부분이 바뀐다.
         * 단일체계원칙도 잘 지킬 수 있다. 수정이 필요할때 한 부분만 수정하면 된다.
         */
        call(); //상속
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    protected abstract void call();
}
