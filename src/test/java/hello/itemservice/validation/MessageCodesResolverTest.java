package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.*;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageCodesResolverTest {
    /**
     * - MessageCodesResolver는 검증 오류 코드로 메시지 코드(required 등)들을 생성한다.
     * - DefaultMessageCodesResolver가 기본 구현체이다.
     * - DefaultMessageCodesResolver은 기본 메시지 생성 규칙이 있다.
     * 객체 오류의 경우 다음 순서로 2가지 생성한다.
     *
     * 예) 오류 코드: required /  object name : item
     * 1. code + "." + object name (required.item)
     * 2. code (required)
     *
     * 필드 오류의 경우 다음 순서로 4가지 메시지 코드를 생성한다.
     *
     * 예) 오류 코드: typeMismatch, objeect name, field "age", field type: int
     * 1. "typeMismatch.user.age"
     * 2. "typeMismatch.age"
     * 3. "typeMismatch.int"
     * 4. "typeMismatch"
     */
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    /**
     * MessageCodesResolver가 메시지 코드를 읽어온다.
     */
    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        for (String messageCode : messageCodes) {
            System.out.println("messageCode=" + messageCode);
        }

        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    /**
     * messageCode = required.item.itemName (디테일한 순으로 출력)
     * messageCode = required.itemName
     * messageCode = required.java.lang.String
     * messageCode = required
     *
     * [동작 방식]
     * rejectValue() , reject() 는 내부에서 MessageCodesResolver 를 사용한다. 여기에서 메시지 코드들을 생성한다.
     * FieldError, ObjectError 의 생성자를 보면, 오류 코드를 하나가 아니라 여러 오류 코드를 가질 수 있다.
     * MessageCodesResolver 를 통해서 생성된 순서대로 오류 코드를 보관한다.
     * codes [range.item.price, range.price, range.java.lang.Integer, range]
     *
     * ex) new FieldError("item", "itemName", item.getItemName(), false, required.item.itemName ... );
     * ex) new FieldError("item", "itemName", item.getItemName(), false, required.itemName ... );
     * ex) new FieldError("item", "itemName", item.getItemName(), false, required.java.lang.String ... );
     * ex) new FieldError("item", "itemName", item.getItemName(), false, required ... );
     *
     * 이 부분을 BindingResult 의 로그를 통해서 확인해보자.
     * Error in object 'item': codes [totalPriceMin.item,totalPriceMin]; arguments [10000,1111]; default message [null]
     *
     * 테스트해보니 디테일한 순으로 탐색 후 찾고자하는 메시지코드가 존재하면 찾아와서 출력하고 끝낸다.
     * 만약 찾아도 없다면 디폴트 메시지를 출력한다.
     */
    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);

//        for (String messageCode : messageCodes) {
//            System.out.println("messageCode = " + messageCode);
//        }

        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );

    }
}
